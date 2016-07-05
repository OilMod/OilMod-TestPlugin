package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ItemMagnetWandforcyItem extends OilItemBase {
    public ItemMagnetWandforcyItem() {
        super(Material.FIREWORK_CHARGE, 0, "ItemMagnetWandforcy", 1, "Item Magnet Wandforcy");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new ItemMagnetWandforcyItemStack(nmsItemStack, this);
    }
}
