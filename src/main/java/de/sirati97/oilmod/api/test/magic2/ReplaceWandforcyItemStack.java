package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.inventory.InventoryFactoryBase;
import de.sirati97.oilmod.api.inventory.ModInventoryObject;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.test.BlockFilter;
import de.sirati97.oilmod.api.test.TestPlugin;
import de.sirati97.oilmod.api.userinterface.UIOilInventoryPanel;
import de.sirati97.oilmod.api.userinterface.UIPanel;
import de.sirati97.oilmod.api.util.OilUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static de.sirati97.oilmod.api.test.InventoryUtil.transferInventory;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public class ReplaceWandforcyItemStack extends WandforcyItemStackBase<ReplaceWandforcyItemStack> {
    private static final BlockFace[] SITES = new BlockFace[]{BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    private ModInventoryObject blockContainer = InventoryFactoryBase.getInstance().createBasicInventory("blockCon", this, 18, "Block Container", BlockFilter.INSTANCE, true);
    private UIPanel uiPanel;

    public ReplaceWandforcyItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public void onWandUse(Wand wand, Player player, Action action) {
        player.openInventory(blockContainer.getBukkitInventory());
    }

    @Override
    public boolean onWandLeftClickOnBlock(Wand wand, Player player, Action action, Block blockClicked, BlockFace blockFace) {

        if (!wand.checkVis(1)) {
            return true;
        }
        ItemStack newBlock = getNextBlock(false);
        if (newBlock == null || (newBlock.getType().equals(blockClicked.getType()) && ((byte)newBlock.getDurability()) == blockClicked.getData())) {
            return true;
        }

        replaceBlocks(wand, player, Arrays.asList(new ReplaceBlockArgsTuple(blockClicked.getType(), blockClicked.getData(),  blockClicked, BlockFace.SELF)), blockClicked.getLocation());
        return true;
    }


    private void replaceBlocks(final Wand wand, final Player player, final List<ReplaceBlockArgsTuple> argsCalls, final Location start) {
        Runnable r = new Runnable() {
            List<ReplaceBlockArgsTuple> calls = argsCalls;
            List<ReplaceBlockArgsTuple> nextCalls = new ArrayList<>();
            int i = 0;
            @Override
            public void run() {
                if (i < calls.size()) {
                    List<ItemStack> drops = new ArrayList<>();
                    boolean success = false;
                    while (i < calls.size() && !(success|=replaceBlock(wand, player, calls.get(i++), start, drops, nextCalls))); //Tries until a block was replaced or queue is empty

                    Inventory inv = player.getInventory();
                    Map<Integer, ItemStack> couldNotAdd = inv.addItem(drops.toArray(new ItemStack[drops.size()]));
                    for (ItemStack drop:couldNotAdd.values()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), drop);
                    }

                    if (!success && i >= calls.size()) {
                        run();
                        return;
                    }
                } else {
                    if (nextCalls.size() > 0){
                        i = 0;
                        calls = nextCalls;
                        nextCalls = new ArrayList<>();
                        run();
                    }
                    return;
                }
                if (TestPlugin.rnd.nextInt(2+getEnchantmentLevel(Enchantment.DIG_SPEED))<2) {
                    Bukkit.getScheduler().runTaskLater(TestPlugin.getInstance(), this, 1); //waits one tick
                } else {
                    run();
                }
            }
        };
        r.run();
    }

    private static class ReplaceBlockArgsTuple {
        final Material type;
        final byte data;
        final Block block;
        final BlockFace from;

        private ReplaceBlockArgsTuple(Material type, byte data, Block block, BlockFace from) {
            this.type = type;
            this.data = data;
            this.block = block;
            this.from = from;
        }
    }

    private boolean replaceBlock(Wand wand, Player player, ReplaceBlockArgsTuple args, Location start, List<ItemStack> drops, List<ReplaceBlockArgsTuple> nextCalls) {
        return replaceBlock(wand, player, args.type, args.data, args.block, args.from, start, drops, nextCalls);
    }


    private boolean replaceBlock(Wand wand, Player player, Material type, byte data, Block block, BlockFace from, Location start, List<ItemStack> drops, List<ReplaceBlockArgsTuple> nextCalls) {
        if (!wand.checkVis(1)) {
            return false;
        }
        ItemStack newBlock = getNextBlock(false);
        if (newBlock == null || !type.equals(block.getType()) || (block.getData()) != data) {
            return false;
        }
        if (!OilUtil.canBreak(player, block)) {
            return false;
        }
        List<ItemStack> newDrops =Arrays.asList(hasEnchantment(Enchantment.SILK_TOUCH)? OilUtil.getDropsSilktouch(block):OilUtil.getDrops(block));
        BlockState oldState = block.getState();
        BlockState newState = block.getState();
        newState.setType(newBlock.getType());
        newState.setRawData((byte)newBlock.getDurability());
        newState.update(true, false);
        if (!OilUtil.canPlace(player, block, oldState, block.getRelative(from), getNmsItemStack().asBukkitItemStack())) {
            oldState.update(true, false);
            return false;
        }
        drops.addAll(newDrops);
        wand.useVis((TestPlugin.rnd.nextInt(3+getEnchantmentLevel(Enchantment.DURABILITY))<3)?1:0);
        getNextBlock(true);
        for (BlockFace face:SITES) {
            Block block2 = block.getRelative(face);
            if (face != from && getDistance(block2.getLocation(), start) < 5) {
                nextCalls.add(new ReplaceBlockArgsTuple(type, data, block2, face.getOppositeFace()));
            }
        }
        return true;
    }

    private int getDistance(Location loc1, Location loc2) {
        int x = Math.abs(loc1.getBlockX()-loc2.getBlockX());
        int y = Math.abs(loc1.getBlockY()-loc2.getBlockY());
        int z = Math.abs(loc1.getBlockZ()-loc2.getBlockZ());
        return Math.max(x, Math.max(y, z));
    }

    private ItemStack getNextBlock(boolean remove) {
        Inventory inv = blockContainer.getBukkitInventory();
        for (int i=0;i<inv.getSize();i++) {
            ItemStack itemStack = inv.getItem(i);
            if (itemStack != null && itemStack.getType().isBlock()) {
                ItemStack result = itemStack.clone();
                result.setAmount(1);
                if (remove) {
                    if (itemStack.getAmount() < 2) {
                        inv.setItem(i, null);
                    } else {
                        itemStack.setAmount(itemStack.getAmount()-1);
                    }
                }
                return result;
            }
        }
        return null;
    }

    @Override
    protected boolean checkClass(WandforcyItemStackBase itemStack) {
        return itemStack instanceof ReplaceWandforcyItemStack;
    }

    @Override
    protected List<ItemStack> combineWith(ReplaceWandforcyItemStack other) {
        List<ItemStack> drops= super.combineWith(other);
        transferInventory(blockContainer.getBukkitInventory(), other.blockContainer.getBukkitInventory(), drops);
        return drops;
    }

    @Override
    public UIPanel getUIPanel() {
        return uiPanel==null?uiPanel=new UIOilInventoryPanel(blockContainer):uiPanel;
    }
}
