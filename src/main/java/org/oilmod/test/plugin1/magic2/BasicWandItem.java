package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import org.bukkit.Material;
import org.oilmod.api.items.type.ITool;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-TestPlugin.
 */
public class BasicWandItem extends OilItem implements ITool {
    public BasicWandItem(OilMod mod) {
        super(mod.createKey("wand_basic"), Material.BLAZE_ROD, "Basic Wand");
    }

    @Override
    protected BasicWandItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new BasicWandItemStack(nmsItemStack, this);
    }
}
