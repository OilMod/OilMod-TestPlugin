package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class LifestealBeamWandforcyItem extends WandforcyItemBase<LifestealBeamWandforcyItemStack> {
    public LifestealBeamWandforcyItem(OilMod mod) {
        super(mod.createKey("wandforcy_lifesteal_beam"), "Lifesteal Beam");
    }

    @Override
    public LifestealBeamWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new LifestealBeamWandforcyItemStack(nmsItemStack, this);
    }

}
