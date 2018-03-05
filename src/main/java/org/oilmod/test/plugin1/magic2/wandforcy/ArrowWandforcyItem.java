package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;
import org.bukkit.enchantments.Enchantment;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ArrowWandforcyItem extends WandforcyItemBase<ArrowWandforcyItemStack> {
    public ArrowWandforcyItem(OilMod mod) {
        super(mod.createKey("wandforcy_arrow"), "Arrow");
    }

    @Override
    public ArrowWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
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
