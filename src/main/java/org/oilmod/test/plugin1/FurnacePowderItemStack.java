package org.oilmod.test.plugin1;

import org.oilmod.api.inventory.InventoryFactory;
import org.oilmod.api.inventory.ModFurnaceInventoryObject;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class FurnacePowderItemStack extends OilItemStack {
    ModFurnaceInventoryObject inventory = InventoryFactory.getInstance().createFurnaceInventory("inv", this, "Furnace Powder", TestPlugin.getInstance().getTicker(), PortableInventoryFilter.INSTANCE, true);

    public FurnacePowderItemStack(NMSItemStack nmsItemStack, OilItem item) {
        super(nmsItemStack, item);
    }

}
