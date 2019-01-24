package org.oilmod.test.demo.basicitem;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.items.type.ITool;
import org.oilmod.api.util.InteractionResult;
import org.oilmod.api.util.OilKey;

public class BasicExampleItem extends OilItem implements ITool { //Base class for all modded Items (Items=/=ItemStacks)

    public BasicExampleItem(OilKey key) {
        super(key, Material.STICK, "Basic Example Item");
        //VanillaSees=Material.STICK, MaterialData=0, MaxStackSize=2, DisplayName="Basic Example Item"
    }

    @Override
    public int getMaxStackSize() {
        return 2;
    }

    @Override
    public InteractionResult onItemUseOnBlock(OilItemStack stack, HumanEntity human, Location loc, boolean offhand, BlockFace blockFace, float hitX, float hitY, float hitZ) {
        Block block = loc.getBlock().getRelative(blockFace);
        if (block.getType().equals(Material.AIR)) {
            block.setType(Material.FIRE); //will set the clicked block face on fire
        }
        return InteractionResult.SUCCESS;
    }
}