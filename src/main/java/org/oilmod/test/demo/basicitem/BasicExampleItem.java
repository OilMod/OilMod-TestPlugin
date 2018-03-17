package org.oilmod.test.demo.basicitem;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.util.OilKey;

public class BasicExampleItem extends OilItem { //Base class for all modded Items (Items=/=ItemStacks)

    public BasicExampleItem(OilKey key) {
        super(key, itemType, Material.STICK, 0, 2, "Basic Example Item");
        //VanillaSees=Material.STICK, MaterialData=0, MaxStackSize=2, DisplayName="Basic Example Item"
    }

    @Override
    public boolean onUseOnBlock(OilItemStack itemStack, Player player, Action action, Block blockClicked, BlockFace blockFace) {
        Block block = blockClicked.getRelative(blockFace);
        if (block.getType().equals(Material.AIR)) {
            block.setType(Material.FIRE); //will set the clicked block face on fire
        }
        return true;
    }
}