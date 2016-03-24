package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.data.IntegerData;
import de.sirati97.oilmod.api.inventory.InventoryFactoryBase;
import de.sirati97.oilmod.api.inventory.ModInventoryObject;
import de.sirati97.oilmod.api.items.ItemDescription;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sirati97 on 11.03.2016.
 */
public abstract class WandItemStackBase<T extends WandItemStackBase> extends OilItemStack implements VisHolder{
    protected final ModInventoryObject visContainer = InventoryFactoryBase.getInstance().createBasicInventory("visCon", this, 5, "Vis Container", VisFilter.INSTANCE);
    protected final IntegerData vis = new IntegerData("visStored", this);

    public WandItemStackBase(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public boolean onUse(Player player, Action action) {
        if (player.isSneaking()) {
            player.openInventory(visContainer.getBukkitInventory());
        }
        return true;
    }

    protected boolean useVis(boolean remove, int visNeeded) {

        if (getVis() >= visNeeded) {
            if (remove) {
                setVis(getVis()-visNeeded);
            }
            return true;
        }
        int visVirtual = getVis();
        Inventory inv = visContainer.getBukkitInventory();
        for (int i=0;i<inv.getSize()&&visVirtual<visNeeded;i++) {
            ItemStack itemStack = inv.getItem(i);
            if (itemStack != null && itemStack instanceof OilBukkitItemStack) {
                OilBukkitItemStack oilBukkitItemStack = (OilBukkitItemStack) itemStack;
                if (oilBukkitItemStack.getOilItemStack() instanceof VisHolder) {
                    VisHolder visHolder = (VisHolder) oilBukkitItemStack.getOilItemStack();
                    int visMissing = visNeeded-visVirtual;
                    int itemsNeeded = visMissing/visHolder.getVis()+((visMissing%visHolder.getVis()==0)?0:1);
                    int itemsConverted = Math.min(itemsNeeded, itemStack.getAmount());
                    visVirtual += itemsConverted * visHolder.getVis();
                    if (remove) {
                        if (itemStack.getAmount()-itemsConverted < 1) {
                            inv.setItem(i, null);
                        } else {
                            itemStack.setAmount(itemStack.getAmount()-itemsConverted);
                        }
                    }
                }
            }
        }
        if (remove) {
            setVis(visVirtual-visNeeded);
        }
        return visVirtual>=visNeeded;
    }

    public int getVis() {
        return vis.getData();
    }

    public void setVis(int vis) {
        ItemDescription description = getItemDescription();
        description.setLine(0, getVisStoredDescriptionLine(vis), true);
        this.vis.setData(vis);
    }

    @Override
    public final void combineAnvil(ItemStack itemStack, HumanEntity human) {
        T other = (T) ((OilBukkitItemStack) itemStack).getOilItemStack();
        List<ItemStack> drops= combineWith(other);
        dropAll(drops, human);
    }

    @Override
    public void prepareCombineAnvil(ItemStack itemStack, HumanEntity human) {
        T other = (T) ((OilBukkitItemStack) itemStack).getOilItemStack();
        setVis(getVis()+other.getVis());
    }

    protected String getVisStoredDescriptionLine(int vis) {
        return "Stores " + vis + " Vis.";
    }

    @Override
    protected List<String> createDescription() {
        return Arrays.asList(getVisStoredDescriptionLine(vis.getData()));
    }

    protected List<ItemStack> transferInventory(Inventory into, Inventory from) {
        List<ItemStack> result = new ArrayList<>();
        for (ItemStack itemStack:from.getContents()) {
            if (itemStack != null) {
                result.addAll(into.addItem(itemStack).values());
            }
        }
        return result;
    }

    protected List<ItemStack> combineWith(T other) {
        return new ArrayList<ItemStack>(transferInventory(visContainer.getBukkitInventory(), other.visContainer.getBukkitInventory()));
    }

    protected void dropAll(List<ItemStack> drops, HumanEntity human) {
        World w = human.getWorld();
        Location drop = human.getEyeLocation();
        for (ItemStack itemStack:drops) {
            w.dropItem(drop, itemStack);
        }
    }

    @Override
    public final boolean canCombineAnvil(ItemStack itemStack, HumanEntity human) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof WandItemStackBase && checkClass((WandItemStackBase) ((OilBukkitItemStack) itemStack).getOilItemStack());
    }

    protected abstract boolean checkClass(WandItemStackBase itemStack);
}
