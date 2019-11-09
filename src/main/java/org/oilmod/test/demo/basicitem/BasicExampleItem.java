package org.oilmod.test.demo.basicitem;

import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.items.type.ITool;
import org.oilmod.api.rep.block.BlockFaceRep;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.world.LocationBlockRep;
import org.oilmod.api.util.InteractionResult;
import org.oilmod.api.util.OilKey;

import static org.oilmod.api.rep.providers.minecraft.MinecraftBlock.AIR;
import static org.oilmod.api.rep.providers.minecraft.MinecraftBlock.FIRE;
import static org.oilmod.api.rep.providers.minecraft.MinecraftItem.STICK;

public class BasicExampleItem extends OilItem implements ITool { //Base class for all modded Items (Items=/=ItemStacks)

    public BasicExampleItem(OilKey key) {
        super(key, STICK, "Basic Example Item");
        //VanillaSees=Material.STICK, MaterialData=0, MaxStackSize=2, DisplayName="Basic Example Item"
    }

    @Override
    public int getMaxStackSize() {
        return 2;
    }

    @Override
    public InteractionResult onItemUseOnBlock(OilItemStack stack, EntityHumanRep human, LocationBlockRep loc, boolean offhand, BlockFaceRep blockFace, float hitX, float hitY, float hitZ) {
        LocationBlockRep fireLoc = loc.getRelative(blockFace);

        BlockStateRep block = fireLoc.getBlock();
        if (block.isSame(AIR)) {
            fireLoc.setBlock(FIRE); //will set the clicked block face on fire
        }
        return InteractionResult.SUCCESS;
    }
}