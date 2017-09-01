/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00.executor;

import io.github.ffy00.provider.ConfigProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class PluginCommandExecutor implements CommandExecutor {

    private ConfigProvider lcp;
    private FileConfiguration lconfig;

    public PluginCommandExecutor(ConfigProvider cp){
        lcp = cp;
        lconfig = cp.get();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] agrs){

        if(!sender.hasPermission("anubis.tags"))
            return false;

        if(label.equalsIgnoreCase("forcartag")) {

            if(agrs.length < 2) {
                sender.sendMessage(lconfig.getString("msg.add.usage").replaceAll("&", "§").replaceAll("%cmd%", label));
                return false;
            }

            lconfig.set("forced_tags.players", lconfig.getStringList("").add(agrs[0]));
            lconfig.set("forced_tags.server_name", agrs[1]);
            lcp.save(lconfig);
            return true;
        } else if(label.equalsIgnoreCase("desforcartag")) {

            if(agrs.length < 1) {
                sender.sendMessage(lconfig.getString("msg.remove.usage").replaceAll("&", "§").replaceAll("%cmd%", label));
                return false;
            }

            lconfig.set("forced_tags.players", lconfig.getStringList("forced_tags.players").remove(agrs[0]));
            lcp.save(lconfig);
            return true;
        } else {
            return false;
        }
    }

}