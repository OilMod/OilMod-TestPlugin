package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.data.LongData;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 12.03.2016.
 */
public class ArrowWandItemStack extends WandItemStackBase<ArrowWandItemStack> {
    protected final LongData lastShot = new LongData("lastShot", this);

    public ArrowWandItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public boolean onUse(Player player, Action action) {


        if (player.isSneaking()) {
            super.onUse(player, action);
        } else {
            boolean hasFlame = hasEnchantment(Enchantment.ARROW_FIRE);
            int arrowKnockback = getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);
            int arrowDamage = getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
            int durability = getEnchantmentLevel(Enchantment.DURABILITY);
            int visUsed = (int) ((15+(hasFlame?8:0)+2*arrowKnockback+3*arrowDamage)*(3d/(3d+durability)));
            if ((lastShot.getData() > System.currentTimeMillis() || lastShot.getData()+(2000d/Math.cbrt(getEnchantmentLevel(Enchantment.DIG_SPEED)+1)) < System.currentTimeMillis()) && useVis(false, visUsed)){
                lastShot.setData(System.currentTimeMillis());

                useVis(true, visUsed);
                Arrow arrow = player.getWorld().spawnArrow(player.getEyeLocation().add(player.getLocation().getDirection()), player.getLocation().getDirection(), (float) (2 + 0.2*arrowDamage) ,1);
                arrow.setCritical(true);
                arrow.setKnockbackStrength(arrowKnockback);
                if (hasFlame) {
                    arrow.setFireTicks(Integer.MAX_VALUE);
                }
                arrow.setShooter(player);
            }
        }
        return true;
    }

    @Override
    protected boolean checkClass(WandItemStackBase itemStack) {
        return itemStack instanceof ArrowWandItemStack;
    }
}
