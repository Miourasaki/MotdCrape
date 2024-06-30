package net.miourasaki.motdcrape.bungee;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import net.miourasaki.motdcrape.BungeePlugin;
import net.miourasaki.motdcrape.config.ComponentMotd;
import net.miourasaki.motdcrape.config.CrapeComponent;

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
                    BaseComponent baseComponent = new TextComponent();
                    List<CrapeComponent> components = ComponentMotd.parse((List<String>) maintainList);

                    resultComponent(baseComponent, components);
                    event.setCancelReason(baseComponent);
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

    public static void resultComponent(BaseComponent baseComponent, List<CrapeComponent> components) {
        for (CrapeComponent component : components) {
            BaseComponent componentObject = new TextComponent();
            componentObject.addExtra(component.getContext());
            if (component.getColor() != null) componentObject.setColor(ChatColor.of(component.getColor()));
            if (component.isBold()) componentObject.setBold(true);
            if (component.isItalic()) componentObject.setItalic(true);
            if (component.isObfuscated()) componentObject.setObfuscated(true);
            if (component.isStrikethrough()) componentObject.setStrikethrough(true);
            if (component.isUnderlined()) componentObject.setUnderlined(true);

            baseComponent.addExtra(componentObject);
        }
    }
}
