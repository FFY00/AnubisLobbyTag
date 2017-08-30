/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00;

import io.github.ffy00.provider.ConfigProviderBungee;
import io.github.ffy00.provider.DatabaseProvider;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class AnubisLobbyTagBungee extends Plugin {

    private DatabaseProvider dp;
    private ConfigProviderBungee cp;

    private FileConfiguration config;

    @Override
    public void onEnable(){
        getLogger().info("§bEnabling §cAnubisLobbyTag §bv" + getDescription().getVersion() + " by FFY00!");

    }

    @Override
    public void onDisable(){
        getLogger().info("§bDisabling §cAnubisLobbyTag §bv" + getDescription().getVersion() + " by FFY00!");
    }

    /**
     * Setup Config
     */
    private void setupConfig(){
        cp = new ConfigProviderBungee(this);
        config = cp.get("");
    }

}
