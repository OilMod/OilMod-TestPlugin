package de.sirati97.oilmod.api.test.magic;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class CoffinItem extends OilItemBase<CoffinItemStack>{

    public CoffinItem() {
        super(Material.ENDER_CHEST, 0, "Coffin", 64, "Coffin");
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
