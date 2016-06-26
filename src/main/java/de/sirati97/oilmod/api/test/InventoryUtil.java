package de.sirati97.oilmod.api.test;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public final class InventoryUtil {
    private InventoryUtil() {throw new UnsupportedOperationException();}


    public static void transferInventory(Inventory into, Inventory from, List<ItemStack> drops) {
        for (ItemStack itemStack:from.getContents()) {
            if (itemStack != null) {
                drops.addAll(into.addItem(itemStack).values());
            }
        }
    }

    public static void dropAll(List<ItemStack> drops, HumanEntity human) {
        World w = human.getWorld();
        Location drop = human.getEyeLocation();
        for (ItemStack itemStack:drops) {
            w.dropItem(drop, itemStack);
        }
    }
}
