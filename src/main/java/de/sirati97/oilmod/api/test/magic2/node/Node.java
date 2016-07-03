package de.sirati97.oilmod.api.test.magic2.node;

import de.sirati97.oilmod.api.test.TestPlugin;
import de.sirati97.oilmod.api.util.OilUtil;
import de.sirati97.oilmod.api.util.ParticleSpawnData;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sirati97 on 30.06.2016 for OilMod-TestPlugin.
 */
public class Node {
    private final static ParticleSpawnData SMALL_SMOKE_PARTICLE = new ParticleSpawnData(Effect.SMALL_SMOKE).setParticleCount(3);
    private final static ParticleSpawnData FLAME_PARTICLE = new ParticleSpawnData(Effect.FLAME);
    private final Location location;
    private int vis;
    private long lastWorldTick;

    public Node(Location location) {
        this.location = location;
        this.lastWorldTick = OilUtil.getWorldTicksPlayed(location.getWorld());
        this.vis = TestPlugin.rnd.nextInt(600)+300;
    }

    public Node(World world, Map<String, Object> map) {
        this.location = new Location(world, (double)map.get("x"), (double)map.get("y"), (double)map.get("z"));
        this.lastWorldTick = ((Number)map.get("lastWorldTick")).longValue();
        this.vis = ((Number)map.get("vis")).intValue();
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", location.getX());
        map.put("y", location.getY());
        map.put("z", location.getZ());
        map.put("vis", vis);
        map.put("lastWorldTick", lastWorldTick);
        return map;
    }

    public int getVis() {
        long worldTick = OilUtil.getWorldTicksPlayed(location.getWorld());
        long diff = worldTick - lastWorldTick;
        if (diff > 3) {
            vis += diff/3;
            lastWorldTick = worldTick-diff%5;
        }
        vis = Math.min(30000, vis);
        return vis;
    }

    public Location getLocation() {
        return location;
    }

    public boolean useVis(int visUsed) {
        int vis = getVis();
        if (vis < visUsed) {
            return false;
        } else {
            this.vis -= visUsed;
            return true;
        }
    }

    public boolean stealVis(int visStolen) {
        spawnMonsters(80+2*visStolen, visStolen);
        return useVis(visStolen);
    }

    public long getChunkID() {
        return NodeManager.to1D(location.getChunk().getX(), location.getChunk().getZ());
    }

    public void display() {
        int vis = getVis();
        float root = (float) Math.sqrt(vis);
        float size = Math.min(root/1000*((float)Math.log1p(vis)),0.3f);
        OilUtil.spawnParticleCloud(location, SMALL_SMOKE_PARTICLE, size, size, size, (int) root);
    }


    public void tick() {
        int vis = getVis();
        int monsterChance = NodeManager.log2(vis+1);
        spawnMonsters(20000, monsterChance*monsterChance);
    }

    private void spawnMonsters(int rnd, int min) {
        int vis = getVis();
        if (TestPlugin.rnd.nextInt(rnd)<min) {
            EntityType type;
            int visNeeded;
            if (vis > 500 && TestPlugin.rnd.nextBoolean()) {
                type = EntityType.ENDERMAN;
                visNeeded = 200;
            } else if (vis > 200 && TestPlugin.rnd.nextBoolean()) {
                type = EntityType.CREEPER;
                visNeeded = 150;
            } else if (vis > 150 && TestPlugin.rnd.nextBoolean()) {
                type = EntityType.SKELETON;
                visNeeded = 125;
            } else if (vis > 100 && TestPlugin.rnd.nextBoolean()) {
                type = EntityType.ZOMBIE;
                visNeeded = 100;
            } else if (vis > 70 && TestPlugin.rnd.nextBoolean()) {
                type = EntityType.SPIDER;
                visNeeded = 50;
            } else {
                type = EntityType.BAT;
                visNeeded = Math.min(vis, 25);
            }
            if (useVis(visNeeded)) {
                Location loc = location.clone();
                loc.add(TestPlugin.rnd.nextInt(21)-10,0,TestPlugin.rnd.nextInt(21)-10);
                loc.setY(loc.getWorld().getHighestBlockYAt(loc));
                LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, type);
                setStrikeTo(location, entity, SMALL_SMOKE_PARTICLE, FLAME_PARTICLE,1,5);
            }
        }
    }

//    boolean spawned = false;
//    private void setStrikeTo(LivingEntity entity) {
//        if (spawned)return;
//        spawned = true;
//        final Location from = location.clone();
//        final Location to = entity.getLocation();
//        to.setY(to.getY()+(entity.getEyeHeight(true)*TestPlugin.rnd.nextDouble()));
//        Bukkit.getScheduler().runTask(TestPlugin.getInstance(), new Runnable() {
//
//            Location temp = from.clone();
//            Vector v;
//            Vector random;
//            @Override
//            public void run() {
//                v = to.toVector().subtract(temp.toVector()).normalize().multiply(0.5d);
//                random = v.clone();
//                random.setX(random.getX() + TestPlugin.rnd.nextDouble() / 5 * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
//                random.setY(random.getY() + TestPlugin.rnd.nextDouble() / 5 * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
//                random.setZ(random.getZ() + TestPlugin.rnd.nextDouble() / 5 * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
//                v.multiply(2).add(random).multiply(1d/3d);
//                OilUtil.spawnParticleLine(temp, SMALL_SMOKE_PARTICLE, v);
//                if (temp.distanceSquared(to)<3) {
//                    return;
//                }
//                temp.add(v);
//                for (Player p:Bukkit.getOnlinePlayers()) {
//                    p.teleport(temp);
//                }
//                Bukkit.getScheduler().runTaskLater(TestPlugin.getInstance(), this, 1);
//            }
//        });
//    }


    public static void setStrikeTo(Location from, Location to, ParticleSpawnData primary, ParticleSpawnData secondary, int secondaryChance, int rndStrength) {
        from = from.clone();
        Location temp = from.clone();
        Vector v;
        Vector random;
        while (true) {
            v = to.toVector().subtract(temp.toVector()).normalize().multiply(0.5d);
            random = new Vector(
                    TestPlugin.rnd.nextDouble() * (TestPlugin.rnd.nextBoolean() ? 1 : -1),
                    TestPlugin.rnd.nextDouble() * (TestPlugin.rnd.nextBoolean() ? 1 : -1),
                    TestPlugin.rnd.nextDouble() * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
            v.multiply(rndStrength).add(random).multiply(1d/((double)rndStrength+1d));
            OilUtil.spawnParticleLine(temp, SMALL_SMOKE_PARTICLE, v);
            if (secondary != null && TestPlugin.rnd.nextInt(secondaryChance)==0) {
                OilUtil.spawnParticleCloud(temp, secondary);
            }
            if (temp.distanceSquared(to)<1) {
                return;
            }
            temp.add(v);
        }
    }

    public static void setStrikeTo(Location from, LivingEntity entity, ParticleSpawnData primary, ParticleSpawnData secondary, int secondaryChance, int rndStrength) {
        Location to = entity.getLocation();
        to.setY(to.getY()+(entity.getEyeHeight(true)*TestPlugin.rnd.nextDouble()));
        setStrikeTo(from, to, primary, secondary, secondaryChance, rndStrength);
    }

}
