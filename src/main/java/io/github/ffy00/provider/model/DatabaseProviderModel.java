/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00.provider.model;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public abstract class DatabaseProviderModel {

    protected String lurl;
    protected String luser;
    protected String lpassword;

    protected Connection con = null;

    public DatabaseProviderModel(String host, String db, String user, String password){
        lurl = "jdbc:mysql://" + host + ":3306" + "/" + db;
        luser = user;
        lpassword = password;
        connect();
    }

    public DatabaseProviderModel(String host, String port, String db, String user, String password){
        lurl = "jdbc:mysql://" + host + ":" + port + "/" + db;
        luser = user;
        lpassword = password;
        connect();
    }

    private boolean connect(){
        try {
            con = DriverManager.getConnection(lurl, luser, lpassword);

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();

            return false;
        }
    }

    public void version(){
        try{
            Statement st = null;
            ResultSet rs = null;
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

}
