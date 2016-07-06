package de.sirati97.oilmod.api.test.magic2.wandforcy;

import de.sirati97.oilmod.api.items.NMSItemStack;

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
