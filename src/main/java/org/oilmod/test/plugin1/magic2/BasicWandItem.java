package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItemBase;
import org.bukkit.Material;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-TestPlugin.
 */
public class BasicWandItem extends OilItemBase<BasicWandItemStack> {
    public BasicWandItem() {
        super("BasicWand", Material.BLAZE_ROD, 0, 1, "Basic Wand");
    }

    @Override
    protected BasicWandItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new BasicWandItemStack(nmsItemStack, this);
    }
}
