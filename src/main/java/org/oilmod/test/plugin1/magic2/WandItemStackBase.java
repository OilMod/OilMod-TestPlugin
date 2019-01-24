package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.data.IntegerData;
import org.oilmod.api.inventory.ItemFilter;
import org.oilmod.api.items.ItemDescription;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.test.plugin1.TestPlugin;
import org.oilmod.test.plugin1.magic2.node.Node;
import org.oilmod.test.plugin1.magic2.ui.WandUIBuilder;
import org.oilmod.api.userinterface.ItemStackHolder;
import org.oilmod.api.util.OilUtil;
import org.oilmod.api.util.ParticleSpawnData;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.oilmod.test.plugin1.InventoryUtil.dropAll;

/**
 * Created by sirati97 on 11.03.2016.
 */
public abstract class WandItemStackBase<T extends WandItemStackBase<T>> extends OilItemStack implements VisHolder, Wand{
    private static final ParticleSpawnData PORTAL_PARTICLES = new ParticleSpawnData(Effect.PORTAL);
    private static final ParticleSpawnData SMOKE_PARTICLES = new ParticleSpawnData(Effect.SMALL_SMOKE).setParticleCount(2);
    private final ItemStackHolder wandforcyContainer = new WandforcyItemstackHolderImpl();
    private final IntegerData vis = new IntegerData("visStored", this);

    public WandItemStackBase(NMSItemStack nmsItemStack, OilItem item) {
        super(nmsItemStack, item);
    }

    private boolean useVis(boolean remove, int visNeeded) {
        if (getVis() >= visNeeded) {
            if (remove) {
                setVis(getVis()-visNeeded);
            }
            return true;
        }
        return false;
    }

    public int getVis() {
        return vis.getData();
    }

    public void setVis(int vis) {
        //ItemDescription description = getItemDescription(); //TODO readd
        //description.setLine(1, getVisStoredDescriptionLine(vis), true);
        this.vis.setData(vis);
    }

    protected String getVisStoredDescriptionLine(int vis) {
        return "Stores " + vis + " Vis.";
    }
    protected String getWandforcyDescriptionLine() {
        Wandforcy wandforcy = getWandforcy();
        return (wandforcy==null?"No":wandforcy.getSpellName())+" spell loaded.";
    }

    /*@Override
    protected List<String> createDescription() {
        //noinspection ArraysAsListWithZeroOrOneArgument
        return Arrays.asList(getWandforcyDescriptionLine(), getVisStoredDescriptionLine(vis.getData()));
    }*/ //TODO: readd


    protected List<ItemStack> combineWith(T other) {
        List<ItemStack> result = new ArrayList<>();
//        transferInventory(visContainer.getBukkitInventory(), other.getVisContainer().getBukkitInventory(), result);
        Wandforcy wandforcy = getWandforcy();
        if (other.getWandforcy()==null) {
            other.setWandforcy(wandforcy);
        } else if (wandforcy != null) {
            result.add(wandforcy.asItemStack());
        }
        return result;
    }


    protected boolean checkClass(WandItemStackBase itemStack) {
        return itemStack.getClass()==WandItemStackBase.class;
    }


    public ItemStackHolder getWandforcyContainer() {
        return wandforcyContainer;
    }

    protected abstract ItemStack getWandforcyAsItemStack();
    protected abstract void setWandforcy(ItemStack itemStack);

    @Override
    public Wandforcy getWandforcy() {
        ItemStack wandforcyItemStack = getWandforcyAsItemStack();
        if (wandforcyItemStack instanceof OilBukkitItemStack) {
            OilItemStack oilItemStack = ((OilBukkitItemStack)wandforcyItemStack).getOilItemStack();
            if (oilItemStack instanceof Wandforcy) {
                return (Wandforcy) oilItemStack;
            }
        }
        return null;
    }


    @Override
    public void setWandforcy(Wandforcy wandforcy) {
        setWandforcy(wandforcy==null?null:wandforcy.asItemStack());
    }


    public ItemFilter getWandforcyFilter() {
        return WandforcyFilter.INSTANCE;
    }

    @Override
    public void useVis(int amount) {
        useVis(true, amount);
    }

    @Override
    public boolean checkVis(int amount) {
        return useVis(false, amount);
    }


    @Override
    public String getCurrentDisplayName() {
        return isRenamed()?getRename():getItem().getDisplayName();
    }

    private long lastEmptyNodeMessage = 0;
    protected final void onNoWandforcyUse(Player player) {
        double maxDist = 8*8;
        final Location eyes = player.getEyeLocation();
        for (int i = 0; i < 1; i++) {
            Vector v = player.getLocation().getDirection().normalize();
            Location tempLoc = eyes.clone().add(v);
            double dist = tempLoc.distanceSquared(eyes);
            Block b = tempLoc.getBlock();
            Material mat = b.getType();
            while (tempLoc.getY() > 0 && dist < maxDist) {
                if (!mat.isSolid()) {
                    OilUtil.spawnParticleLine(tempLoc, SMOKE_PARTICLES, v);
                }
                if (dist > 2) {
                    Node node = TestPlugin.getNodeManager().getNearestInRange(tempLoc, 3);
                    if (node != null && node.getLocation().distanceSquared(eyes) > 2) {
                        Node.setStrikeTo(tempLoc, node.getLocation(), SMOKE_PARTICLES, null, 1, 4);
                        if (node.stealVis(5)) {
                            Node.setStrikeTo(node.getLocation(), player, SMOKE_PARTICLES, PORTAL_PARTICLES, 1, 3);
                            setVis(getVis() + 5);
                        } else {
                            if (lastEmptyNodeMessage +5000<System.currentTimeMillis()) {
                                player.sendMessage("This node is empty.");
                                lastEmptyNodeMessage = System.currentTimeMillis();
                            }
                            return;
                        }
                    }
                }
                v.setX(v.getX() + TestPlugin.rnd.nextDouble() / 3 * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
                v.setY(v.getY() + TestPlugin.rnd.nextDouble() / 3 * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
                v.setZ(v.getZ() + TestPlugin.rnd.nextDouble() / 3 * (TestPlugin.rnd.nextBoolean() ? 1 : -1));
                tempLoc = tempLoc.add(v);
                dist = tempLoc.distanceSquared(eyes);
                b = tempLoc.getBlock();
                mat = b.getType();
            }
        }
    }

    protected abstract void onEjectWandforcy(Player player);

    private class WandforcyItemstackHolderImpl implements ItemStackHolder{

        @Override
        public ItemStack getItemStack() {
            return getWandforcyAsItemStack();
        }

        @Override
        public void setItemStack(ItemStack itemStack) {
            setWandforcy(itemStack);
        }
    }
}
