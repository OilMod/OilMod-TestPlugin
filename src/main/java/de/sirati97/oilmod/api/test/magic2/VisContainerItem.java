package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-TestPlugin.
 */
public class VisContainerItem extends OilItemBase {
    public VisContainerItem() {
        super(Material.END_CRYSTAL, 0, "VisContainer", "Vis Container");
    }

    @Override
    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return true;
    }
}
