package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.inventory.InventoryFactoryBase;
import de.sirati97.oilmod.api.inventory.ModInventoryObject;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class BackpackItemstack extends OilItemStack {
    ModInventoryObject inventory = InventoryFactoryBase.getInstance().createBasicInventory("inv", this, 9, "Backpack", BackpackInventoryFilter.INSTANCE, true);

    public BackpackItemstack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public boolean onUse(Player player, Action action) {
        player.openInventory(inventory.getBukkitInventory());
        return true;
    }
}
