package net.miourasaki.motdcrape.spigot;

import net.miourasaki.motdcrape.SpigotPlugin;
import net.miourasaki.motdcrape.console.Out;

import java.io.File;
import java.io.IOException;

public class ReloadConfiguration {


    public static void reload() {
        try {
            SpigotPlugin.masterConfig = new SpigotConfigObject(SpigotPlugin.instance, "default","master.yml");
            SpigotPlugin.maintainMode = SpigotPlugin.masterConfig.configuration.getBoolean("maintain.enabled");


            File otherConfig = new File(SpigotPlugin.instance.getDataFolder() + "/configs");
            if (otherConfig.isDirectory()) {
                File[] files = otherConfig.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".yml")) {
                            SpigotPlugin.otherConfig.put(
                                    file.getName().replace(".yml", ""),
                                    new SpigotConfigObject(SpigotPlugin.instance, file.getName().replace(".yml", ""),"configs/" + file.getName())
                            );
                        }
                    }
                }
            }


            SpigotPlugin.defaultConfig = new SpigotConfigObject(SpigotPlugin.instance, SpigotPlugin.masterConfig.configuration.getString("default-config"),"default.yml");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Out.send("Configuration load finish！");
    }
}
