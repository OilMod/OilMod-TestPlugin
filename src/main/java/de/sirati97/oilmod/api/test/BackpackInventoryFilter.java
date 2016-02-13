package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.inventory.ItemFilter;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 13.02.2016.
 */
public class BackpackInventoryFilter implements ItemFilter {
    public final static BackpackInventoryFilter INSTANCE = new BackpackInventoryFilter();
    @Override
    public boolean allowed(ItemStack itemStack) {
        if (itemStack instanceof OilBukkitItemStack) {
            OilItemStack oilItemStack = ((OilBukkitItemStack) itemStack).getOilItemStack();
            return !(oilItemStack instanceof BackpackItemstack);
        }
        return true;
    }
}
