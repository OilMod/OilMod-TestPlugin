package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.NMSItemStack;
import org.bukkit.enchantments.Enchantment;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class OreMagnetWandforcyItem extends WandforcyItemBase<OreMagnetWandforcyItemStack> {
    public OreMagnetWandforcyItem(OilMod mod) {
        super(mod.createKey("wandforcy_ore_magnet"), "Ore Magnet");
    }

    @Override
    public OreMagnetWandforcyItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new OreMagnetWandforcyItemStack(nmsItemStack, this);
    }


    @Override
    public int getEnchantSelectModifier() {
        return 12;
    }

    @Override
    public boolean canEnchant(Enchantment enchantment) {
        return Enchantment.DURABILITY.equals(enchantment)||Enchantment.DIG_SPEED.equals(enchantment);
    }
}
