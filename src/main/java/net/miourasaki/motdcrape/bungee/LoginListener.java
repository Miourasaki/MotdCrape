package net.miourasaki.motdcrape.bungee;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import net.miourasaki.crapelib.text.component.CrapeParser;
import net.miourasaki.motdcrape.BungeePlugin;

import java.io.IOException;
import java.util.List;

public class LoginListener implements Listener {

    @EventHandler()
    public void onPlayerLogin(PreLoginEvent event) throws IOException {


        if (BungeePlugin.maintainMode) {

            BungeeConfigObject masterConfig = BungeePlugin.masterConfig;
            Configuration configuration = masterConfig.configuration;

                boolean enableComponent = configuration.getBoolean("maintain.component-mode");
                List<?> maintainList = configuration.getList("maintain.list");
                if (enableComponent) {
                    CrapeParser crapeParser = new CrapeParser((List<String>) maintainList);
                    event.setCancelReason(crapeParser.getBaseComponent());
                    event.setCancelled(true);
                }else {

                    StringBuilder result = new StringBuilder();
                    for (Object s: maintainList) {
                        result.append(s).append("\n");
                    }

                    event.setCancelReason(result.toString());
                    event.setCancelled(true);
                }

        }

    }

}
