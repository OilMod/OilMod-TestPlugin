package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.items.NMSItemStack;
import org.oilmod.test.plugin1.magic2.Wand;
import org.oilmod.api.util.OilUtil;
import org.oilmod.api.util.ParticleSpawnData;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public class FlameBeamWandforcyItemStack extends WeaponBeamWandforcyItemStackBase<FlameBeamWandforcyItemStack> {
    private static final ParticleSpawnData FLAME_PARTICLES = new ParticleSpawnData(Effect.FLAME);

    public FlameBeamWandforcyItemStack(NMSItemStack nmsItemStack, WandforcyItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    protected int getMinVisUsage() {
        return 1;
    }

    @Override
    protected int getMaxTryVisUsage() {
        return 2;
    }

    @Override
    protected ParticleSpawnData getSecondaryParticles() {
        return FLAME_PARTICLES;
    }

    @Override
    protected boolean checkGoal(Wand wand, Player player, List<LivingEntity> lastEntities) {
        return true;
    }

    @Override
    protected void hit(Wand wand, Player player, LivingEntity entity, int index) {
        int oldFireTicks = entity.getFireTicks();
        oldFireTicks = oldFireTicks<0?0:oldFireTicks;
        if (oldFireTicks<40 && wand.checkVis((80-oldFireTicks)/40)) {
            if (oldFireTicks<=0) {
                OilUtil.setLastDamager(entity, player);
            }
            entity.setFireTicks(80);
            wand.useVis((80-oldFireTicks)/40);
        }
    }

    @Override
    protected int getAfterHitCooldownMs() {
        return 500;
    }


    @Override
    protected boolean checkClass(WandforcyItemStackBase itemStack) {
        return itemStack instanceof FlameBeamWandforcyItemStack;
    }
}
