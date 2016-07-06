package de.sirati97.oilmod.api.test.magic2.wandforcy;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemStack;
import de.sirati97.oilmod.api.test.magic2.Wand;
import de.sirati97.oilmod.api.test.magic2.Wandforcy;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static de.sirati97.oilmod.api.test.InventoryUtil.dropAll;

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
    public boolean onUse(Player player, Action action) {
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof Wand) {
                Wand wand = (Wand) ((OilBukkitItemStack) itemStack).getOilItemStack();
                if (wand.getWandforcy()==null) {
                    wand.setWandforcy(this);
                    player.getInventory().remove(asBukkitItemStack());
                    player.sendMessage("Inserted " + getCurrentDisplayName() + " into §a" + wand.getCurrentDisplayName());
                    return true;
                }
            }
        }
        player.sendMessage("Cannot find empty wand in hotbar.");
        return true;
    }


    @Override
    public ItemStack asItemStack() {
        return asBukkitItemStack();
    }

    @Override
    public final boolean canCombineAnvil(ItemStack itemStack, HumanEntity human) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof WandforcyItemStackBase && checkClass((WandforcyItemStackBase) ((OilBukkitItemStack) itemStack).getOilItemStack());
    }

    protected abstract boolean checkClass(WandforcyItemStackBase itemStack);

    @Override
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
    }


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
}
