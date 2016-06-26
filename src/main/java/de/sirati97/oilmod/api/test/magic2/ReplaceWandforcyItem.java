package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ReplaceWandforcyItem extends OilItemBase {
    public ReplaceWandforcyItem() {
        super(Material.FIREWORK_CHARGE, 0, "ReplaceWandforcy", 1, "Replace Wandforcy");
    }

    @Override
    public OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new ReplaceWandforcyItemStack(nmsItemStack, this);
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
