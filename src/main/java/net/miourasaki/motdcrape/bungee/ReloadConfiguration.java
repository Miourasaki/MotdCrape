package net.miourasaki.motdcrape.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.miourasaki.motdcrape.BungeePlugin;
import net.miourasaki.motdcrape.console.Out;

import java.io.File;
import java.io.IOException;

public class ReloadConfiguration {


    public static void reload() {
        try {
            BungeePlugin.masterConfig = new BungeeConfigObject(BungeePlugin.instance, "default","master.yml");
            BungeePlugin.maintainMode = BungeePlugin.masterConfig.configuration.getBoolean("maintain.enabled");


            File otherConfig = new File(BungeePlugin.instance.getDataFolder() + "/configs");
            if (otherConfig.isDirectory()) {
                File[] files = otherConfig.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".yml")) {
                            BungeePlugin.otherConfig.put(
                                    file.getName().replace(".yml", ""),
                                    new BungeeConfigObject(BungeePlugin.instance, file.getName().replace(".yml", ""),"configs/" + file.getName())
                            );
                        }
                    }
                }
            }


            BungeePlugin.defaultConfig = new BungeeConfigObject(BungeePlugin.instance, BungeePlugin.masterConfig.configuration.getString("default-config"),"default.yml");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Out.send("Configuration load finish！");
    }
}
