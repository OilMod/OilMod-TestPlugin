package de.sirati97.oilmod.api.test.magic2.node;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.procedure.TLongObjectProcedure;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.lang.Math.abs;

/**
 * Created by sirati97 on 30.06.2016 for OilMod-TestPlugin.
 */
public class NodeManager implements Listener {
    private final static String NODE_FILE_NAME = "MagicNodes.yml";
    private final Map<UUID, TLongObjectMap<List<Node>>> nodes = new HashMap<>();
    private final Set<Node> activeNodes = new HashSet<>();
    private BukkitTask nodeDisplayer;
    private BukkitTask nodeUpdater;
    private BukkitTask nodeAttack;
    private BukkitTask nodeSaver;
    private long lastSaveEvent=0;
    private World lastSaveEventWorld;


    @EventHandler
    public void onWorldLoadEvent(WorldLoadEvent event) {
        loadWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldSaveEvent(WorldSaveEvent event) {
        saveWorld(event.getWorld());
        lastSaveEvent = System.currentTimeMillis();
        lastSaveEventWorld = event.getWorld();
    }

    @EventHandler
    public synchronized void WorldUnloadEvent(WorldUnloadEvent event) {
        if (lastSaveEvent+100<System.currentTimeMillis()||lastSaveEventWorld!=event.getWorld()) {
            saveWorld(event.getWorld());
        }
        nodes.remove(event.getWorld().getUID());
    }

    public synchronized void loadWorld(World world) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File(world.getWorldFolder(), File.separator + NODE_FILE_NAME));
        if (configuration.contains("nodes")) {
            ConfigurationSection nodeSection = configuration.getConfigurationSection("nodes");
            TLongObjectMap<List<Node>> worldNodes = nodes.get(world.getUID());
            if (worldNodes == null) {
                worldNodes = new TLongObjectHashMap<>();
                nodes.put(world.getUID(), worldNodes);
            }
            for (String chunkNames:nodeSection.getKeys(false)) {
                long chunkId = Long.parseLong(chunkNames);
                List<Node> chunkNodes = worldNodes.get(chunkId);
                if (chunkNodes == null) {
                    chunkNodes = new ArrayList<>();
                    worldNodes.put(chunkId, chunkNodes);
                }
                List<Map<?, ?>> chunkData = nodeSection.getMapList(chunkNames);
                for (Map<?, ?> noteDataUncasted :chunkData) {
                    //noinspection unchecked
                    Map<String, Object> nodeData = (Map<String, Object>) noteDataUncasted;
                    chunkNodes.add(new Node(world, nodeData));
                }

            }
        }
    }

    public synchronized void saveWorld(World world) {
        YamlConfiguration configuration =  new YamlConfiguration();
        TLongObjectMap<List<Node>> worldNodes = nodes.get(world.getUID());
        if (worldNodes != null) {
            final ConfigurationSection nodeSection = configuration.createSection("nodes");
            worldNodes.forEachEntry(new TLongObjectProcedure<List<Node>>() {
                @Override
                public boolean execute(long chunkId, List<Node> chunkNodes) {
                    if (chunkNodes != null && chunkNodes.size() > 0) {
                        List<Map<String, Object>> chunkData = new ArrayList<>(chunkNodes.size());
                        for (Node node:chunkNodes) {
                            chunkData.add(node.serialize());
                        }
                        nodeSection.set(String.valueOf(chunkId), chunkData);
                    }
                    return true;
                }
            });
        }
        try {
            configuration.save(new File(world.getWorldFolder(), File.separator + NODE_FILE_NAME));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public synchronized void saveAll() {
        for (World world: Bukkit.getWorlds()) {
            saveWorld(world);
        }
    }

    public synchronized void loadAll() {
        for (World world: Bukkit.getWorlds()) {
            loadWorld(world);
        }
    }


    public  Node getNearestInRange(Location location, double range) {
        final double rangeSquared = range*range;
        List<Node> results = getInRange(location, range);
        Node result=null;
        double lastRangeSquared = Integer.MAX_VALUE;
        for (Node node:results) {
            double nodeRangeSquared = location.distanceSquared(node.getLocation());
            if (nodeRangeSquared < lastRangeSquared && nodeRangeSquared < rangeSquared) {
                result = node;
                lastRangeSquared = nodeRangeSquared;
            }
        }
        return result;
    }

    public List<Node> getInRange(Location location, double range) {
        range = abs(range);
        int rangeInt = (int) Math.ceil(range);
        List<Node> results = new ArrayList<>();
        int cX = location.getBlockX()%16;
        int cZ = location.getBlockZ()%16;
        int highX = abs(cX)+rangeInt;
        int lowX = -abs(cX)-rangeInt;
        int highZ = abs(cZ)+rangeInt;
        int lowZ = -abs(cZ)-rangeInt;
        int chunkX = location.getChunk().getX();
        int chunkZ = location.getChunk().getZ();
        int chunkHighX = roundUp(highX, 16)+chunkX;
        int chunkLowX = roundUp(lowX, 16)+chunkX;
        int chunkHighZ = roundUp(highZ, 16)+chunkZ;
        int chunkLowZ = roundUp(lowZ, 16)+chunkZ;
        for (int x = chunkLowX; x < chunkHighX; x++) {
            for (int z = chunkLowZ; z < chunkHighZ; z++) {
                List<Node> chunkResults = getChunkNodes(location.getWorld(), x, z);
                if (chunkResults != null) {
                    results.addAll(chunkResults);
                }
            }
        }
        return results;
    }

    public synchronized List<Node> getChunkNodes(World world, int x, int z) {
        TLongObjectMap<List<Node>> worldNodes = nodes.get(world.getUID());
        if (worldNodes != null) {
            List<Node> chunkNodes = worldNodes.get(to1D(x, z));
            if (chunkNodes != null) {
                return chunkNodes;
            }
        }
        return null;
    }

    public synchronized void updateActiveNodes() {
        activeNodes.clear();
        for(Player player:Bukkit.getOnlinePlayers()) {
            activeNodes.addAll(getInRange(player.getLocation(), 40));
        }
    }

    public synchronized void tickActiveNodes() {
        for(Node node:activeNodes) {
            node.tick();
        }
    }


    public synchronized void displayActiveNodes() {
        for(Node node:activeNodes) {
            node.display();
        }
    }

    public void init(Plugin plugin) {
        nodeDisplayer = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                displayActiveNodes();
            }
        },10,10);
        nodeUpdater = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                tickActiveNodes();
            }
        },20,20);
        nodeUpdater = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                updateActiveNodes();
            }
        },100,100);
        nodeSaver = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                displayActiveNodes();
            }
        },30600,30600);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        loadAll();
    }

    public void dispose() {
        nodeDisplayer.cancel();
        nodeUpdater.cancel();
        nodeSaver.cancel();
        HandlerList.unregisterAll(this);
        saveAll();
    }

    public void spawnNode(Location location) {
        TLongObjectMap<List<Node>> worldNodes = nodes.get(location.getWorld().getUID());
        if (worldNodes == null) {
            worldNodes = new TLongObjectHashMap<>();
            nodes.put(location.getWorld().getUID(), worldNodes);
        }
        long chunkId = to1D(location.getChunk().getX(), location.getChunk().getZ());
        List<Node> chunkNodes = worldNodes.get(chunkId);
        if (chunkNodes == null) {
            chunkNodes = new ArrayList<>();
            worldNodes.put(chunkId, chunkNodes);
        }
        chunkNodes.add(new Node(location));
    }

    public static long to1D(int x , int z) {
        return (long)x << 32 | z & 0xFFFFFFFFL;
    }

    public static int roundUp(int num, int divisor) {
        int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
        return sign * (abs(num) + abs(divisor) - 1) / abs(divisor);
    }

    public static int log2(int n){
        if(n <= 0) throw new IllegalArgumentException();
        return 31 - Integer.numberOfLeadingZeros(n);
    }
}
