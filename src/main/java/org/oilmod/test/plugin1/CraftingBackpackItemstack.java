package org.oilmod.test.plugin1;

import org.oilmod.api.inventory.InventoryFactory;
import org.oilmod.api.inventory.ModPortableCraftingInventoryObject;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class CraftingBackpackItemstack extends OilItemStack {
    ModPortableCraftingInventoryObject inventory = InventoryFactory.getInstance().createPortableCraftingInventory("inv", this, 3, 3, "Crafting Backpack", PortableInventoryFilter.INSTANCE, true);

    public CraftingBackpackItemstack(NMSItemStack nmsItemStack, OilItem item) {
        super(nmsItemStack, item);
    }

}
