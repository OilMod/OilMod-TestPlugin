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
public class BackpackSackItem extends OilItem {
    public BackpackSackItem(OilMod mod) {
        super(mod.createKey("backpack_sack"), Material.BOAT,  "Backpack Sack");//defines Backpack Sack item
    }
}
