/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00.listeners;

import io.github.ffy00.provider.DatabaseProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class PlayerJoinListener implements Listener{

    private DatabaseProvider ldp;
    private FileConfiguration lconfig;

    private HashMap<String, String> tags = new HashMap<String, String>();
    //private HashMap<String, Team> tags = new HashMap<String, Team>();

    public PlayerJoinListener(DatabaseProvider dp, FileConfiguration config){
        ldp = dp;
        lconfig = config;

        for(String rank : config.getStringList("ranks")){

        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        List<String> prefixes = new ArrayList<String>();

        for(String name : lconfig.getStringList("server_list")){
            ldp.setServerName(name);
            prefixes.add(ldp.getPrefix(p.getName()));
        }

        for(String name : lconfig.getStringList("ranks")){
            for(String prefix : prefixes){
                if(prefix.toLowerCase().contains(name.toLowerCase())){
                    p.setPlayerListName((lconfig.getString("tags." + name) + p.getName()).substring(0, 14));
                    return;
                }
            }
        }

        p.setPlayerListName(lconfig.getString(lconfig.getString("default_rank") + p.getName()).substring(0, 14));

    }

}
