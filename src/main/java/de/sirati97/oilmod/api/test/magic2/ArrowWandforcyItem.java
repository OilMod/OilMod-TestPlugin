package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import de.sirati97.oilmod.api.test.magic.ArrowWandItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ArrowWandforcyItem extends OilItemBase {
    public ArrowWandforcyItem() {
        super(Material.FIREWORK_CHARGE, 0, "ArrowWandforcy", 1, "Arrow Wandforcy");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new ArrowWandforcyItemStack(nmsItemStack, this);
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
