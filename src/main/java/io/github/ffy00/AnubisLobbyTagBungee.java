/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.GroupedThreadFactory;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class AnubisLobbyTagBungee extends Plugin {

    @Override
    public void onEnable(){
        getLogger().info("§bEnabling §cAnubisLobbyTag §bv" + getDescription().getVersion() + " by FFY00!");

    }

    @Override
    public void onDisable(){
        getLogger().info("§bDisabling §cAnubisLobbyTag §bv" + getDescription().getVersion() + " by FFY00!");
    }

}
