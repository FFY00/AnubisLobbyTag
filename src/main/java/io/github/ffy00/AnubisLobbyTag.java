/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00;

import io.github.ffy00.listeners.PlayerQuitListener;
import io.github.ffy00.provider.DatabaseProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class AnubisLobbyTag extends JavaPlugin {

    private PluginDescriptionFile pl;
    private File bukkitFolder;
    private File pluginsFolder;
    private File pluginFolder;
    private PluginManager pm;
    private DatabaseProvider dp;

    @Override
    public void onEnable(){
        pl = getDescription();
        Bukkit.getConsoleSender().sendMessage("§bEnabling §cAnubisLobbyTag §bv" + pl.getVersion() + " by FFY00!");

        setupConfig();
        registerListeners();

        dp = new DatabaseProvider(this, "158.69.203.154", "ffy00", "ffy00", "mwwKU9Dsjwe7nE7H");
        dp.version();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§bDisabling §cAnubisLobbyTag §bv" + pl.getVersion() + " by FFY00!");
    }

    /*
    * Setup Config
    */
    private void setupConfig(){
        bukkitFolder = getDataFolder();
        pluginsFolder = new File(bukkitFolder, "plugins");
        pluginFolder = new File(pluginsFolder, pl.getName());
        if(!new File (bukkitFolder, "config.yml").exists()){
            saveDefaultConfig();
        }
    }

    /**
     * Register Listeners
     */
    private void registerListeners(){
        pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerQuitListener(dp), this);
    }

}
