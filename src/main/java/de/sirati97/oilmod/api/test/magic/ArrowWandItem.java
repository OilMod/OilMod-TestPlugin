package de.sirati97.oilmod.api.test.magic;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ArrowWandItem extends OilItemBase {
    public ArrowWandItem() {
        super(Material.BLAZE_ROD, 0, 7, 1, "Arrow Wand");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new ArrowWandItemStack(nmsItemStack, this);
    }

    @Override
    public int getEnchantSelectModifier() {
        return 11;
    }

    @Override
    public boolean canEnchant(Enchantment enchantment) {
        return Enchantment.ARROW_DAMAGE.equals(enchantment)||Enchantment.ARROW_KNOCKBACK.equals(enchantment)||Enchantment.ARROW_FIRE.equals(enchantment)||Enchantment.DURABILITY.equals(enchantment)||Enchantment.DIG_SPEED.equals(enchantment);
    }
}
