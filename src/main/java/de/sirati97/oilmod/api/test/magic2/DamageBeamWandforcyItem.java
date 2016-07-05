package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class DamageBeamWandforcyItem extends OilItemBase {
    public DamageBeamWandforcyItem() {
        super(Material.FIREWORK_CHARGE, 0, "DamageBeamWandforcy", 1, "Damage Beam Wandforcy");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new DamageBeamWandforcyItemStack(nmsItemStack, this);
    }

}
