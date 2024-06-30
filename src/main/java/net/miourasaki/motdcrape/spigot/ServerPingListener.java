//package net.miourasaki.motdcrape.spigot;
//
//import net.md_5.bungee.api.Favicon;
//import net.md_5.bungee.api.chat.BaseComponent;
//import net.md_5.bungee.api.chat.TextComponent;
//import net.md_5.bungee.api.plugin.Listener;
//import net.miourasaki.motdcrape.BungeePlugin;
//import net.miourasaki.motdcrape.SpigotPlugin;
//import net.miourasaki.motdcrape.config.ComponentMotd;
//import net.miourasaki.motdcrape.config.CrapeComponent;
//import net.miourasaki.motdcrape.config.ImageFile;
//import org.bukkit.configuration.Configuration;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.HandlerList;
//import org.bukkit.event.server.ServerListPingEvent;
//import org.bukkit.util.CachedServerIcon;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.util.*;
//
//public class ServerPingListener implements Listener {
//
//
//    @EventHandler
//    public void onProxyPing(ServerListPingEvent e) throws IOException {
//
//        SpigotConfigObject masterConfig = SpigotPlugin.masterConfig;
//        Configuration masterConfiguration = masterConfig.configuration;
//
//        SpigotConfigObject spigotConfigObject;
//        spigotConfigObject = SpigotPlugin.defaultConfig;
//
////        List<?> ruleList = masterConfiguration.getList("rules");
////        for (Object ruleObject : ruleList) {
////            LinkedHashMap<String, String> ruleMap = (LinkedHashMap<String, String>) ruleObject;
////            String rule = ruleMap.get("rule");
////            String config = ruleMap.get("config");
////            boolean ruleActive = false;
////
////            if (rule.split("\\s+").length == 3) {
////                String clientProtocol = "" + e.getEventName().getVersion();
////                String result = rule.replace("protocol", clientProtocol);
////                String[] parts = result.split("\\s+");
////
////                String leftOperandStr = parts[0];
////                String operator = parts[1];
////                String rightOperandStr = parts[2];
////
////                // 2. 转换操作数为数值
////                int leftOperand = Integer.parseInt(leftOperandStr);
////                int rightOperand = Integer.parseInt(rightOperandStr);
////
////                // 3. 执行比较操作
////                switch (operator) {
////                    case "<":
////                        ruleActive = leftOperand < rightOperand;
////                        break;
////                    case ">":
////                        ruleActive = leftOperand > rightOperand;
////                        break;
////                    case "<=":
////                        ruleActive = leftOperand <= rightOperand;
////                        break;
////                    case ">=":
////                        ruleActive = leftOperand >= rightOperand;
////                        break;
////                    case "==":
////                        ruleActive = leftOperand == rightOperand;
////                        break;
////                    case "!=":
////                        ruleActive = leftOperand != rightOperand;
////                        break;
////                }
////
////
////            }
////
////            if (rule.equals("maintain") && BungeePlugin.maintainMode) {
////                ruleActive = true;
////            }
////
////            if (ruleActive) {
////                spigotConfigObject = BungeePlugin.otherConfig.get(config);
////            }
////
////        }
//
//
//
//        Configuration configuration = spigotConfigObject.configuration;
//        String configName = spigotConfigObject.configName;
//
//
//
//        // 获取信息对象
//        HandlerList ping = e.getHandlers(); // 获取 PingList 对象
//        ServerPing.Players players = ping.getPlayers(); // 获取 玩家信息 对象
//        ServerPing.Protocol protocol = ping.getVersion(); // 获取 版本信息 对象
////        ServerPing.ModInfo modInfo = ping.getModinfo(); // 获取 模组信息 对象
//
//
//        // 判断是否开启版本修改
//        boolean enableVersion = configuration.getBoolean("version.enabled");
//        if (enableVersion) {
//            // 修改版本名
//            String version = configuration.getString("version.name");
//            if (!version.equals("default")) protocol.setName(version);
//
//            // 修改协议版本
//            String protocolStr = configuration.getString("version.protocol");
//            if (!protocolStr.equals("default")) {
//                if (protocolStr.equals("client")) {
//                    protocol.setProtocol(e.getConnection().getVersion());
//                } else if (protocolStr.equals("max")) {
//                    protocol.setProtocol(Integer.MAX_VALUE);
//                } else {
//                    protocol.setProtocol(configuration.getInt("version.protocol"));
//                }
//            }
//        }
//
//        // 判断是否开启玩家数量修改
//        boolean enablePlayerCount = configuration.getBoolean("player.count-enabled");
//        if (enablePlayerCount) {
//            // 修改最大玩家数量
//            String maxPlayerCount = configuration.getString("player.max");
//            if (!maxPlayerCount.equals("default")) players.setMax(configuration.getInt("player.max"));
//
//            // 修改在线玩家数量
//            String onlinePlayerCount = configuration.getString("player.online");
//            if (!onlinePlayerCount.equals("default")) players.setMax(configuration.getInt("player.online"));
//        }
//
//        // 判断是否开启玩家列表修改
//        boolean enablePlayerList = configuration.getBoolean("player.list-enabled");
//        if (enablePlayerList) {
//            List<?> playerList = configuration.getList("player.list");
//            ServerPing.PlayerInfo[] playerInfos = new ServerPing.PlayerInfo[playerList.size()];
//            for (int i = 0; i < playerList.size(); i++) {
//                playerInfos[i] = new ServerPing.PlayerInfo(playerList.get(i).toString(), UUID.randomUUID());
//            }
//            players.setSample(playerInfos);
//        }else {
//            int listPlayerCount = configuration.getInt("player.list-player");
//            if (listPlayerCount > 0) {
//                Collection<ProxiedPlayer> playerList = BungeePlugin.instance.getProxy().getPlayers();
//                ServerPing.PlayerInfo[] playerInfos = new ServerPing.PlayerInfo[playerList.size()];
//
//                if (playerList.size() < listPlayerCount) listPlayerCount = playerList.size();
//                int i = 0;
//                for (ProxiedPlayer player: playerList) {
//                    if (i >= listPlayerCount) break;
//
//                    String playerName = player.getDisplayName();
//                    UUID playerId = player.getUniqueId();
//                    playerInfos[i] = new ServerPing.PlayerInfo(playerName, playerId);
//
//                    i++;
//                }
//
//
//                players.setSample(playerInfos);
//            }
//        }
//
//
//        // 判断是否开启图标替换
//        boolean enableIcon = configuration.getBoolean("icon");
//        if (enableIcon) {
//            // 实现图标替换功能
//            BufferedImage image = ImageFile.get(SpigotPlugin.instance.getDataFolder(),configName);
//            if (image != null) {
//                CachedServerIcon favicon = ServerI;
//                e.setServerIcon();
//            }
//        }
//
////
////        boolean enableMotd = configuration.getBoolean("motd.enabled");
////        if (enableMotd) {
////            boolean enableComponent = configuration.getBoolean("motd.component-mode");
////            List<?> motdList = configuration.getList("motd.list");
////            String motdLine1 = (String) motdList.get(0);
////            String motdLine2 = (String) motdList.get(1);
////            List<String> motdListString = new ArrayList<>();
////            motdListString.add(motdLine1);
////            motdListString.add(motdLine2);
////            if (enableComponent) {
////                BaseComponent baseComponent = new TextComponent();
////                List<CrapeComponent> components = ComponentMotd.parse(motdListString);
////
////                LoginListener.resultComponent(baseComponent, components);
////                ping.setDescriptionComponent(baseComponent);
////            }else {
////                String motdResult = motdLine1 + "\n" + motdLine2;
////                ping.setDescription(motdResult);
////            }
////        }
//
//        // 设置返回PingList为修改后的对象
////        e.setResponse(ping);
//    }
//}
