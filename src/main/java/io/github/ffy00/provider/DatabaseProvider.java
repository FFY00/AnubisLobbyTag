/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00.provider;

import io.github.ffy00.provider.model.DatabaseProviderModel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class DatabaseProvider extends DatabaseProviderModel{

    private String lsvname;

    public DatabaseProvider(JavaPlugin plugin, String svname, String host, String db, String user, String password){
        super(plugin, host, db, user, password);
        lsvname = svname.toLowerCase();
        loadTable();
    }

    public DatabaseProvider(JavaPlugin plugin, String svname, String host, String port, String db, String user, String password){
        super(plugin, host, port, db, user, password);
        lsvname = svname.toLowerCase();
        loadTable();
    }

    protected void loadTable(){
        try{
            Statement st = null;
            st = con.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS " + lsvname + " " +
                                "(id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                                " nick VARCHAR(16), " +
                                " rank VARCHAR(32)) ");

            Bukkit.getConsoleSender().sendMessage("§cAnubisLobbyTag §e>> §bLoaded rank table");
        } catch(SQLException ex){
            Bukkit.getConsoleSender().sendMessage("§cAnubisLobbyTag §e>> §4§l[!] §bError loading the rank table");
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(lplugin);
        }
    }

    public void setPrefix(String name, String prefix){
        PreparedStatement pst = null;
        ResultSet rs = null;

        try{
            pst = con.prepareStatement("SELECT * FROM " + lsvname + " WHERE nick = ?");
            pst.setString(1, name);
            rs = pst.executeQuery();

            boolean exists = false;
            // Exists (Update value)
            while(rs.next()){
                pst = con.prepareStatement("UPDATE " + lsvname + " SET rank = ? WHERE nick = ?");
                pst.setString(1, prefix);
                pst.setString(2, rs.getString(2));
                pst.executeUpdate();
                exists = true;
            }

            // Doesn't exist (Insert value)
            if(!exists){
                pst = con.prepareStatement("INSERT INTO " + lsvname + " (nick, rank) VALUES (?, ?)");
                pst.setString(1, name);
                pst.setString(2, prefix);
                pst.executeUpdate();
            }

        } catch (SQLException ex){
            Bukkit.getConsoleSender().sendMessage("§cAnubisLobbyTag §e>> §4§l[!] §bError sending player perfix §d(" + name + ")");
        }
    }

    public String getPrefix(String name){
        PreparedStatement pst = null;
        ResultSet rs = null;

        try{
            pst = con.prepareStatement("SELECT rank FROM " + lsvname + " WHERE nick = ?");
            pst.setString(1, name);
            rs = pst.executeQuery();

            while(rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException ex){
            Bukkit.getConsoleSender().sendMessage("§cAnubisLobbyTag §e>> §4§l[!] §bError getting player perfix §d(" + name + ")");
        }
        return null;
    }

    public void setServerName(String svname) {
        lsvname = svname.toLowerCase();
    }

}
