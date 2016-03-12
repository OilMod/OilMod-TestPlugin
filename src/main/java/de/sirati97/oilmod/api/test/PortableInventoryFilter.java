package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.inventory.ItemFilter;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 13.02.2016.
 */
public class PortableInventoryFilter implements ItemFilter {
    public final static PortableInventoryFilter INSTANCE = new PortableInventoryFilter();
    @Override
    public boolean allowed(ItemStack itemStack) {
        if (itemStack instanceof OilBukkitItemStack) {
            OilItemStack oilItemStack = ((OilBukkitItemStack) itemStack).getOilItemStack();
            return !(oilItemStack instanceof BackpackItemStack || oilItemStack instanceof FurnacePowderItemStack || oilItemStack instanceof CraftingBackpackItemStack || oilItemStack instanceof ReplaceWandItemStack);
        }
        return true;
    }
}
