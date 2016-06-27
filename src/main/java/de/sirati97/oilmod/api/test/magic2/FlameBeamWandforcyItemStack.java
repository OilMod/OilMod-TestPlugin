package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.util.ParticleSpawnData;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public class FlameBeamWandforcyItemStack extends WeaponBeamWandforcyItemStackBase<FlameBeamWandforcyItemStack> {
    private static final ParticleSpawnData FLAME_PARTICLES = new ParticleSpawnData(Effect.FLAME);

    public FlameBeamWandforcyItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    protected int getMinVisUsage() {
        return 2;
    }

    @Override
    protected int getMaxTryVisUsage() {
        return 4;
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
    protected void hit(Wand wand, Player player, LivingEntity lastEntities) {
        lastEntities.setFireTicks(50);
    }


    @Override
    protected boolean checkClass(WandforcyItemStackBase itemStack) {
        return itemStack instanceof FlameBeamWandforcyItemStack;
    }
}
