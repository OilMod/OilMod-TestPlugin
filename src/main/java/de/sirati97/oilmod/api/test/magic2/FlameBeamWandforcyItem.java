package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class FlameBeamWandforcyItem extends OilItemBase {
    public FlameBeamWandforcyItem() {
        super(Material.FIREWORK_CHARGE, 0, "FlameBeamWandforcy", 1, "Flame Beam Wandforcy");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new FlameBeamWandforcyItemStack(nmsItemStack, this);
    }

}
