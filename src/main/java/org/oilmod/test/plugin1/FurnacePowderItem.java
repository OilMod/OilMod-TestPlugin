package org.oilmod.test.plugin1;

import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.oilmod.api.items.type.IUnique;
import org.oilmod.api.util.InteractionResult;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class FurnacePowderItem extends OilItem implements IUnique {
    public FurnacePowderItem(OilMod mod) {
        super(mod.createKey("furnace_powder"), Material.BLAZE_POWDER, "Furnace Powder");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new FurnacePowderItemStack(nmsItemStack, this);
    }

    @Override
    public ItemInteractionResult onItemRightClick(OilItemStack stack, World world, HumanEntity human, boolean offhand) {
        human.openInventory(stack.getMainInventory().getBukkitInventory());
        return new ItemInteractionResult(InteractionResult.SUCCESS, stack.asBukkitItemStack());
    }
}
