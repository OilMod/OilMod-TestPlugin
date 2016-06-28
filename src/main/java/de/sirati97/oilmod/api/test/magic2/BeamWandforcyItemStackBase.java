package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.test.TestPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public abstract class BeamWandforcyItemStackBase<T extends BeamWandforcyItemStackBase<T>> extends WandforcyItemStackBase<T> {
    public BeamWandforcyItemStackBase(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public final void onWandUse(Wand wand, final Player player, Action action) {
        int minVis = getMinVisUsage();
        int maxVis = getMaxTryVisUsage();
        if (wand.checkVis(minVis+maxVis) && canUse(wand, player)) {
            wand.useVis(minVis);
            startBeams(wand, player);
            double maxDist = getMaxDistance(wand);
            maxDist *= maxDist; //square
            double minDist = getMinDistance(wand);
            minDist *= minDist;
            final Location eyes = player.getEyeLocation();
            final World world = eyes.getWorld();
            round:
            for (int i = 0; i < getTries(); i++) {
                if (wand.checkVis(maxVis)) {
                    Vector v = player.getLocation().getDirection().normalize();
                    Location tempLoc = eyes.clone().add(v);
                    double dist = tempLoc.distanceSquared(eyes);
                    Block b = tempLoc.getBlock();
                    Material mat = b.getType();
                    while (tempLoc.getY() > 0 && dist < maxDist) {
                        if (isStopCriteria(mat, b, tempLoc, v)) {
                            continue round;
                        }
                        if (!mat.isSolid()) {
                            displayParticles(world, tempLoc, v);
                        }
                        if (dist > minDist && isGoalCriteria(mat, b, tempLoc, v)) {
                            onGoal(wand, player, eyes, tempLoc, b);
                            continue round;
                        }
                        int diversionDivisor = getNormalDiversionDivisor();
                        v.setX(v.getX() + TestPlugin.rnd.nextDouble() / diversionDivisor * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
                        v.setY(v.getY() + TestPlugin.rnd.nextDouble() / diversionDivisor * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
                        v.setZ(v.getZ() + TestPlugin.rnd.nextDouble() / diversionDivisor * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
                        tempLoc = tempLoc.add(v);
                        dist = tempLoc.distanceSquared(eyes);
                        b = tempLoc.getBlock();
                        mat = b.getType();
                    }
                }
            }
        }
    }

    protected abstract boolean isStopCriteria(Material mat, Block block, Location location, Vector vector);
    protected abstract boolean isGoalCriteria(Material mat, Block block, Location location, Vector vector);
    protected abstract int getTries();
    protected abstract int getMinVisUsage();
    protected abstract int getMaxTryVisUsage();
    protected abstract double getMaxDistance(Wand wand);
    protected abstract double getMinDistance(Wand wand);
    protected abstract int getNormalDiversionDivisor();
    protected abstract void displayParticles(World world, Location loc, Vector vector);
    protected abstract void onGoal(Wand wand, Player player, Location eyes, Location goal, Block goalBlock);
    protected abstract boolean canUse(Wand wand, Player player);
    protected abstract void startBeams(Wand wand, Player player);


    public static boolean isUnbreakableBlock(Material mat) {
        return Material.BEDROCK.equals(mat) || Material.BARRIER.equals(mat);
    }

    public static boolean isContainerBlock(Material mat) {
        return Material.CHEST.equals(mat) || Material.TRAPPED_CHEST.equals(mat) || Material.DROPPER.equals(mat) || Material.DISPENSER.equals(mat) || Material.HOPPER.equals(mat) || Material.FURNACE.equals(mat) || Material.BURNING_FURNACE.equals(mat) || Material.BREWING_STAND.equals(mat);
    }

    public static boolean isOre(Material mat) {
        return Material.COAL_ORE.equals(mat) || Material.IRON_ORE.equals(mat) || Material.REDSTONE_ORE.equals(mat) || Material.QUARTZ_ORE.equals(mat) || Material.GOLD_ORE.equals(mat) || Material.LAPIS_ORE.equals(mat) || Material.DIAMOND_ORE.equals(mat) || Material.EMERALD_ORE.equals(mat);
    }
//
//    @Override
//    protected List<String> createDescription() {
//        List<String> result = new ArrayList<>();
//        result.add("Efficiency increases the range!");
//        return result;
//    }
}
