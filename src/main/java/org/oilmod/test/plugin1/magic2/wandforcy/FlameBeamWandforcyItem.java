package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.items.NMSItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class FlameBeamWandforcyItem extends WandforcyItemBase<FlameBeamWandforcyItemStack> {
    public FlameBeamWandforcyItem() {
        super("FlameBeamWandforcy", "Flame Beam");
    }

    @Override
    public FlameBeamWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new FlameBeamWandforcyItemStack(nmsItemStack, this);
    }

}
