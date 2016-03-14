package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class BackpackItem extends OilItemBase {
    public BackpackItem() {
        super(Material.LEATHER, 0, 2, 1, "Backpack");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new BackpackItemStack(nmsItemStack, this);
    }
}
