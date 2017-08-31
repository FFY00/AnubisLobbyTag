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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class PlayerJoinListener implements Listener{

    private DatabaseProvider ldp;
    private FileConfiguration lconfig;

    private HashMap<String, Team> tags = new HashMap<String, Team>();

    private ScoreboardManager sm = Bukkit.getScoreboardManager();
    private Scoreboard sc = sm.getNewScoreboard();

    public PlayerJoinListener(DatabaseProvider dp, FileConfiguration config){
        ldp = dp;
        lconfig = config;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        String pname = e.getPlayer().getName();
        List<String> prefixes = new ArrayList<String>();
        String prefix = lconfig.getString("tags." + lconfig.getString("default_rank"));

        for(String name : lconfig.getStringList("server_list")){
            ldp.setServerName(name);
            prefixes.add(ldp.getPrefix(pname));
        }

        loop:
        for(String name : lconfig.getStringList("ranks")){
            for(String gprefix : prefixes){
                if(gprefix.toLowerCase().contains(name.toLowerCase())){
                    prefix = lconfig.getString("tags." + name);
                    break loop;
                }
            }
        }

        if(lconfig.getStringList("forced_tags.players").contains(pname)){
            ldp.setServerName(lconfig.getString("forced_tags.server_name." + pname));
            prefix = ldp.getPrefix(pname);
        }

        String op = prefix;
        prefix = prefix.length() > 14 ? prefix.substring(0, 16) : prefix;
        if(!tags.containsKey(prefix)){
            Team t = sc.registerNewTeam(prefix);
            t.setPrefix(prefix.replaceAll("&", "§"));
            tags.put(prefix, t);
        }
        tags.get(prefix).addEntry(pname);
        e.getPlayer().setScoreboard(sc);
    }

}
