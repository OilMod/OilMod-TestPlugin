package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class CraftingBackpackItem extends OilItemBase {
    public CraftingBackpackItem() {
        super(Material.LEATHER, 0, 4, 1, "Crafting Backpack");
    }

    @Override
    public OilItemStack createOilStack(NMSItemStack nmsItemStack) {
        return new CraftingBackpackItemStack(nmsItemStack, this);
    }
}
