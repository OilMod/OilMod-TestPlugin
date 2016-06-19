package de.sirati97.oilmod.api.test.backpack;

import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class BackpackSackItem extends OilItemBase {
    public BackpackSackItem() {
        super(Material.BOAT, 0, "BackpackSack", 64, "Backpack Sack");//defines Backpack Sack item
    }

    @Override
    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return true;
    }
}
