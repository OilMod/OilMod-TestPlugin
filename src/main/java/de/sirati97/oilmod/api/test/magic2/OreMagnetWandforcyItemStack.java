package de.sirati97.oilmod.api.test.magic2;

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
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public class OreMagnetWandforcyItemStack extends BeamWandforcyItemStackBase<OreMagnetWandforcyItemStack> {
    private static final ParticleSpawnData SMOKE_PARTICLES = new ParticleSpawnData(Effect.SMALL_SMOKE);
    public OreMagnetWandforcyItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    protected boolean isStopCriteria(Material mat, Block block, Location location, Vector vector) {
        return isStopBlock(mat);
    }

    public static boolean isStopBlock(Material mat) {
        return isUnbreakableBlock(mat) || isContainerBlock(mat);
    }

    @Override
    protected boolean isGoalCriteria(Material mat, Block block, Location location, Vector vector) {
        return isOre(mat);
    }

    @Override
    protected int getTries() {
        return 20;
    }

    @Override
    protected int getMinVisUsage() {
        return 3;
    }

    @Override
    protected int getMaxTryVisUsage() {
        return 4;
    }

    @Override
    protected double getMaxDistance(Wand wand) {
        return 30 + 15 * (getEnchantmentLevel(Enchantment.DIG_SPEED));
    }

    @Override
    protected double getMinDistance(Wand wand) {
        return 5;
    }

    @Override
    protected int getNormalDiversionDivisor() {
        return 18;
    }

    @Override
    protected void displayParticles(World w, Location loc, Vector vector) {
        OilUtil.spawnParticleCloud(loc, SMOKE_PARTICLES);
        if (TestPlugin.rnd.nextInt(15) == 0) {
            w.spigot().playEffect(loc, Effect.FLYING_GLYPH);
        }
    }

    @Override
    protected void onGoal(Wand wand, Player player, Location eyes, Location ore, Block oreBlock) {
        Vector v = eyes.clone().subtract(oreBlock.getLocation()).toVector().normalize();
        Block lastSwitched = null;
        BlockState oreBlockState = oreBlock.getState();
        List<BlockState> blockStates = new ArrayList<>();
        blockStates.add(oreBlockState);

        for (int j = 0; j < 15; j++) {
            Block b = ore.getBlock();
            ore = ore.add(v);
            Block b2 = ore.getBlock();
            Material mat2 = b2.getType();
            Location checkPoint = ore.clone();
            int counter = 0;
            while (!mat2.isSolid() && !isStopBlock(mat2) && !isOre(mat2) && ore.distanceSquared(eyes) > 4) {
                ore = ore.add(v);
                b2 = ore.getBlock();
                mat2 = b2.getType();
                counter++;
            }
            if (mat2.isSolid()) {
                for (int k = 0; k < counter; k++) {
                    checkPoint = checkPoint.add(v);
                    OilUtil.spawnParticleCloud(ore, SMOKE_PARTICLES);
                    if (TestPlugin.rnd.nextInt(3) == 0) {
                        ore.getWorld().spigot().playEffect(ore, Effect.POTION_SWIRL_TRANSPARENT);
                    }
                }
            }
            if (ore.distanceSquared(eyes) < 4) {
                break;
            }
            if (isStopBlock(mat2)) {
                continue;
            }

            switchBlocks(b, b2, blockStates);
            lastSwitched = b2;
        }

        if (lastSwitched != null) {
            setToState(lastSwitched, oreBlockState);
            boolean allowed = OilUtil.canMultiPlace(player, blockStates, oreBlockState.getBlock(), getNmsItemStack().asBukkitItemStack());
            if (allowed) {
                if (TestPlugin.rnd.nextInt(2 + getEnchantmentLevel(Enchantment.DURABILITY)) < 2) {
                    wand.useVis(4);
                }
            } else {
                for (BlockState blockState:blockStates) {
                    blockState.update(true, false);
                }
            }
        }

    }

    @SuppressWarnings("deprecation")
    private static void switchBlocks(Block to, Block from, List<BlockState> blockStates) {
        blockStates.add(from.getState());

        BlockState newState = to.getState();
        newState.setType(from.getType());
        newState.setRawData(from.getData());
        newState.update(true, false);
    }


    private static void setToState(Block b, BlockState blockState) {
        BlockState newState = b.getState();
        newState.setType(blockState.getType());
        newState.setData(blockState.getData());
        newState.update(true, false);
    }

    @Override
    protected List<String> createDescription() {
        return Collections.singletonList("Efficiency increases the range!");
    }

    @Override
    protected boolean checkClass(WandforcyItemStackBase itemStack) {
        return itemStack instanceof OreMagnetWandforcyItemStack;
    }

    @Override
    public UIPanel getUIPanel() {
        return null;
    }
}
