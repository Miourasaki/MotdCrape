package net.miourasaki.motdcrape.bungee;

import net.miourasaki.motdcrape.BungeePlugin;
import net.miourasaki.motdcrape.console.Out;

public class EnableMaintainMode {

    public static void change() {
        BungeeConfigObject config = BungeePlugin.masterConfig;

        BungeePlugin.maintainMode = !BungeePlugin.maintainMode;

        config.set("maintain.enabled", BungeePlugin.maintainMode);

        if (BungeePlugin.maintainMode) {
            Out.send("Start maintain mode");
        }else {
            Out.send("Stop maintain mode");

        }
    }

}
