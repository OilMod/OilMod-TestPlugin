package org.oilmod.test.plugin1;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemRegistry;
import org.oilmod.test.plugin1.backpack.BackpackItem;
import org.oilmod.test.plugin1.magic2.MagicUtil;
import org.oilmod.test.plugin1.magic2.node.NodeManager;
import org.oilmod.test.plugin1.ui.TestUIBuilder;
import org.oilmod.api.util.WeakReferenceTicker;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.FurnaceBurnEvent;
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
    private FurnacePowderItem furnacePowderItem;
    private CraftingBackpackItem craftingBackpackItem;
    private WeakReferenceTicker ticker;
    private static TestPlugin instance;
    private TestUIBuilder testUIBuilder = new TestUIBuilder();
    private NodeManager nodeManager;
    private OilMod mod;

    @Override
    public void onEnable() {
        instance = this;
        nodeManager = new NodeManager();
        nodeManager.init(this);
        ticker = new WeakReferenceTicker(this, 1, 20);
        mod = new OilMod("oiltest");
        itemRegistry = new ItemRegistry(mod);
        itemRegistry.register(testItem = new TestItem());
        itemRegistry.register(furnacePowderItem = new FurnacePowderItem());
        itemRegistry.register(craftingBackpackItem = new CraftingBackpackItem());
        BackpackItem.registerBackpacks(itemRegistry);
        MagicUtil.register(itemRegistry);
        getCommand("invsee").setExecutor(new InvseeCommand());
    }

    @Override
    public void onDisable() {
        nodeManager.dispose();
    }

    @Override
    public boolean onCommand(CommandSender sender, final Command command, final String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("oilmodtest") && args.length > 0) {
                if (args[0].equalsIgnoreCase("testitem")) {
                    for (int i=0;i<5;i++) {
                        ItemStack item = testItem.createItemStack(player, 1);
                        player.getInventory().addItem(item);
                    }
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
                } else if (args[0].equalsIgnoreCase("particle")) {
                    if (args.length<3) {
                        player.sendMessage("/" + label + " particle [particle] [data/id]");
                    } else {
                        final boolean mode = args[2].equalsIgnoreCase("data");
                        Effect effect = Effect.getByName(args[1]);

                        if (effect == null) {
                            effect = Effect.valueOf(args[1]);
                        }
                        final Location loc = player.getEyeLocation();
                        final Effect finalEffect = effect;
                        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                            final World w = loc.getWorld();
                            int counter = 0;
                            int i=0;
                            @Override
                            public void run() {
                                w.spigot().playEffect(loc, finalEffect, mode ? 0 : counter, mode ? counter : 0, 0,0,0, (float) 0.2, 1, 30);
                                i++;
                                if (i == 10) {
                                    i = 0;
                                    counter++;
                                }
                                if (counter < 1000) {
                                    Bukkit.getScheduler().runTaskLater(TestPlugin.this, this, 2);
                                }
                            }
                        }, 30);
                    }




                } else if (args[0].equalsIgnoreCase("moreItems")) {
                    player.getInventory().getItemInMainHand().setAmount(64);
                } else if (args[0].equalsIgnoreCase("spawnNode")) {
                    nodeManager.spawnNode(player.getEyeLocation());
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

    public static NodeManager getNodeManager() {
        return instance.nodeManager;
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
