package de.sirati97.oilmod.api.test.magic2.wandforcy;

import de.sirati97.oilmod.api.items.NMSItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class SniperWandforcyItem extends WandforcyItemBase<SniperWandforcyItemStack> {
    public SniperWandforcyItem() {
        super("SniperWandforcy", "Sniper");
    }

    @Override
    public SniperWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new SniperWandforcyItemStack(nmsItemStack, this);
    }

}
