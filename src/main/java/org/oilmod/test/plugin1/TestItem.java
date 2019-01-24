package org.oilmod.test.plugin1;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.oilmod.api.items.type.ITool;
import org.oilmod.api.util.InteractionResult;

import static org.oilmod.api.util.InteractionResult.SUCCESS;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class TestItem extends OilItem implements ITool {
    public TestItem(OilMod mod) {
        super(mod.createKey("test_item"), Material.STICK, "TestItem");
    }

    @Override
    public int getMaxStackSize() {
        return 2;
    }

    @Override
    public ItemInteractionResult onItemRightClick(OilItemStack stack, World world, HumanEntity human, boolean offhand) {
        human.sendMessage("You used a custom item!");
        return new ItemInteractionResult(SUCCESS, stack.asBukkitItemStack());
    }

    @Override
    public InteractionResult onItemUseOnBlock(OilItemStack stack, HumanEntity human, Location loc, boolean offhand, BlockFace blockFace, float hitX, float hitY, float hitZ) {
        Block block = loc.getBlock().getRelative(blockFace);
        if (block.getType().equals(Material.AIR)) {
            block.setType(Material.FIRE);
        }
        return SUCCESS;
    }
}
