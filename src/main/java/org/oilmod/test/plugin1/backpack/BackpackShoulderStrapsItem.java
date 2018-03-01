package org.oilmod.test.plugin1.backpack;

import org.oilmod.api.items.OilItemBase;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class BackpackShoulderStrapsItem extends OilItemBase {
    public BackpackShoulderStrapsItem() {
        super(Material.EMPTY_MAP, 0, "BackpackShoulderStraps", 64, "Backpack Shoulder Straps");//defines Backpack Shoulder Strap item
    }

    @Override
    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return true;
    }
}
