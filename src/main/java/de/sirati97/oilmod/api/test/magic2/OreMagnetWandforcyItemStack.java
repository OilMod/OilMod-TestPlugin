package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.test.TestPlugin;
import de.sirati97.oilmod.api.userinterface.UIPanel;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public class OreMagnetWandforcyItemStack extends WandforcyItemStackBase<OreMagnetWandforcyItemStack> {
    public OreMagnetWandforcyItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onWandUse(Wand wand, final Player player, Action action) {
        double maxDist = 30 + 15 * (getEnchantmentLevel(Enchantment.DIG_SPEED));
        maxDist *= maxDist; //square
        final Location eyes = player.getEyeLocation();
        round:
        for (int i = 0; i < 20; i++) {
            if (wand.checkVis(5)) {
                Vector v = player.getLocation().getDirection().normalize();
                Location ore = eyes.clone().add(v);
                double dist = ore.distanceSquared(eyes);
                Block b = ore.getBlock();
                Material mat = b.getType();
                while (ore.getY() > 0 && dist < maxDist) {
                    if (isStopBlock(mat)) {
                        return;
                    }
                    if (!mat.isSolid()) {
                        ore.getWorld().spigot().playEffect(ore, Effect.SMALL_SMOKE);
                    }
                    if (isOre(mat) && dist > 5 * 5) {
                        v = eyes.clone().subtract(b.getLocation()).toVector().normalize();
                        boolean executed = false;
                        move: for (int j = 0; j < 15; j++) {
                            b = ore.getBlock();
                            mat = b.getType();
                            byte data = b.getData();
                            ore = ore.add(v);
                            Block b2 = ore.getBlock();
                            Material mat2 = b2.getType();
                            Location checkPoint = ore.clone();
                            int counter = 0;
                            while (!mat2.isSolid() && !isStopBlock(mat2) && ore.distanceSquared(eyes) > 4) {
                                ore = ore.add(v);
                                b2 = ore.getBlock();
                                mat2 = b2.getType();
                                counter++;
                            }
                            if (mat2.isSolid()) {
                                for (int k = 0; k < counter; k++) {
                                    checkPoint = checkPoint.add(v);
                                    ore.getWorld().spigot().playEffect(ore, Effect.SMALL_SMOKE);
                                    if (TestPlugin.rnd.nextInt(3) == 0) {
                                        ore.getWorld().spigot().playEffect(ore, Effect.POTION_SWIRL_TRANSPARENT);
                                    }
                                }
                            }
                            if (ore.distanceSquared(eyes) < 4) {
                                continue round;
                            }
                            if (isStopBlock(mat2)) {
                                //noinspection UnnecessaryLabelOnContinueStatement
                                continue move;
                            }
                            executed = true;
                            b.setType(b2.getType());
                            b.setData(b2.getData());
                            b2.setType(mat);
                            b2.setData(data);
                        }

                        if (executed) {
                            if (TestPlugin.rnd.nextInt(2 + getEnchantmentLevel(Enchantment.DIG_SPEED)) < 2) {
                                wand.useVis(5);
                            }
                        }

                        continue round;
                    }
                    v.setX(v.getX() + TestPlugin.rnd.nextDouble() / 18 * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
                    v.setY(v.getY() + TestPlugin.rnd.nextDouble() / 18 * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
                    v.setZ(v.getZ() + TestPlugin.rnd.nextDouble() / 18 * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
                    ore = ore.add(v);
                    ore.getWorld().spigot().playEffect(ore, Effect.SMALL_SMOKE);
                    if (TestPlugin.rnd.nextInt(15) == 0) {
                        ore.getWorld().spigot().playEffect(ore, Effect.FLYING_GLYPH);
                    }
                    dist = ore.distanceSquared(eyes);
                    b = ore.getBlock();
                    mat = b.getType();
                }
            }
        }
    }

    public static boolean isOre(Material mat) {
        return Material.COAL_ORE.equals(mat) || Material.IRON_ORE.equals(mat) || Material.REDSTONE_ORE.equals(mat) || Material.QUARTZ_ORE.equals(mat) || Material.GOLD_ORE.equals(mat) || Material.LAPIS_ORE.equals(mat) || Material.DIAMOND_ORE.equals(mat) || Material.EMERALD_ORE.equals(mat);
    }


    public static boolean isStopBlock(Material mat) {
        return Material.BEDROCK.equals(mat) || Material.BARRIER.equals(mat) || Material.CHEST.equals(mat) || Material.TRAPPED_CHEST.equals(mat) || Material.DROPPER.equals(mat) || Material.DISPENSER.equals(mat) || Material.HOPPER.equals(mat) || Material.FURNACE.equals(mat) || Material.BURNING_FURNACE.equals(mat) || Material.BREWING_STAND.equals(mat);
    }

    @Override
    protected List<String> createDescription() {
        return Arrays.asList("Efficiency increases the range!");
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
