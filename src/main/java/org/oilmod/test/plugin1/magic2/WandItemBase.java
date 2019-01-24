package org.oilmod.test.plugin1.magic2;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.items.type.ITool;
import org.oilmod.api.util.InteractionResult;
import org.oilmod.api.util.OilKey;
import org.oilmod.test.plugin1.magic2.ui.WandUIBuilder;

import java.util.List;

import static org.oilmod.api.util.InteractionResult.*;
import static org.oilmod.test.plugin1.InventoryUtil.dropAll;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-TestPlugin.
 */
public class WandItemBase extends OilItem implements ITool {

    /**
     * @param key             Mod unique key that identifies the item
     * @param vanillaMaterial The Vanilla Material that is shown to the client
     * @param displayName     displayed displayName of the item
     */
    public WandItemBase(OilKey key, Material vanillaMaterial, String displayName) {
        super(key, vanillaMaterial, displayName);
    }

    @Override
    public InteractionResult onItemUseOnBlock(OilItemStack stack, HumanEntity human, Location pos, boolean offhand, BlockFace blockFace, float hitX, float hitY, float hitZ) {

        WandItemStackBase wand = (WandItemStackBase) stack;
        Wandforcy wandforcy = wand.getWandforcy();
        Player player = ((Player)human);
        if (!player.isSneaking() && wandforcy != null) {
            wandforcy.onWandUseOnBlock(wand, player, Action.RIGHT_CLICK_BLOCK, pos.getBlock(), blockFace);
            return SUCCESS;
        }
        return PASS;
    }

    @Override
    public ItemInteractionResult onItemRightClick(OilItemStack stack, World world, HumanEntity human, boolean offhand) {
        Player player = ((Player)human);
        WandItemStackBase wand = (WandItemStackBase) stack;

        if (player.isSneaking()) {
            WandUIBuilder.INSTANCE.displayNewUI(player, wand);
        } else {
            Wandforcy wandforcy = wand.getWandforcy();
            if (wandforcy == null) {
                wand.onNoWandforcyUse(player);
            } else {
                wandforcy.onWandUse(wand, player, Action.RIGHT_CLICK_AIR);
            }
        }
        return new ItemInteractionResult(SUCCESS, stack.asBukkitItemStack());
    }

    @Override
    public ItemInteractionResult onItemLeftClick(OilItemStack stack, World world, HumanEntity human, boolean offhand) {

        WandItemStackBase wand = (WandItemStackBase) stack;
        Player player = ((Player)human);
        boolean result = false;
        if (player.isSneaking()) {
            wand.onEjectWandforcy(player);
            result = true;
        } else {
            Wandforcy wandforcy = wand.getWandforcy();
            if (wandforcy != null) {
                result = wandforcy.onWandLeftClick(wand, player, Action.LEFT_CLICK_AIR);
            }
        }
        return new ItemInteractionResult(result?PASS:SUCCESS, stack.asBukkitItemStack());
    }

    @Override
    public InteractionResult onItemLeftClickOnBlock(OilItemStack stack, HumanEntity human, Location loc, boolean offhand, BlockFace blockFace, float hitX, float hitY, float hitZ) {
        WandItemStackBase wand = (WandItemStackBase) stack;
        Wandforcy wandforcy = wand.getWandforcy();
        Player player = ((Player)human);
        boolean result = false;
        if (!player.isSneaking() && wandforcy != null) {
            result = wandforcy.onWandLeftClickOnBlock(wand, player, Action.LEFT_CLICK_BLOCK, loc.getBlock(), blockFace);
        }
        return result?PASS:SUCCESS;
    }




    /*@Override
    public final void combineAnvil(ItemStack itemStack, HumanEntity human) {
        //noinspection unchecked
        T other = (T) ((OilBukkitItemStack) itemStack).getOilItemStack();
        List<ItemStack> drops= combineWith(other);
        dropAll(drops, human);
    }

    @Override
    public void prepareCombineAnvil(ItemStack itemStack, HumanEntity human) {
        //noinspection unchecked
        T other = (T) ((OilBukkitItemStack) itemStack).getOilItemStack();
        setVis(getVis()+other.getVis());
    }


    @Override
    public final boolean canCombineAnvil(ItemStack itemStack, HumanEntity human) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof WandItemStackBase && checkClass((WandItemStackBase) ((OilBukkitItemStack) itemStack).getOilItemStack());
    }*/ //TODO: readd
}
