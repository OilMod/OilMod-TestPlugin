package org.oilmod.test.plugin1;

import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItemBase;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class FurnacePowderItem extends OilItemBase {
    public FurnacePowderItem() {
        super(Material.BLAZE_POWDER, 0, "FurnacePowder", 1, "Furnace Powder");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new FurnacePowderItemStack(nmsItemStack, this);
    }
}
