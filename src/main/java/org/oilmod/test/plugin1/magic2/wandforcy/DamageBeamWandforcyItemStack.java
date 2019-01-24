package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.items.NMSItemStack;
import org.oilmod.test.plugin1.TestPlugin;
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
public class DamageBeamWandforcyItemStack extends WeaponBeamWandforcyItemStackBase<DamageBeamWandforcyItemStack> {
    private static final ParticleSpawnData FIREWORKS_PARTICLES = new ParticleSpawnData(Effect.FIREWORKS_SPARK);

    public DamageBeamWandforcyItemStack(NMSItemStack nmsItemStack, WandforcyItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    protected int getMinVisUsage() {
        return 2;
    }

    @Override
    protected int getMaxTryVisUsage() {
        return 3;
    }

    @Override
    protected ParticleSpawnData getSecondaryParticles() {
        return FIREWORKS_PARTICLES;
    }

    @Override
    protected boolean checkGoal(Wand wand, Player player, List<LivingEntity> lastEntities) {
        return true;
    }

    @Override
    protected void hit(Wand wand, Player player, LivingEntity entity, int index) {
        if (!entity.isInvulnerable() && wand.checkVis(3)) {
            boolean damaged = OilUtil.damageEntity(entity, 3, TestPlugin.rnd.nextInt(4)==0?player:null); //stop armor from getting damaged
            if (damaged) {
                wand.useVis(3);
            }
        }
    }


    @Override
    protected boolean checkClass(WandforcyItemStackBase itemStack) {
        return itemStack instanceof DamageBeamWandforcyItemStack;
    }

    @Override
    protected int getAfterHitCooldownMs() {
        return 1300;
    }

}
