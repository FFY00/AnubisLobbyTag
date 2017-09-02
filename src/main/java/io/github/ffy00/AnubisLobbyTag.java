/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00;

import io.github.ffy00.executor.PluginCommandExecutor;
import io.github.ffy00.listeners.PlayerJoinListener;
import io.github.ffy00.listeners.PlayerQuitListener;
import io.github.ffy00.provider.ConfigProvider;
import io.github.ffy00.provider.DatabaseProvider;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class AnubisLobbyTag extends JavaPlugin {

    private PluginManager pm;
    private DatabaseProvider dp;
    private ConfigProvider cp;

    private FileConfiguration config;

    @Override
    public void onEnable(){
        Bukkit.getConsoleSender().sendMessage("§bEnabling §cAnubisLobbyTag §bv" + getDescription().getVersion() + " by FFY00!");

        setupConfig();
        dp = new DatabaseProvider(this, config.getString("server_name"), config.getString("db.host"), config.getString("db.dbname"), config.getString("db.user"), config.getString("db.password"));
        registerListeners();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§bDisabling §cAnubisLobbyTag §bv" + getDescription().getVersion() + " by FFY00!");
    }

    /**
     * Setup Config
     */
    private void setupConfig(){
        cp = new ConfigProvider(this);
        config = cp.get();
    }

    /**
     * Register Listeners
     */
    private void registerListeners(){
        pm = getServer().getPluginManager();
        if(config.getBoolean("lobby")){
            pm.registerEvents(new PlayerJoinListener(dp, config), this);
        } else {
            pm.registerEvents(new PlayerQuitListener(dp), this);
        }
    }

    /*
    * Register Commands
    */
    private void registerCommands(){
        PluginCommandExecutor ce = new PluginCommandExecutor(cp);
        getCommand("forcartag").setExecutor(ce);
        getCommand("desforcartag").setExecutor(ce);
    }

}
