package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.inventory.InventoryFactoryBase;
import de.sirati97.oilmod.api.inventory.ModInventoryObject;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class ReplaceWandItemStack extends WandItemStackBase<ReplaceWandItemStack> implements VisHolder{
    private static final BlockFace[] SITES = new BlockFace[]{BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    private ModInventoryObject blockContainer = InventoryFactoryBase.getInstance().createBasicInventory("blockCon", this, 18, "Block Container", BlockFilter.INSTANCE, true);


    public ReplaceWandItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    @Override
    public boolean onUse(Player player, Action action) {
        if (player.isSneaking()) {
            super.onUse(player, action);
        } else {
            player.openInventory(blockContainer.getBukkitInventory());
        }
        return true;
    }

    @Override
    public boolean onLeftClickOnBlock(Player player, Action action, Block blockClicked, BlockFace blockFace) {

        if (!useVis(false, 1)) {
            return true;
        }
        ItemStack newBlock = getNextBlock(false);
        if (newBlock == null || (newBlock.getType().equals(blockClicked.getType()) && ((byte)newBlock.getDurability()) == blockClicked.getData())) {
            return true;
        }

        replaceBlocks(player, Arrays.asList(new ReplaceBlockArgsTuple(blockClicked.getType(), blockClicked.getData(),  blockClicked, BlockFace.SELF)), blockClicked.getLocation());
        return true;
    }


    private void replaceBlocks(final Player player, final List<ReplaceBlockArgsTuple> argsCalls, final Location start) {
        Runnable r = new Runnable() {
            List<ReplaceBlockArgsTuple> calls = argsCalls;
            List<ReplaceBlockArgsTuple> nextCalls = new ArrayList<>();
            int i = 0;
            @Override
            public void run() {
                if (i < calls.size()) {
                    List<ItemStack> drops = new ArrayList<>();
                    boolean success = false;
                    while (i < calls.size() && !(success|=replaceBlock(calls.get(i++), start, drops, nextCalls))); //Tries until a block was replaced or queue is empty

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

    private boolean replaceBlock(ReplaceBlockArgsTuple args, Location start, List<ItemStack> drops, List<ReplaceBlockArgsTuple> nextCalls) {
        return replaceBlock(args.type, args.data, args.block, args.from, start, drops, nextCalls);
    }


    private boolean replaceBlock(Material type, byte data, Block block, BlockFace from, Location start, List<ItemStack> drops, List<ReplaceBlockArgsTuple> nextCalls) {
        if (!useVis(false, 1)) {
            return false;
        }
        ItemStack newBlock = getNextBlock(false);
        if (newBlock == null || !type.equals(block.getType()) || (block.getData()) != data) {
            return false;
        }
        drops.addAll(Arrays.asList(hasEnchantment(Enchantment.SILK_TOUCH)? Util.getDropsSilktouch(block):Util.getDrops(block)));
        block.setType(newBlock.getType());
        block.setData((byte)newBlock.getDurability());
        useVis(true, (TestPlugin.rnd.nextInt(3+getEnchantmentLevel(Enchantment.DURABILITY))<3)?1:0);
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
    protected boolean checkClass(WandItemStackBase itemStack) {
        return itemStack instanceof ReplaceWandItemStack;
    }

    @Override
    protected List<ItemStack> combineWith(ReplaceWandItemStack other) {
        List<ItemStack> drops= super.combineWith(other);
        drops.addAll(transferInventory(blockContainer.getBukkitInventory(), other.blockContainer.getBukkitInventory()));
        return drops;
    }
}
