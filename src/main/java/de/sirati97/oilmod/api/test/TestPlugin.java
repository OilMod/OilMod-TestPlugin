package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.items.ItemRegister;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class TestPlugin extends JavaPlugin {
    private ItemRegister itemRegister;
    private TestItem testItem;
    private BackpackItem backpackItem;

    @Override
    public void onEnable() {
        itemRegister = new ItemRegister("oiltst");
        itemRegister.init();
        itemRegister.register(testItem = new TestItem());
        itemRegister.register(backpackItem = new BackpackItem());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("oilmodtest") && args.length > 0) {
                if (args[0].equalsIgnoreCase("testitem")) {
                    for (int i=0;i<5;i++) {
                        ItemStack item = testItem.createItemStack(player, 1);
                        player.getInventory().addItem(item);
                    }
                }
                if (args[0].equalsIgnoreCase("backpack")) {
                    ItemStack item = backpackItem.createItemStack(player, 1);
                    player.getInventory().addItem(item);
                }
            }
        }
        return true;
    }
}
