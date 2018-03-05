package org.oilmod.test.plugin1;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class FurnacePowderItem extends OilItem {
    public FurnacePowderItem(OilMod mod) {
        super(mod.createKey("furnace_powder"), Material.BLAZE_POWDER, 0, 1, "Furnace Powder");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new FurnacePowderItemStack(nmsItemStack, this);
    }
}
