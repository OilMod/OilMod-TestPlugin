package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.inventory.ItemFilter;
import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.test.plugin1.magic2.wandforcy.WandforcyItemStackBase;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 13.02.2016.
 */
public class WandforcyFilter implements ItemFilter {
    public final static WandforcyFilter INSTANCE = new WandforcyFilter();
    @Override
    public boolean allowed(ItemStack itemStack) {
        if (itemStack instanceof OilBukkitItemStack) {
            return ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof WandforcyItemStackBase;
        }
        return false;
    }
}
