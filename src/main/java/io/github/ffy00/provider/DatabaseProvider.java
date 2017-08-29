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

    protected String lsvname;

    public DatabaseProvider(JavaPlugin plugin, String host, String db, String user, String password){
        super(plugin, host, db, user, password);
    }

    public DatabaseProvider(JavaPlugin plugin, String host, String port, String db, String user, String password){
        super(plugin, host, port, db, user, password);
    }

    protected void loadTable(){
        try{
            Statement st = null;
            ResultSet rs = null;
            st = con.createStatement();
            rs = st.executeQuery("CREATE TABLE IF NOT EXISTS " + lsvname + " (user STRING PRIMARY KEY, rank STRING)");

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
            pst = con.prepareStatement("SELECT * FROM " + lsvname + " WHERE name = ?");
            pst.setString(1, name);
            rs = pst.executeQuery();

            boolean exists = false;
            // Exists (Update value)
            while(rs.next()){
                pst = con.prepareStatement("UPDATE " + lsvname + " SET rank = ? WHERE name = ?");
                pst.setString(1, prefix);
                pst.setString(2, rs.getString(1));
                pst.executeUpdate();
                exists = true;
            }

            // Doesn't exist (Insert value)
            if(!exists){
                pst = con.prepareStatement("INSERT INTO " + lsvname + " (name, rank) VALUES (?, ?)");
                pst.setString(1, name);
                pst.setString(2, prefix);
                pst.executeUpdate();
            }

        } catch (SQLException ex){
            Bukkit.getConsoleSender().sendMessage("§cAnubisLobbyTag §e>> §4§l[!] §bError sending player perfix §d(" + name + ")");
            ex.printStackTrace();
        }
    }

}
