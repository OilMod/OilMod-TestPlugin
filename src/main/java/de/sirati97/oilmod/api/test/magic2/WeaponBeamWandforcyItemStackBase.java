package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.data.LongData;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.test.TestPlugin;
import de.sirati97.oilmod.api.userinterface.UIPanel;
import de.sirati97.oilmod.api.util.OilUtil;
import de.sirati97.oilmod.api.util.ParticleSpawnData;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public abstract class WeaponBeamWandforcyItemStackBase<T extends WeaponBeamWandforcyItemStackBase<T>> extends BeamWandforcyItemStackBase<T> {
    private static final ParticleSpawnData SMOKE_PARTICLES = new ParticleSpawnData(Effect.SMALL_SMOKE).setParticleCount(2);
    private List<LivingEntity> lastEntities;
    private final LongData lastUse = new LongData("lastUse", this);

    public WeaponBeamWandforcyItemStackBase(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    protected boolean isStopCriteria(Player player, Material mat, Block block, Location location, Vector vector) {
        return mat.isSolid();
    }

    @Override
    protected boolean isGoalCriteria(Player player, Material mat, Block block, Location location, Vector vector) {
        lastEntities = OilUtil.getNearbyEntities(location, 0.5, LivingEntity.class);
        lastEntities.remove(player);
        return lastEntities.size()>0;
    }

    @Override
    protected int getTries() {
        return 8;
    }

    @Override
    protected double getMaxDistance(Wand wand) {
        return 12;
    }

    @Override
    protected double getMinDistance(Wand wand) {
        return 1.5;
    }

    @Override
    protected int getNormalDiversionDivisor() {
        return 7;
    }

    @Override
    protected void displayParticles(World world, Location loc, Vector vector) {
        OilUtil.spawnParticleLine(loc, SMOKE_PARTICLES, vector);
        if (TestPlugin.rnd.nextInt(8)==0) {
            ParticleSpawnData secondary = getSecondaryParticles();
            if (secondary != null) {
                OilUtil.spawnParticleCloud(loc, getSecondaryParticles());
            }
        }
    }

    protected abstract ParticleSpawnData getSecondaryParticles();
    protected abstract boolean checkGoal(Wand wand, Player player, List<LivingEntity> lastEntities);
    protected abstract void hit(Wand wand, Player player, LivingEntity entity, int index);
    protected abstract int getAfterHitCooldownMs();

    @Override
    protected void onGoal(Wand wand, Player player, Location eyes, Location goal, Block goalBlock) {
        if (checkGoal(wand, player, lastEntities)) {
            int counter = 0;
            for (LivingEntity entity:lastEntities) {
                hit(wand, player, entity, counter++);
            }
            setCooldown(getAfterHitCooldownMs());
        }
    }

    @Override
    protected boolean canUse(Wand wand, Player player) {
        int cooldown = getCurrentCooldown();
        if (cooldown > 1000) {
            player.sendMessage("On cooldown. Wait " + (cooldown+500)/1000 + "s.");
        }
        return cooldown<=0;
    }

    @Override
    protected void startBeams(Wand wand, Player player) {
    }

    public void setCooldown(int ms) {
        lastUse.setData(System.currentTimeMillis()+ms);
    }

    public int getCurrentCooldown() {
        long cooldown = lastUse.getData() - System.currentTimeMillis();
        return cooldown<0?0:((int)cooldown);
    }

    @Override
    public UIPanel getUIPanel() {
        return null;
    }
}
