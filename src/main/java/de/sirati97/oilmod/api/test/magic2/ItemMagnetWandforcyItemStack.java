package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.userinterface.UIPanel;
import de.sirati97.oilmod.api.util.OilUtil;
import de.sirati97.oilmod.api.util.ParticleSpawnData;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public class ItemMagnetWandforcyItemStack extends BeamWandforcyItemStackBase<ItemMagnetWandforcyItemStack> {
    private static final ParticleSpawnData SMOKE_PARTICLES = new ParticleSpawnData(Effect.SMALL_SMOKE).setParticleCount(2);
    private List<Item> lastItems;

    public ItemMagnetWandforcyItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    protected boolean checkClass(WandforcyItemStackBase itemStack) {
        return false;
    }

    @Override
    protected boolean isStopCriteria(Player player, Material mat, Block block, Location location, Vector vector) {
        return mat.isSolid();
    }

    @Override
    protected boolean isGoalCriteria(Player player, Material mat, Block block, Location location, Vector vector) {
        lastItems = OilUtil.getNearbyEntities(location, 0.4, Item.class);
        return lastItems.size()>0;
    }

    @Override
    protected int getTries() {
        return 10;
    }

    @Override
    protected int getMinVisUsage() {
        return 1;
    }

    @Override
    protected int getMaxTryVisUsage() {
        return 0;
    }

    @Override
    protected double getMaxDistance(Wand wand) {
        return 20;
    }

    @Override
    protected double getMinDistance(Wand wand) {
        return 1.5;
    }

    @Override
    protected int getNormalDiversionDivisor() {
        return 14;
    }

    @Override
    protected void displayParticles(World world, Location loc, Vector vector) {
        OilUtil.spawnParticleLine(loc, SMOKE_PARTICLES, vector);
    }


    @Override
    protected void onGoal(Wand wand, Player player, Location eyes, Location goal, Block goalBlock) {
        for (Item item:lastItems) {
            Location itemLoc = item.getLocation();
            double distSquared = eyes.distanceSquared(itemLoc);
            Vector v = itemLoc.subtract(player.getLocation()).toVector().normalize().multiply(-0.8);
            v.setY(0.3f);
            if (distSquared < 25) {
                v.multiply(0.5);
            }
            item.setVelocity(v);
        }
    }

    @Override
    protected boolean canUse(Wand wand, Player player) {
        return true;
    }

    @Override
    protected void startBeams(Wand wand, Player player) {
    }

    @Override
    public UIPanel getUIPanel() {
        return null;
    }
}
