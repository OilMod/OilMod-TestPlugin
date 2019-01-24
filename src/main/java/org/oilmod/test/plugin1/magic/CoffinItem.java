package org.oilmod.test.plugin1.magic;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.oilmod.api.items.type.IUnique;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class CoffinItem extends OilItem implements IUnique {

    public CoffinItem(OilMod mod) {
        super(mod.createKey("coffin"), Material.ENDER_CHEST, "Coffin");
    }

    @Override
    public CoffinItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new CoffinItemStack(nmsItemStack, this);
    }


}
