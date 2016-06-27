package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.items.ItemRegistry;
import de.sirati97.oilmod.api.test.backpack.BackpackItem;
import de.sirati97.oilmod.api.test.magic.ArrowWandItem;
import de.sirati97.oilmod.api.test.magic.ReplaceWandItem;
import de.sirati97.oilmod.api.test.magic.VisBottleItem;
import de.sirati97.oilmod.api.test.magic2.ArrowWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.OreMagnetWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.ReplaceWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.WandItem;
import de.sirati97.oilmod.api.test.ui.InvseeUIBuilder;
import de.sirati97.oilmod.api.test.ui.TestUIBuilder;
import de.sirati97.oilmod.api.util.WeakReferenceTicker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.StringReader;
import java.util.Random;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class TestPlugin extends JavaPlugin {
    public static final Random rnd = new Random();
    private ItemRegistry itemRegistry;
    private TestItem testItem;
    private BackpackItem backpackItem;
    private FurnacePowderItem furnacePowderItem;
    private CraftingBackpackItem craftingBackpackItem;
    private WeakReferenceTicker ticker;
    private static TestPlugin instance;
    private TestUIBuilder testUIBuilder = new TestUIBuilder();
    private InvseeUIBuilder invseeUIBuilder = new InvseeUIBuilder();

    @Override
    public void onEnable() {
        instance = this;
        ticker = new WeakReferenceTicker(this, 1, 20);
        itemRegistry = new ItemRegistry("oiltest");
        itemRegistry.register(testItem = new TestItem());
        itemRegistry.register(furnacePowderItem = new FurnacePowderItem());
        itemRegistry.register(craftingBackpackItem = new CraftingBackpackItem());
        itemRegistry.register(new ReplaceWandItem());
        itemRegistry.register(new VisBottleItem());
        itemRegistry.register(new ArrowWandItem());
        BackpackItem.registerBackpacks(itemRegistry);
        itemRegistry.register(new WandItem());
        itemRegistry.register(new ArrowWandforcyItem());
        itemRegistry.register(new ReplaceWandforcyItem());
        itemRegistry.register(new OreMagnetWandforcyItem());
        getCommand("invsee").setExecutor(new InvseeCommand());
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
                } else if (args[0].equalsIgnoreCase("backpack")) {
                    ItemStack item = backpackItem.createItemStack(player, 1);
                    player.getInventory().addItem(item);
                } else if (args[0].equalsIgnoreCase("oven")) {
                    ItemStack item = furnacePowderItem.createItemStack(player, 1);
                    player.getInventory().addItem(item);
                } else if (args[0].equalsIgnoreCase("craft")) {
                    ItemStack item = craftingBackpackItem.createItemStack(player, 1);
                    player.getInventory().addItem(item);
                } else if (args[0].equalsIgnoreCase("ui")) {
                    testUIBuilder.displayNewUI(player);
                } else if (args[0].equalsIgnoreCase("yaml")) {
                    YamlConfiguration configuration = new YamlConfiguration();
                    configuration.set("item", player.getInventory().getItemInMainHand());
                    System.out.println(configuration.saveToString());
                    configuration = YamlConfiguration.loadConfiguration(new StringReader(configuration.saveToString()));
                    ItemStack itemStack = configuration.getItemStack("item");
                    player.getInventory().addItem(itemStack);
                } else if (args[0].equalsIgnoreCase("gc")) {
                    System.gc();
                }
            }
        }
        return true;
    }

    public WeakReferenceTicker getTicker() {
        return ticker;
    }

    public static TestPlugin getInstance() {
        return instance;
    }



    public static void printTrace(String text) {
        System.out.println("Printing stack trace for " + text + ":");
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 2; i < elements.length; i++) {
            StackTraceElement s = elements[i];
            System.out.println("\tat " + s.getClassName() + "." + s.getMethodName()
                    + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
        }
    }
}
