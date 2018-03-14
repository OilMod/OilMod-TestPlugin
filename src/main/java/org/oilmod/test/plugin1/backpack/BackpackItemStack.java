package org.oilmod.test.plugin1.backpack;

import org.oilmod.api.inventory.InventoryFactory;
import org.oilmod.api.inventory.ModInventoryObject;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.test.plugin1.PortableInventoryFilter;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class BackpackItemStack extends OilItemStack {
    private final ModInventoryObject inventory;

    public BackpackItemStack(NMSItemStack nmsItemStack, BackpackItem item) {
        super(nmsItemStack, item);
        inventory = InventoryFactory.getInstance().createBasicInventory("inv", this, 9*item.getRows(), "Backpack", PortableInventoryFilter.INSTANCE, true); //Creates inventory attached to
    }

    @Override
    protected List<String> createDescription() {
        return Arrays.asList(getItem().getDisplayName()+".", "", "This backpack has §6" + getRows() + " " + (getRows()==1?"row":"rows")+"§7.", "Can store up to §6" + (getRows()*9) + "§7 stacks of items!"); //Creates item description if needed
    }

    @Override
    public BackpackItem getItem() {
        return (BackpackItem) super.getItem();
    }

    @Override
    public boolean onUse(Player player, Action action) {
        player.openInventory(inventory.getBukkitInventory()); //onUse event - opens inventory
        return true; //suppress normal minecraft actions
    }

    public int getRows() {
        return getItem().getRows();
    }

    protected void transferInventory(Inventory into, Inventory from) { //copies all items from an inventory to another
        for (int i=0;i<from.getSize();i++) {
            ItemStack itemStack =from.getItem(i);
            if (itemStack != null) {
                into.setItem(i, itemStack);
            }
        }
    }

    public void copyTo(BackpackItemStack other) {
        if (getRows() > other.getRows()) {
            throw new IllegalStateException("Cannot copy to a smaller backpack!");
        }
        transferInventory(other.inventory.getBukkitInventory(), this.inventory.getBukkitInventory());
    }
}
