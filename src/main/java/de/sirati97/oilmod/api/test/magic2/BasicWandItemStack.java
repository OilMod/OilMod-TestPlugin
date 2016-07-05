package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.data.IntegerData;
import de.sirati97.oilmod.api.data.ItemStackData;
import de.sirati97.oilmod.api.inventory.ItemFilter;
import de.sirati97.oilmod.api.items.ItemDescription;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import de.sirati97.oilmod.api.test.TestPlugin;
import de.sirati97.oilmod.api.test.magic.VisHolder;
import de.sirati97.oilmod.api.test.magic2.node.Node;
import de.sirati97.oilmod.api.test.magic2.ui.WandUIBuilder;
import de.sirati97.oilmod.api.userinterface.ItemStackHolder;
import de.sirati97.oilmod.api.util.OilUtil;
import de.sirati97.oilmod.api.util.ParticleSpawnData;
import net.md_5.bungee.api.ChatColor;
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

import static de.sirati97.oilmod.api.test.InventoryUtil.dropAll;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class BasicWandItemStack<T extends BasicWandItemStack<T>> extends OilItemStack implements VisHolder, Wand{
    private static final ParticleSpawnData PORTAL_PARTICLES = new ParticleSpawnData(Effect.PORTAL);
    private static final ParticleSpawnData SMOKE_PARTICLES = new ParticleSpawnData(Effect.SMALL_SMOKE).setParticleCount(2);
    private final ItemStackData wandforcy = ItemStackData.createInstance("wandforcy", this);
    private final IntegerData vis = new IntegerData("visStored", this);

    public BasicWandItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public boolean onUseOnBlock(Player player, Action action, Block blockClicked, BlockFace blockFace) {
        Wandforcy wandforcy = getWandforcy();
        if (!player.isSneaking() && wandforcy != null) {
            wandforcy.onWandUseOnBlock(this, player, action, blockClicked, blockFace);
        }
        return true;
    }

    @Override
    public boolean onUse(Player player, Action action) {
        if (player.isSneaking()) {
            WandUIBuilder.INSTANCE.displayNewUI(player, this);
        } else {
            Wandforcy wandforcy = getWandforcy();
            if (wandforcy == null) {
                onNoForcyUse(player);
            } else {
                wandforcy.onWandUse(this, player, action);
            }
        }
        return true;
    }

    @Override
    public boolean onLeftClickOnBlock(Player player, Action action, Block blockClicked, BlockFace blockFace) {
        Wandforcy wandforcy = getWandforcy();
        if (!player.isSneaking() && wandforcy != null) {
            return wandforcy.onWandLeftClickOnBlock(this, player, action, blockClicked, blockFace);
        }
        return false;
    }

    @Override
    public boolean onLeftClick(Player player, Action action) {
        if (player.isSneaking()) {
            onEjectWandforcy(player);
            return true;
        } else {
            Wandforcy wandforcy = getWandforcy();
            if (wandforcy != null) {
                return wandforcy.onWandLeftClick(this, player, action);
            }
        }
        return false;
    }

    private boolean useVis(boolean remove, int visNeeded) {
        if (getVis() >= visNeeded) {
            if (remove) {
                setVis(getVis()-visNeeded);
            }
            return true;
        }
//        int visVirtual = getVis();
//        Inventory inv = visContainer.getBukkitInventory();
//        for (int i=0;i<inv.getSize()&&visVirtual<visNeeded;i++) {
//            ItemStack itemStack = inv.getItem(i);
//            if (itemStack != null && itemStack instanceof OilBukkitItemStack) {
//                OilBukkitItemStack oilBukkitItemStack = (OilBukkitItemStack) itemStack;
//                if (oilBukkitItemStack.getOilItemStack() instanceof VisHolder) {
//                    VisHolder visHolder = (VisHolder) oilBukkitItemStack.getOilItemStack();
//                    int visMissing = visNeeded-visVirtual;
//                    int itemsNeeded = visMissing/visHolder.getVis()+((visMissing%visHolder.getVis()==0)?0:1);
//                    int itemsConverted = Math.min(itemsNeeded, itemStack.getAmount());
//                    visVirtual += itemsConverted * visHolder.getVis();
//                    if (remove) {
//                        if (itemStack.getAmount()-itemsConverted < 1) {
//                            inv.setItem(i, null);
//                        } else {
//                            itemStack.setAmount(itemStack.getAmount()-itemsConverted);
//                        }
//                    }
//                }
//            }
//        }
//        if (remove && visVirtual >= visNeeded) {
//            setVis(visVirtual-visNeeded);
//        }
//        return visVirtual>=visNeeded;
        return false;
    }

    public int getVis() {
        return vis.getData();
    }

    public void setVis(int vis) {
        ItemDescription description = getItemDescription();
        description.setLine(0, getVisStoredDescriptionLine(vis), true);
        this.vis.setData(vis);
    }

    @Override
    public final void combineAnvil(ItemStack itemStack, HumanEntity human) {
        //noinspection unchecked
        T other = (T) ((OilBukkitItemStack) itemStack).getOilItemStack();
        List<ItemStack> drops= combineWith(other);
        dropAll(drops, human);
    }

    @Override
    public void prepareCombineAnvil(ItemStack itemStack, HumanEntity human) {
        //noinspection unchecked
        T other = (T) ((OilBukkitItemStack) itemStack).getOilItemStack();
        setVis(getVis()+other.getVis());
    }

    protected String getVisStoredDescriptionLine(int vis) {
        return "Stores " + vis + " Vis.";
    }

    @Override
    protected List<String> createDescription() {
        //noinspection ArraysAsListWithZeroOrOneArgument
        return Arrays.asList(getVisStoredDescriptionLine(vis.getData()));
    }


    protected List<ItemStack> combineWith(T other) {
        List<ItemStack> result = new ArrayList<>();
//        transferInventory(visContainer.getBukkitInventory(), other.getVisContainer().getBukkitInventory(), result);
//        transferInventory(wandforcyContainer.getBukkitInventory(), other.getWandforcyContainer().getBukkitInventory(), result);
        if (other.getWandforcyContainer().getItemStack()==null) {
            other.getWandforcyContainer().setItemStack(wandforcy.getItemStack());
        } else if (wandforcy.getItemStack() != null) {
            result.add(wandforcy.getItemStack());
        }
        return result;
    }


    @Override
    public final boolean canCombineAnvil(ItemStack itemStack, HumanEntity human) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof BasicWandItemStack && checkClass((BasicWandItemStack) ((OilBukkitItemStack) itemStack).getOilItemStack());
    }

    protected boolean checkClass(BasicWandItemStack itemStack) {
        return itemStack.getClass()==BasicWandItemStack.class;
    }


    public ItemStackHolder getWandforcyContainer() {
        return wandforcy;
    }

    public ItemStack getWandforcyAsItemStack() {
        return getWandforcyContainer().getItemStack();
    }

    public void setWandforcy(ItemStack itemStack) {
        if (itemStack==null || getWandforcyFilter().allowed(itemStack)) {
            getWandforcyContainer().setItemStack(itemStack);
        }
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



    private long lastEmptyNodeMessage = 0;
    public final void onNoForcyUse(Player player) {
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

    private void onEjectWandforcy(Player player) {
        ItemStack wandforcyItemStack = getWandforcyAsItemStack();
        if (wandforcyItemStack == null) {
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack itemStack = player.getInventory().getItem(i);
                if (getWandforcyFilter().allowed(itemStack)) {
                    player.sendMessage("Inserted §a" + ChatColor.stripColor(itemStack.getItemMeta().getDisplayName())+"§r.");
                    player.getInventory().setItem(i, null);
                    setWandforcy(itemStack);
                    return;
                }
            }
            player.sendMessage("Cannot find any insertable Wandforcy.");
        } else {
            if (player.getInventory().addItem(wandforcyItemStack).size()>0) {
                player.sendMessage("Cannot eject Wandforcy. Player inventory is full.");
            } else {
                player.sendMessage("Ejected §a" + ChatColor.stripColor(wandforcyItemStack.getItemMeta().getDisplayName())+"§r.");
                setWandforcy((ItemStack) null);
            }
        }


    }
}
