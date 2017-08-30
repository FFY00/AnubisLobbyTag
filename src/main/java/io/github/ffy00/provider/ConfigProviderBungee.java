/*
 * Copyright 2017 FFY00
 *
 * Simple Non Code License (SNCL) v1.10.0
 */

package io.github.ffy00.provider;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;


/**
 *
 * @author FFY00 <FFY00 at ffy00.github.io>
 */
public class ConfigProviderBungee {

    protected Plugin lplugin;

    public ConfigProviderBungee(Plugin plugin){
        lplugin = plugin;
    }

    public Configuration get(){
        return get("config.yml");
    }

    protected void saveResource(String name, boolean replace){
        if (!lplugin.getDataFolder().exists())
            lplugin.getDataFolder().mkdir();

        File file = new File(lplugin.getDataFolder(), name);
        InputStream in = lplugin.getResourceAsStream(name);

        try {
            if (!file.exists() || replace) {
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }
        } catch (IOException ex) {
            lplugin.getLogger().info("§cAnubisLobbyTag §e>> §4§l[!] §bCouldn't save config §d§o" + name);
        }
    }

    public Configuration get(String name){
        lplugin.getLogger().info("§cAnubisLobbyTag §e>> §bLoading config §d§o" + name);
        File f = new File(lplugin.getDataFolder(),  name);
        if(!f.exists()){
            lplugin.getLogger().info("§cAnubisLobbyTag §e>> §bCreating config §d§o" + name);
            saveResource(name, false);
            lplugin.getResourceAsStream(name);
        }
        Configuration c = null;
        try{
            c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
        } catch (IOException ex){
            lplugin.getLogger().info("§cAnubisLobbyTag §e>> §4§l[!] §bCouldn't save config §d§o" + name);
        }
        return c;
    }

    public boolean save(Configuration c, String name) {
        File f = new File(lplugin.getDataFolder().getAbsoluteFile() + "plugins" + File.separator + lplugin.getDescription().getName() + File.separator + name);
        if (!f.exists()) {
            lplugin.getLogger().info("§cAnubisLobbyTag §e>> §bCreating config §d§o" + name);
            saveResource(name, false);
        }
        try {
            lplugin.getLogger().info("§cAnubisLobbyTag §e>> §bSaving config §d§o" + name);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(c, f);
            return true;
        } catch (IOException ex) {
            lplugin.getLogger().info("§cAnubisLobbyTag §e>> §4§l[!] §bCouldn't save config §d§o" + name);
            return false;
        }
    }

}
