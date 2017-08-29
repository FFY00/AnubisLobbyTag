/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00.listeners;

import io.github.ffy00.provider.DatabaseProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class PlayerQuitListener implements Listener{

    private DatabaseProvider ldp;

    public PlayerQuitListener(DatabaseProvider dp){
        ldp = dp;
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){
        Player p = e.getPlayer();
        String prefix = PermissionsEx.getUser(p).getPrefix();
        if(prefix == null){
            prefix = PermissionsEx.getUser(p).getGroups()[0].getPrefix();
        }
        Bukkit.getConsoleSender().sendMessage(p.getName() + "." + prefix);
        ldp.setPrefix(p.getName(), prefix);
    }

}
