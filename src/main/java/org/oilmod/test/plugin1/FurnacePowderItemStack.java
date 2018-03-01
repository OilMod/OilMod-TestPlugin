package org.oilmod.test.plugin1;

import org.oilmod.api.inventory.InventoryFactoryBase;
import org.oilmod.api.inventory.ModFurnaceInventoryObject;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItemBase;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class FurnacePowderItemStack extends OilItemStack {
    ModFurnaceInventoryObject inventory = InventoryFactoryBase.getInstance().createFurnaceInventory("inv", this, "Furnace Powder", TestPlugin.getInstance().getTicker(), PortableInventoryFilter.INSTANCE, true);

    public FurnacePowderItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public boolean onUse(Player player, Action action) {
        player.openInventory(inventory.getBukkitInventory());
        return true;
    }
}
