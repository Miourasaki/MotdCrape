package net.miourasaki.motdcrape;

import net.md_5.bungee.api.plugin.Plugin;
import net.miourasaki.motdcrape.bungee.*;
import net.miourasaki.motdcrape.console.Out;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public final class BungeePlugin extends Plugin {

    public static BungeePlugin instance;
    public static BungeeConfigObject masterConfig;
    public static BungeeConfigObject defaultConfig;
    public static HashMap<String,BungeeConfigObject> otherConfig = new HashMap<>();

    public static boolean maintainMode = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        ReloadConfiguration.reload();

        Out.send("Motd Crape Active on " + getProxy().getName());
        getProxy().getPluginManager().registerListener(this, new ServerPingListener());
        getProxy().getPluginManager().registerListener(this, new LoginListener());
        getProxy().getPluginManager().registerCommand(this, new CommandListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Out.send("Bye!");
        getProxy().getPluginManager().unregisterListeners(this);
        getProxy().getPluginManager().unregisterCommands(this);

    }




}



