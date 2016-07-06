package de.sirati97.oilmod.api.test.magic2.wandforcy;

import de.sirati97.oilmod.api.items.NMSItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class DamageBeamWandforcyItem extends WandforcyItemBase<DamageBeamWandforcyItemStack> {
    public DamageBeamWandforcyItem() {
        super("DamageBeamWandforcy", "Damage Beam");
    }

    @Override
    public DamageBeamWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new DamageBeamWandforcyItemStack(nmsItemStack, this);
    }

}
