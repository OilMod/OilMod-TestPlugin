package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class LifestealBeamWandforcyItem extends OilItemBase {
    public LifestealBeamWandforcyItem() {
        super(Material.FIREWORK_CHARGE, 0, "LifestealBeamWandforcy", 1, "Lifesteal Beam Wandforcy");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new LifestealBeamWandforcyItemStack(nmsItemStack, this);
    }

}
