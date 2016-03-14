package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ReplaceWandItem extends OilItemBase {
    static final ItemStack silktouchItem = new ItemStack(Material.DIAMOND_PICKAXE, 1);
    static {
        silktouchItem.addEnchantment(Enchantment.SILK_TOUCH,1);
    }

    public ReplaceWandItem() {
        super(Material.BLAZE_ROD, 0, 5, 1, "Replace Wand");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new ReplaceWandItemStack(nmsItemStack, this);
    }


    @Override
    public int getEnchantSelectModifier() {
        return 12;
    }

    @Override
    public boolean canEnchant(Enchantment enchantment) {
        return Enchantment.DURABILITY.equals(enchantment)||Enchantment.DIG_SPEED.equals(enchantment)||Enchantment.SILK_TOUCH.equals(enchantment);
    }
}
