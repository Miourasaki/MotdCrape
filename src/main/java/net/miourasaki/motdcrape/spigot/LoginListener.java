package net.miourasaki.motdcrape.spigot;


import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.miourasaki.motdcrape.SpigotPlugin;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.IOException;
import java.util.List;


public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) throws IOException {


        if (SpigotPlugin.maintainMode) {

            SpigotConfigObject masterConfig = SpigotPlugin.masterConfig;
            Configuration configuration = masterConfig.configuration;

                boolean enableComponent = configuration.getBoolean("maintain.component-mode");
                List<?> maintainList = configuration.getList("maintain.list");
                if (enableComponent) {
                    BaseComponent baseComponent = new TextComponent();
//                    List<CrapeComponent> components = ComponentMotd.parse((List<String>) maintainList);

                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, baseComponent.toLegacyText());
                }else {

                    StringBuilder result = new StringBuilder();
                    for (Object s: maintainList) {
                        result.append(s).append("\n");
                    }

                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, result.toString());
                }

        }

    }

}
