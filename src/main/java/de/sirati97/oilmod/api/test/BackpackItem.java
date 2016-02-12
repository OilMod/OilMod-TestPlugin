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
    public OilItemStack createOilStack(NMSItemStack nmsItemStack) {
        return new BackpackItemstack(nmsItemStack, this);
    }
}
