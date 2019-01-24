package org.oilmod.test.plugin1.magic2.wandforcy;

import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.oilmod.api.items.type.IUnique;
import org.oilmod.api.util.InteractionResult;
import org.oilmod.api.util.OilKey;
import org.oilmod.test.plugin1.magic2.Wand;
import org.oilmod.test.plugin1.magic2.WandItemStackBase;
import org.oilmod.test.plugin1.magic2.Wandforcy;

import java.util.List;

import static org.oilmod.api.util.InteractionResult.PASS;
import static org.oilmod.api.util.InteractionResult.SUCCESS;
import static org.oilmod.test.plugin1.InventoryUtil.dropAll;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-TestPlugin.
 */
public abstract class WandforcyItemBase<T extends OilItemStack> extends OilItem implements IUnique {
    private final String wandforcyName;

    public WandforcyItemBase(OilKey key, String wandforcyName) {
        super(key, Material.FIREWORK_CHARGE, wandforcyName + " Wandforcy");
        this.wandforcyName = wandforcyName;
    }

    public String getWandforcyName() {
        return wandforcyName;
    }


    @Override
    public ItemInteractionResult onItemRightClick(OilItemStack stack, World world, HumanEntity human, boolean offhand) {
        Wandforcy wandforcy = (Wandforcy) stack;
        Player player = ((Player)human);
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof Wand) {
                Wand wand = (Wand) ((OilBukkitItemStack) itemStack).getOilItemStack();
                if (wand.getWandforcy()==null) {
                    wand.setWandforcy(wandforcy);
                    player.getInventory().remove(stack.asBukkitItemStack());
                    player.sendMessage("Inserted " + wandforcy.getCurrentDisplayName() + " into §a" + wand.getCurrentDisplayName());
                    return new ItemInteractionResult(SUCCESS, stack.asBukkitItemStack());
                }
            }
        }
        player.sendMessage("Cannot find empty wand in hotbar.");
        return new ItemInteractionResult(PASS, stack.asBukkitItemStack());
    }

    //TODO: readd when api is readded/check if needed at all lol

    /*@Override
    public final boolean canCombineAnvil(ItemStack itemStack, HumanEntity human) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof WandforcyItemStackBase && checkClass((WandforcyItemStackBase) ((OilBukkitItemStack) itemStack).getOilItemStack());
    }
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
    }*/
}
