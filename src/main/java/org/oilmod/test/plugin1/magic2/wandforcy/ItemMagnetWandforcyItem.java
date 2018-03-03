package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ItemMagnetWandforcyItem extends WandforcyItemBase<ItemMagnetWandforcyItemStack> {
    public ItemMagnetWandforcyItem(OilMod mod) {
        super(mod.createKey("ItemMagnetWandforcy"), "Item Magnet");
    }

    @Override
    public ItemMagnetWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new ItemMagnetWandforcyItemStack(nmsItemStack, this);
    }
}
