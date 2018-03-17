package org.oilmod.test.plugin1.magic;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class CoffinItem extends OilItem<CoffinItemStack> {

    public CoffinItem(OilMod mod) {
        super(mod.createKey("coffin"), itemType, Material.ENDER_CHEST, 0, 64, "Coffin");
    }

    @Override
    public CoffinItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new CoffinItemStack(nmsItemStack, this);
    }

    @Override
    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return true;
    }

}
