package de.sirati97.oilmod.api.test.magic2.wandforcy;

import de.sirati97.oilmod.api.items.NMSItemStack;
import org.bukkit.enchantments.Enchantment;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ReplaceWandforcyItem extends WandforcyItemBase<ReplaceWandforcyItemStack> {
    public ReplaceWandforcyItem() {
        super("ReplaceWandforcy", "Replace");
    }

    @Override
    public ReplaceWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
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
