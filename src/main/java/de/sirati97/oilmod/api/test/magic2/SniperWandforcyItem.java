package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class SniperWandforcyItem extends OilItemBase {
    public SniperWandforcyItem() {
        super(Material.FIREWORK_CHARGE, 0, "SniperWandforcy", 1, "Sniper Wandforcy");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new SniperWandforcyItemStack(nmsItemStack, this);
    }

}
