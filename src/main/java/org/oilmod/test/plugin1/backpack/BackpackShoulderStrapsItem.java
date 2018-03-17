package org.oilmod.test.plugin1.backpack;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class BackpackShoulderStrapsItem extends OilItem {
    public BackpackShoulderStrapsItem(OilMod mod) {
        super(mod.createKey("backpack_shoulder_straps"), itemType, Material.EMPTY_MAP, 0, 64, "Backpack Shoulder Straps");//defines Backpack Shoulder Strap item
    }

    @Override
    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return true;
    }
}
