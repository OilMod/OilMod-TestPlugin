package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.items.NMSItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ItemMagnetWandforcyItem extends WandforcyItemBase<ItemMagnetWandforcyItemStack> {
    public ItemMagnetWandforcyItem() {
        super("ItemMagnetWandforcy", "Item Magnet");
    }

    @Override
    public ItemMagnetWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new ItemMagnetWandforcyItemStack(nmsItemStack, this);
    }
}
