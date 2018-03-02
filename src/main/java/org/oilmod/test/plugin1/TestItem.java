package org.oilmod.test.plugin1;

import org.oilmod.api.items.OilItemBase;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class TestItem extends OilItemBase {
    public TestItem() {
        super("TestItem", Material.STICK, 0, 2, "TestItem");
    }

    @Override
    public boolean onUseOnBlock(OilItemStack itemStack, Player player, Action action, Block blockClicked, BlockFace blockFace) {
        Block block = blockClicked.getRelative(blockFace);
        if (block.getType().equals(Material.AIR)) {
            block.setType(Material.FIRE);
        }
        return true;
    }

    @Override
    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        player.sendMessage("You used a custom item!");
        return false;
    }
}
