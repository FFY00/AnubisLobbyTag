/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00.provider;

import io.github.ffy00.provider.model.DatabaseProviderModel;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class DatabaseProvider extends DatabaseProviderModel{

    public DatabaseProvider(String host, String port, String db, String user, String password){
        super(host, port, db, user, password);
    }

    public DatabaseProvider(String host, String db, String user, String password){
        super(host, db, user, password);
    }



}
