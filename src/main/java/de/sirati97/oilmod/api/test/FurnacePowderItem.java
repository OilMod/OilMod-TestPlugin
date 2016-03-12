package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class FurnacePowderItem extends OilItemBase {
    public FurnacePowderItem() {
        super(Material.BLAZE_POWDER, 0, 3, 1, "Furnace Powder");
    }

    @Override
    public OilItemStack createOilStack(NMSItemStack nmsItemStack) {
        return new FurnacePowderItemStack(nmsItemStack, this);
    }
}
