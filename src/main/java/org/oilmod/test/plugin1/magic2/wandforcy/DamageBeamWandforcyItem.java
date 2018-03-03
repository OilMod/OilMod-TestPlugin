package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class DamageBeamWandforcyItem extends WandforcyItemBase<DamageBeamWandforcyItemStack> {
    public DamageBeamWandforcyItem(OilMod mod) {
        super(mod.createKey("DamageBeamWandforcy"), "Damage Beam");
    }

    @Override
    public DamageBeamWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new DamageBeamWandforcyItemStack(nmsItemStack, this);
    }

}
