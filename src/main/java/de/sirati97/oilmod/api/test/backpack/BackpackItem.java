package de.sirati97.oilmod.api.test.backpack;

import de.sirati97.oilmod.api.items.ItemRegistry;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.crafting.ItemCraftingFactory;
import de.sirati97.oilmod.api.items.crafting.OilCraftingRecipe;
import de.sirati97.oilmod.api.items.crafting.OilCraftingResult;
import de.sirati97.oilmod.api.items.crafting.OilItemOilCraftingResult;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class BackpackItem extends OilItemBase<BackpackItemStack> {
    private final int rows;

    public BackpackItem(int id, int rows) {
        super(Material.LEATHER, 0, id, 1, names[rows-1] + " Backpack"); //defines Backpack item
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    @Override
    public BackpackItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new BackpackItemStack(nmsItemStack, this); //Uses custom itemstack class (for handling nbt/the inventory)
    }


    private static BackpackItem[] backpacks;
    private static String[] names = {"Tiny", "Small", "Medium", "Big", "Huge", "Very Huge"};
    private static class BackpackIncreaseSiteCraftingResult implements OilCraftingResult {

        @Override
        public ItemStack getResult(ItemStack[] itemStacks, boolean shaped, int width, int height) {
            BackpackItemStack backpack = null;
            for (ItemStack itemStack:itemStacks) {
                if (itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof BackpackItemStack) {
                    backpack = (BackpackItemStack) ((OilBukkitItemStack) itemStack).getOilItemStack();
                    break;
                }
            }
            if (backpack == null) {
                throw new IllegalStateException("Somehow no backpack is enlarged, because there is no backpack");
            }
            if (backpack.getRows() >= backpacks.length) {
                return null; //Maximum size reached.
            }

            BackpackItemStack newBackpack = (BackpackItemStack) ((OilBukkitItemStack)backpacks[backpack.getRows()].createItemStack(1)).getOilItemStack(); //Creates a backpack of the next size
            backpack.copyTo(newBackpack); //copy inventory
            if (!backpack.createDisplayName().equals(backpack.getCurrentDisplayName())) {
                newBackpack.setDisplayName(backpack.getCurrentDisplayName(), true);//If old backpack was renamed. rename new backpack as well
            }
            return newBackpack.getNmsItemStack().asBukkitItemStack();
        }
    }
    public static void registerBackpacks(ItemRegistry registry, int startId) {
        //Register Items
        BackpackShoulderStrapsItem shoulderStrapsItem=new BackpackShoulderStrapsItem(startId++); //creates item with free id
        BackpackSackItem sackItem=new BackpackSackItem(startId++);
        registry.register(shoulderStrapsItem); //registers the,
        registry.register(sackItem);
        backpacks = new BackpackItem[names.length];
        for (int idOffset=0;idOffset<names.length;idOffset++) {
            BackpackItem item = new BackpackItem(startId+idOffset, 1+idOffset); //creates all the different backpacks
            registry.register(item);
            backpacks[idOffset] = item;
        }
        //Add crafting recipes
        OilCraftingRecipe recipe;
        recipe = ItemCraftingFactory.createShapedRecipe(2,3, new OilItemOilCraftingResult(shoulderStrapsItem, 4), Material.STRING, Material.LEATHER, null, Material.LEATHER, Material.IRON_INGOT, Material.LEATHER);
        ItemCraftingFactory.registerGlobal(recipe);
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(sackItem, 1), Material.PAPER, Material.PAPER, Material.PAPER, Material.LEATHER, Material.STICK, Material.LEATHER, Material.STRING, Material.LEATHER, Material.STRING);
        ItemCraftingFactory.registerGlobal(recipe);
        recipe = ItemCraftingFactory.createShapedRecipe(2,2, new OilItemOilCraftingResult(backpacks[0], 1), BackpackSackItem.class, BackpackShoulderStrapsItem.class, Material.SLIME_BALL, BackpackShoulderStrapsItem.class);
        ItemCraftingFactory.registerGlobal(recipe);
        recipe = ItemCraftingFactory.createShapelessRecipe(new BackpackIncreaseSiteCraftingResult(), BackpackItem.class, BackpackSackItem.class, Material.LEATHER, Material.PAPER);
        ItemCraftingFactory.registerGlobal(recipe);
    }

}
