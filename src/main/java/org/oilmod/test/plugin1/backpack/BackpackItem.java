package org.oilmod.test.plugin1.backpack;

import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.ItemRegistry;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.items.crafting.DataHolder;
import org.oilmod.api.items.crafting.ItemCraftingFactory;
import org.oilmod.api.items.crafting.OilModItemClassIngredient;
import org.oilmod.api.items.crafting.OilCraftingRecipe;
import org.oilmod.api.items.crafting.OilCraftingResult;
import org.oilmod.api.items.crafting.OilItemCraftingResult;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.oilmod.api.items.type.IUnique;
import org.oilmod.api.util.InteractionResult;

import java.util.Random;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class BackpackItem extends OilItem implements IUnique {
    private final int rows;

    public BackpackItem(OilMod mod, int rowsIndex) {
        super(mod.createKey("backpack_" + names[rowsIndex].replace(" ","_").toLowerCase()), Material.LEATHER,names[rowsIndex] + " Backpack"); //defines Backpack item
        this.rows = rowsIndex+1;
    }

    public int getRows() {
        return rows;
    }

    @Override
    public BackpackItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new BackpackItemStack(nmsItemStack, this); //Uses custom itemstack class (for handling nbt/the inventory)
    }

    @Override
    public ItemInteractionResult onItemRightClick(OilItemStack stack, World world, HumanEntity human, boolean offhand) {
        human.openInventory(stack.getMainInventory().getBukkitInventory()); //onUse event - opens inventory
        return new ItemInteractionResult(InteractionResult.SUCCESS, stack.asBukkitItemStack());
    }

    private static BackpackItem[] backpacks;
    private static String[] names = {"Tiny", "Small", "Medium", "Big", "Huge", "Very Huge"};
    private static class BackpackIncreaseSizeCraftingResult implements OilCraftingResult {

        @Override
        public ItemStack preCraftResult(ItemStack[] itemStacks, boolean shaped, int width, int height) { //Here are all fast crafting processes that create the result
            BackpackItemStack backpack = findBackpack(itemStacks);
            if (backpack.getRows() >= backpacks.length) {
                return null; //Maximum size reached.
            }

            BackpackItemStack newBackpack = (BackpackItemStack) ((OilBukkitItemStack)backpacks[backpack.getRows()].createItemStack(1)).getOilItemStack(); //Creates a backpack of the next size
            if (backpack.isRenamed()) {
                newBackpack.setRename(backpack.getRename());//If old backpack was renamed. rename new backpack as well
            }
            return newBackpack.getNmsItemStack().asItemStackRep();
        }

        @Override
        public void craftResult(ItemStack result, ItemStack[] itemStacks, boolean shaped, int width, int height) { //Here are all expensive processes that doesn't change the appearance of the result item.
            BackpackItemStack backpack = findBackpack(itemStacks);

            BackpackItemStack newBackpack = (BackpackItemStack) ((OilBukkitItemStack)result).getOilItemStack(); //Gets backpack itemstack class
            backpack.copyTo(newBackpack); //copy inventory
        }

        private BackpackItemStack findBackpack(ItemStack[] itemStacks) {
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
            return backpack;
        }
    }

    private static class BackpackIncreaseSizeIngredient extends OilModItemClassIngredient {

        public BackpackIncreaseSizeIngredient() {
            super(BackpackItem.class);
        }

        @Override
        public ItemStack getRandomExample(Random rnd, DataHolder dataHolder) {
            ItemStack[] result = backpacks[rnd.nextInt(backpacks.length-1)].getNaturalExamples(); //Will ensure that all examples are valid as the biggest backpack cannot be enlarged
            return result[rnd.nextInt(result.length)];
        }
    }

    public static void registerBackpacks(ItemRegistry registry) {
        OilMod m = registry.getMod();
        //Register Items
        BackpackShoulderStrapsItem shoulderStrapsItem=new BackpackShoulderStrapsItem(m);
        BackpackSackItem sackItem=new BackpackSackItem(m);
        registry.register(shoulderStrapsItem); //registers the,
        registry.register(sackItem);
        backpacks = new BackpackItem[names.length];
        for (int rows=0;rows<names.length;rows++) {
            BackpackItem item = new BackpackItem(m, rows); //creates all the different backpacks
            registry.register(item);
            backpacks[rows] = item;
        }
        //Add crafting recipes
        OilCraftingRecipe recipe;
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_shoulder_strap"), 2,3, new OilItemCraftingResult(shoulderStrapsItem, 4), Material.STRING, Material.LEATHER, null, Material.LEATHER, Material.IRON_INGOT, Material.LEATHER);
        ItemCraftingFactory.registerGlobal(recipe);
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_sack"), 3,3, new OilItemCraftingResult(sackItem, 1), Material.PAPER, Material.PAPER, Material.PAPER, Material.LEATHER, Material.STICK, Material.LEATHER, Material.STRING, Material.LEATHER, Material.STRING);
        ItemCraftingFactory.registerGlobal(recipe);
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_backpack"), 2,2, new OilItemCraftingResult(backpacks[0], 1), BackpackSackItem.class, BackpackShoulderStrapsItem.class, Material.SLIME_BALL, BackpackShoulderStrapsItem.class);
        ItemCraftingFactory.registerGlobal(recipe);
        recipe = ItemCraftingFactory.createShapelessRecipe(m.createKey("backpack_increase_size"), new BackpackIncreaseSizeCraftingResult(), new BackpackIncreaseSizeIngredient(), BackpackSackItem.class, Material.LEATHER, Material.PAPER);
        ItemCraftingFactory.registerGlobal(recipe);
    }

}
