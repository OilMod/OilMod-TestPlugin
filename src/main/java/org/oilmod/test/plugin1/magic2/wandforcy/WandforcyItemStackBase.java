package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.test.plugin1.magic2.Wand;
import org.oilmod.test.plugin1.magic2.Wandforcy;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static org.oilmod.test.plugin1.InventoryUtil.dropAll;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-TestPlugin.
 */
public abstract class WandforcyItemStackBase<T extends WandforcyItemStackBase<T>> extends OilItemStack implements Wandforcy {

    public WandforcyItemStackBase(NMSItemStack nmsItemStack, WandforcyItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public boolean onWandLeftClick(Wand wand, Player player, Action action) {
        return false;
    }

    @Override
    public boolean onWandLeftClickOnBlock(Wand wand, Player player, Action action, Block blockClicked, BlockFace blockFace) {
        return false;
    }

    @Override
    public void onWandUseOnBlock(Wand wand, Player player, Action action, Block blockClicked, BlockFace blockFace) {
    }



    @Override
    public ItemStack asItemStack() {
        return asBukkitItemStack();
    }

    protected abstract boolean checkClass(WandforcyItemStackBase itemStack);


    protected List<ItemStack> combineWith(T other) {
        return new ArrayList<>();
    }

    @Override
    public WandforcyItemBase getItem() {
        return (WandforcyItemBase) super.getItem();
    }

    @Override
    public String getSpellName() {
        return getItem().getWandforcyName();
    }

    @Override
    public String getCurrentDisplayName() {
        return isRenamed()?getRename():getItem().getDisplayName();
    }
}
