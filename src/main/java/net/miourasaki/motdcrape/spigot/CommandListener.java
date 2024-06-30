package net.miourasaki.motdcrape.spigot;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.miourasaki.motdcrape.BungeePlugin;

public class CommandListener extends Command {

    public CommandListener() {
        super("motdcrape");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length >= 1) {
            if (sender instanceof ProxiedPlayer) {

                if (sender.hasPermission("motdcrape.admin")) {
                    switch (args[0]) {
                        case "reload":
                            ReloadConfiguration.reload();
                            sender.sendMessage("Reloaded configuration successfully.");
                            break;
                        case "debug":
                        case "maintain":
                            EnableMaintainMode.change();
                            if (BungeePlugin.maintainMode) {
                                sender.sendMessage("Enabled maintain mode.");

                            }else {
                                sender.sendMessage("Disabled maintain mode.");
                            }
                            break;
                    }
                }else {
                    sender.sendMessage("Unknown command. Type \"help\" for help.");
                }

            }else {
                switch (args[0]) {
                    case "reload":
                        ReloadConfiguration.reload();
                        break;
                    case "debug":
                    case "maintain":
                        EnableMaintainMode.change();
                        break;
                }
            }


        }



    }


}
