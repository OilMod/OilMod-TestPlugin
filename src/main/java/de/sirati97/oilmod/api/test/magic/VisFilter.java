package de.sirati97.oilmod.api.test.magic;

import de.sirati97.oilmod.api.inventory.ItemFilter;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 13.02.2016.
 */
public class VisFilter implements ItemFilter {
    public final static VisFilter INSTANCE = new VisFilter();
    @Override
    public boolean allowed(ItemStack itemStack) {
        if (itemStack instanceof OilBukkitItemStack) {
            return ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof TransportableVisHolder;
        }
        return false;
    }
}
