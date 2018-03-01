package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.items.NMSItemStack;
import org.oilmod.test.plugin1.TestPlugin;
import org.oilmod.test.plugin1.magic2.Wand;
import org.oilmod.api.util.OilUtil;
import org.oilmod.api.util.ParticleSpawnData;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public class LifestealBeamWandforcyItemStack extends WeaponBeamWandforcyItemStackBase<LifestealBeamWandforcyItemStack> {
    private static final ParticleSpawnData CRIT_PARTICLES = new ParticleSpawnData(Effect.MAGIC_CRIT);

    public LifestealBeamWandforcyItemStack(NMSItemStack nmsItemStack, WandforcyItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    protected int getMinVisUsage() {
        return 2;
    }

    @Override
    protected int getMaxTryVisUsage() {
        return 8;
    }

    @Override
    protected ParticleSpawnData getSecondaryParticles() {
        return CRIT_PARTICLES;
    }

    @Override
    protected boolean checkGoal(Wand wand, Player player, List<LivingEntity> lastEntities) {
        return true;
    }

    @Override
    protected void hit(Wand wand, Player player, LivingEntity entity, int index) {
        if (!entity.isInvulnerable() && wand.checkVis(8)) {
            PotionEffect wither = null;
            for (PotionEffect potionEffect:entity.getActivePotionEffects()) {
                if (potionEffect.getType()==PotionEffectType.WITHER) {
                    wither = potionEffect;
                    break;
                }
            }
            boolean damaged;
            if (wither == null || wither.getDuration() < 20) {
                damaged = OilUtil.damageEntity(entity, 3, player);
            } else {
                damaged = OilUtil.damageEntity(entity, 3, TestPlugin.rnd.nextInt(4)==0?player:null); //stop armor from getting damaged
            }


            if (damaged) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 0, false, false));
                player.setHealth(Math.min(player.getHealth()+0.8, player.getMaxHealth()));
                wand.useVis(8);
            }
        }
    }


    @Override
    protected boolean checkClass(WandforcyItemStackBase itemStack) {
        return itemStack instanceof LifestealBeamWandforcyItemStack;
    }

    @Override
    protected int getAfterHitCooldownMs() {
        return 666;
    }
}
