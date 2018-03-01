package org.oilmod.test.plugin1;

import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItemBase;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class CraftingBackpackItem extends OilItemBase {
    public CraftingBackpackItem() {
        super(Material.LEATHER, 0, "CraftingBackpack", 1, "Crafting Backpack");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new CraftingBackpackItemstack(nmsItemStack, this);
    }
}
