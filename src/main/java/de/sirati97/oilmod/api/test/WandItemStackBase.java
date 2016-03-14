package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.data.IntegerData;
import de.sirati97.oilmod.api.inventory.InventoryFactoryBase;
import de.sirati97.oilmod.api.inventory.ModInventoryObject;
import de.sirati97.oilmod.api.items.ItemDescription;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class WandItemStackBase extends OilItemStack implements VisHolder{
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

    protected String getVisStoredDescriptionLine(int vis) {
        return "Stores " + vis + " Vis.";
    }

    @Override
    protected List<String> createDescription() {
        return Arrays.asList(getVisStoredDescriptionLine(vis.getData()));
    }
}
