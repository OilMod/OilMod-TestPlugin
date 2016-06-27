package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.data.IntegerData;
import de.sirati97.oilmod.api.data.ItemStackData;
import de.sirati97.oilmod.api.inventory.InventoryFactoryBase;
import de.sirati97.oilmod.api.inventory.ItemFilter;
import de.sirati97.oilmod.api.inventory.ModInventoryObject;
import de.sirati97.oilmod.api.items.ItemDescription;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import de.sirati97.oilmod.api.test.magic.VisFilter;
import de.sirati97.oilmod.api.test.magic.VisHolder;
import de.sirati97.oilmod.api.test.magic2.ui.WandUIBuilder;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.sirati97.oilmod.api.test.InventoryUtil.dropAll;
import static de.sirati97.oilmod.api.test.InventoryUtil.transferInventory;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class WandItemStack<T extends WandItemStack<T>> extends OilItemStack implements VisHolder, Wand{
    private final ModInventoryObject visContainer = InventoryFactoryBase.getInstance().createBasicInventory("visCon", this, 5, "Vis Container", VisFilter.INSTANCE);
    private final ModInventoryObject wandforcyContainer = InventoryFactoryBase.getInstance().createBasicInventory("wandforcyCon", this, 5, "Wandforcy Container", WandforcyFilter.INSTANCE);
    private final ItemStackData activeWandforcy = ItemStackData.createInstance("activeWandforcy", this);
    private final IntegerData vis = new IntegerData("visStored", this);

    public WandItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public boolean onUseOnBlock(Player player, Action action, Block blockClicked, BlockFace blockFace) {
        Wandforcy wandforcy = getActiveWandforcy();
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
            Wandforcy wandforcy = getActiveWandforcy();
            if (wandforcy != null) {
                wandforcy.onWandUse(this, player, action);
            }
        }
        return true;
    }

    @Override
    public boolean onLeftClickOnBlock(Player player, Action action, Block blockClicked, BlockFace blockFace) {
        Wandforcy wandforcy = getActiveWandforcy();
        if (wandforcy != null) {
            return wandforcy.onWandLeftClickOnBlock(this, player, action, blockClicked, blockFace);
        }
        return false;
    }

    @Override
    public boolean onLeftClick(Player player, Action action) {
        Wandforcy wandforcy = getActiveWandforcy();
        if (wandforcy != null) {
            return wandforcy.onWandLeftClick(this, player, action);
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
        int visVirtual = getVis();
        Inventory inv = visContainer.getBukkitInventory();
        for (int i=0;i<inv.getSize()&&visVirtual<visNeeded;i++) {
            ItemStack itemStack = inv.getItem(i);
            if (itemStack != null && itemStack instanceof OilBukkitItemStack) {
                OilBukkitItemStack oilBukkitItemStack = (OilBukkitItemStack) itemStack;
                if (oilBukkitItemStack.getOilItemStack() instanceof VisHolder) {
                    VisHolder visHolder = (VisHolder) oilBukkitItemStack.getOilItemStack();
                    int visMissing = visNeeded-visVirtual;
                    int itemsNeeded = visMissing/visHolder.getVis()+((visMissing%visHolder.getVis()==0)?0:1);
                    int itemsConverted = Math.min(itemsNeeded, itemStack.getAmount());
                    visVirtual += itemsConverted * visHolder.getVis();
                    if (remove) {
                        if (itemStack.getAmount()-itemsConverted < 1) {
                            inv.setItem(i, null);
                        } else {
                            itemStack.setAmount(itemStack.getAmount()-itemsConverted);
                        }
                    }
                }
            }
        }
        if (remove) {
            setVis(visVirtual-visNeeded);
        }
        return visVirtual>=visNeeded;
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
        T other = (T) ((OilBukkitItemStack) itemStack).getOilItemStack();
        List<ItemStack> drops= combineWith(other);
        dropAll(drops, human);
    }

    @Override
    public void prepareCombineAnvil(ItemStack itemStack, HumanEntity human) {
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
        transferInventory(visContainer.getBukkitInventory(), other.getVisContainer().getBukkitInventory(), result);
        transferInventory(wandforcyContainer.getBukkitInventory(), other.getWandforcyContainer().getBukkitInventory(), result);
        if (other.getActiveWandforcyContainer().getItemStack()==null) {
            other.getActiveWandforcyContainer().setItemStack(activeWandforcy.getItemStack());
        } else if (activeWandforcy.getItemStack() != null) {
            result.add(activeWandforcy.getItemStack());
        }
        return result;
    }


    @Override
    public final boolean canCombineAnvil(ItemStack itemStack, HumanEntity human) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof WandItemStack && checkClass((WandItemStack) ((OilBukkitItemStack) itemStack).getOilItemStack());
    }

    protected boolean checkClass(WandItemStack itemStack) {
        return itemStack.getClass()==WandItemStack.class;
    }

    public ModInventoryObject getWandforcyContainer() {
        return wandforcyContainer;
    }

    public ItemStackData getActiveWandforcyContainer() {
        return activeWandforcy;
    }

    public ModInventoryObject getVisContainer() {
        return visContainer;
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

    public Wandforcy getActiveWandforcy() {
        ItemStack wandforcyItemStack = activeWandforcy.getItemStack();
        if (wandforcyItemStack instanceof OilBukkitItemStack) {
            OilItemStack oilItemStack = ((OilBukkitItemStack)wandforcyItemStack).getOilItemStack();
            if (oilItemStack instanceof Wandforcy) {
                return (Wandforcy) oilItemStack;
            }
        }
        return null;
    }
}
