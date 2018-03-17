package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-TestPlugin.
 */
public class VisContainerItem extends OilItem {
    public VisContainerItem(OilMod mod) {
        super(mod.createKey("vis_container"), itemType, Material.END_CRYSTAL, 0, "Vis Container");
    }

    @Override
    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return true;
    }
}
