package org.oilmod.test.plugin1;

import org.oilmod.api.inventory.ItemFilter;
import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.test.plugin1.backpack.BackpackItemStack;
import org.oilmod.test.plugin1.magic2.wandforcy.ReplaceWandforcyItemStack;
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
            return !(oilItemStack instanceof BackpackItemStack || oilItemStack instanceof FurnacePowderItemStack || oilItemStack instanceof CraftingBackpackItemstack || oilItemStack instanceof ReplaceWandforcyItemStack);
        }
        return true;
    }
}
