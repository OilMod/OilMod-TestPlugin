package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.data.ItemStackData;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class BasicWandItemStack extends WandItemStackBase<BasicWandItemStack>{
    private final ItemStackData wandforcyDataTag = ItemStackData.createInstance("wandforcy", this);

    public BasicWandItemStack(NMSItemStack nmsItemStack, OilItem item) {
        super(nmsItemStack, item);
    }

    protected ItemStack getWandforcyAsItemStack() {
        return wandforcyDataTag.getItemStack();
    }

    public void setWandforcy(ItemStack itemStack) {
        if (itemStack==null || getWandforcyFilter().allowed(itemStack)) {
            getItemDescription().setLine(0, getWandforcyDescriptionLine(), true);
            wandforcyDataTag.setItemStack(itemStack);
        }
    }

    protected void onEjectWandforcy(Player player) {
        Wandforcy wandforcy = getWandforcy();
        if (wandforcy == null) {
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack itemStack = player.getInventory().getItem(i);
                if (getWandforcyFilter().allowed(itemStack)) {
                    player.sendMessage("Inserted §a" + ChatColor.stripColor(itemStack.getItemMeta().getDisplayName())+"§r.");
                    player.getInventory().setItem(i, null);
                    setWandforcy(itemStack);
                    return;
                }
            }
            player.sendMessage("Cannot find any insertable Wandforcy.");
        } else {
            if (player.getInventory().addItem(wandforcy.asItemStack()).size()>0) {
                player.sendMessage("Cannot eject " + wandforcy.getCurrentDisplayName() + ". Player inventory is full.");
            } else {
                player.sendMessage("Ejected §a" + wandforcy.getCurrentDisplayName() + "§r.");
                setWandforcy((ItemStack) null);
            }
        }
    }
}
