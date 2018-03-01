package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.items.NMSItemStack;
import org.oilmod.test.plugin1.magic2.Wand;
import org.oilmod.api.util.OilUtil;
import org.oilmod.api.util.ParticleSpawnData;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by sirati97 on 28.06.2016 for OilMod-TestPlugin.
 */
public class SniperWandforcyItemStack extends WeaponBeamWandforcyItemStackBase<SniperWandforcyItemStack> {
    public SniperWandforcyItemStack(NMSItemStack nmsItemStack, WandforcyItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    protected ParticleSpawnData getSecondaryParticles() {
        return null;
    }

    @Override
    protected boolean checkGoal(Wand wand, Player player, List<LivingEntity> lastEntities) {
        return true;
    }

    @Override
    protected void hit(Wand wand, Player player, LivingEntity entity, int index) {
        double dist = player.getEyeLocation().distance(entity.getLocation());
        boolean shortMode = false;
        if (dist <= 25) {
            if (index == 0) { //only send message on first hit (we can hit more than one target)
                player.sendMessage("This weapon is ineffective on short range");
            }
            shortMode = true;
        }
        if (index == 0) {
            player.playSound(player.getEyeLocation().add(player.getLocation().getDirection().normalize().multiply(5)), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 0.8f);
        }
        if (wand.checkVis(shortMode?10:30)) {
            wand.useVis(shortMode?10:30);
        }
        OilUtil.damageEntity(entity, shortMode?4:9, player);
        Vector v = player.getLocation().getDirection().normalize().multiply(shortMode?0.9:1.5);
        if (v.getY() < 0.1) {
            v.setY(0.1);
        }
        entity.setVelocity(v);
    }

    @Override
    protected int getAfterHitCooldownMs() {
        return 5000;
    }

    @Override
    protected int getMinVisUsage() {
        return 20;
    }

    @Override
    protected int getMaxTryVisUsage() {
        return 50;
    }

    @Override
    protected double getMinDistance(Wand wand) {
        return 2;
    }

    @Override
    protected double getMaxDistance(Wand wand) {
        return 160;
    }

    @Override
    protected int getTries() {
        return 1;
    }

    @Override
    protected int getNormalDiversionDivisor() {
        return 10000;
    }

    @Override
    protected boolean checkClass(WandforcyItemStackBase itemStack) {
        return itemStack instanceof SniperWandforcyItemStack;
    }

    @Override
    protected void startBeams(Wand wand, Player player) {
        setCooldown(2000);
    }
}
