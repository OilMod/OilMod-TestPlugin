package org.oilmod.test.plugin1;

import org.oilmod.test.plugin1.ui.InvseeUIBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by sirati97 on 19.06.2016 for OilMod-TestPlugin.
 */
public class InvseeCommand implements CommandExecutor {
    private InvseeUIBuilder invseeUIBuilder = new InvseeUIBuilder();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "/"+label + " <player>");
            } else {
                Player other = Bukkit.getPlayer(args[0]);
                if (other == null) {
                    player.sendMessage(ChatColor.RED + "Cannot find player with name " + args[0]);
                } else if (player == other) {
                    player.sendMessage(ChatColor.RED + "You must not look into your own inventory");
                    //prevents item duplication - nms bug
                } else {
                    invseeUIBuilder.displayNewUI(player, other);
                }
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "This command can only be executed by players.");
        }
        return true;
    }
}
