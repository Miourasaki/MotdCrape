package net.miourasaki.motdcrape.spigot;

import net.miourasaki.motdcrape.SpigotPlugin;
import net.miourasaki.motdcrape.console.Out;

public class EnableMaintainMode {

    public static void change() {
        SpigotConfigObject config = SpigotPlugin.masterConfig;

        SpigotPlugin.maintainMode = !SpigotPlugin.maintainMode;

        config.set("maintain.enabled", SpigotPlugin.maintainMode);

        if (SpigotPlugin.maintainMode) {
            Out.send("Start maintain mode");
        }else {
            Out.send("Stop maintain mode");

        }
    }

}
