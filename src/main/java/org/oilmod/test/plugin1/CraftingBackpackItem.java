package org.oilmod.test.plugin1;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class CraftingBackpackItem extends OilItem {
    public CraftingBackpackItem(OilMod mod) {
        super(mod.createKey("CraftingBackpack"), Material.LEATHER, 0, 1, "Crafting Backpack");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new CraftingBackpackItemstack(nmsItemStack, this);
    }
}
