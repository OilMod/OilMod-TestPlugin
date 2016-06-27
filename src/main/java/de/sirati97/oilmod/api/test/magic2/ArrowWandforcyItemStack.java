package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.data.LongData;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.userinterface.UIPanel;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public class ArrowWandforcyItemStack extends WandforcyItemStackBase<ArrowWandforcyItemStack> {
    protected final LongData lastShot = new LongData("lastShot", this);

    public ArrowWandforcyItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public void onWandUse(Wand wand, Player player, Action action) {
        boolean hasFlame = hasEnchantment(Enchantment.ARROW_FIRE);
        int arrowKnockback = getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);
        int arrowDamage = getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
        int durability = getEnchantmentLevel(Enchantment.DURABILITY);
        int visUsed = (int) ((15+(hasFlame?8:0)+2*arrowKnockback+3*arrowDamage)*(3d/(3d+durability)));
        if ((lastShot.getData() > System.currentTimeMillis() || lastShot.getData()+(2000d/Math.cbrt(getEnchantmentLevel(Enchantment.DIG_SPEED)+1)) < System.currentTimeMillis()) && wand.checkVis(visUsed)){
            lastShot.setData(System.currentTimeMillis());

            wand.useVis(visUsed);
            Arrow arrow = player.getWorld().spawnArrow(player.getEyeLocation().add(player.getLocation().getDirection()), player.getLocation().getDirection(), (float) (2 + 0.2*arrowDamage) ,1);
            arrow.setCritical(true);
            arrow.setKnockbackStrength(arrowKnockback);
            if (hasFlame) {
                arrow.setFireTicks(Integer.MAX_VALUE);
            }
            arrow.setShooter(player);
        }
    }

    @Override
    public UIPanel getUIPanel() {
        return null;
    }

    @Override
    protected boolean checkClass(WandforcyItemStackBase itemStack) {
        return itemStack instanceof ArrowWandforcyItemStack;
    }
}
