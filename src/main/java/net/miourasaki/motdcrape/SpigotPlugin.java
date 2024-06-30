package net.miourasaki.motdcrape;

import net.miourasaki.motdcrape.spigot.ReloadConfiguration;
import net.miourasaki.motdcrape.spigot.SpigotConfigObject;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class SpigotPlugin extends JavaPlugin {

    public static SpigotPlugin instance;

    public static SpigotConfigObject masterConfig;
    public static SpigotConfigObject defaultConfig;
    public static HashMap<String,SpigotConfigObject> otherConfig = new HashMap<>();

    public static boolean maintainMode = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        ReloadConfiguration.reload();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
