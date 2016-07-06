package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.ItemRegistry;
import de.sirati97.oilmod.api.items.crafting.InterchangeableCraftingIngredient;
import de.sirati97.oilmod.api.items.crafting.ItemCraftingFactory;
import de.sirati97.oilmod.api.items.crafting.OilCraftingRecipe;
import de.sirati97.oilmod.api.items.crafting.OilItemOilCraftingResult;
import de.sirati97.oilmod.api.items.crafting.VanillaEnchantedBookCraftingIngredient;
import de.sirati97.oilmod.api.items.crafting.VanillaPotionCraftingIngredient;
import de.sirati97.oilmod.api.test.magic2.wandforcy.ArrowWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.wandforcy.DamageBeamWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.wandforcy.FlameBeamWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.wandforcy.ItemMagnetWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.wandforcy.LifestealBeamWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.wandforcy.OreMagnetWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.wandforcy.ReplaceWandforcyItem;
import de.sirati97.oilmod.api.test.magic2.wandforcy.SniperWandforcyItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-TestPlugin.
 */
public final class MagicUtil {
    private MagicUtil(){throw new UnsupportedOperationException();}

    public static void register(ItemRegistry registry) {
        //register items
        VisContainerItem visContainerItem=new VisContainerItem();
        registry.register(visContainerItem);
        BasicWandItem basicBasicWandItem = new BasicWandItem();
        registry.register(basicBasicWandItem);
        BlankWandforcyItem blankWandforcyItem = new BlankWandforcyItem();
        registry.register(blankWandforcyItem);
        ArrowWandforcyItem arrowWandforcyItem = new ArrowWandforcyItem();
        registry.register(arrowWandforcyItem);
        ReplaceWandforcyItem replaceWandforcyItem = new ReplaceWandforcyItem();
        registry.register(replaceWandforcyItem);
        OreMagnetWandforcyItem oreMagnetWandforcyItem = new OreMagnetWandforcyItem();
        registry.register(oreMagnetWandforcyItem);
        FlameBeamWandforcyItem flameBeamWandforcyItem = new FlameBeamWandforcyItem();
        registry.register(flameBeamWandforcyItem);
        LifestealBeamWandforcyItem lifestealBeamWandforcyItem = new LifestealBeamWandforcyItem();
        registry.register(lifestealBeamWandforcyItem);
        SniperWandforcyItem sniperWandforcyItem = new SniperWandforcyItem();
        registry.register(sniperWandforcyItem);
        DamageBeamWandforcyItem damageBeamWandforcyItem = new DamageBeamWandforcyItem();
        registry.register(damageBeamWandforcyItem);
        ItemMagnetWandforcyItem itemMagnetWandforcyItem = new ItemMagnetWandforcyItem();
        registry.register(itemMagnetWandforcyItem);

        //register crafting
        OilCraftingRecipe recipe;


        ItemStack lapislazuli = new ItemStack(Material.INK_SACK, 1, (short) 4);
        ItemStack witherskull = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
        VanillaPotionCraftingIngredient poisonLongNotNormal = new VanillaPotionCraftingIngredient(PotionType.POISON, VanillaPotionCraftingIngredient.PotionUpgraded.NotUpgraded, VanillaPotionCraftingIngredient.PotionExtended.Extended, VanillaPotionCraftingIngredient.PotionBottleType.Throwable, VanillaPotionCraftingIngredient.PotionBottleType.Lingering);
        VanillaPotionCraftingIngredient regenLongNotNormal = new VanillaPotionCraftingIngredient(PotionType.REGEN, VanillaPotionCraftingIngredient.PotionUpgraded.NotUpgraded, VanillaPotionCraftingIngredient.PotionExtended.Extended, VanillaPotionCraftingIngredient.PotionBottleType.Throwable, VanillaPotionCraftingIngredient.PotionBottleType.Lingering);
        InterchangeableCraftingIngredient allGoldTools = new InterchangeableCraftingIngredient(noId(Material.GOLD_SWORD), noId(Material.GOLD_SPADE), noId(Material.GOLD_PICKAXE), noId(Material.GOLD_AXE));


        //###VisContainerItem###
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(visContainerItem, 2),
                Material.IRON_INGOT,  Material.ENDER_PEARL, Material.IRON_INGOT,
                Material.LAPIS_BLOCK, Material.LAPIS_BLOCK, Material.LAPIS_BLOCK,
                Material.IRON_INGOT,  Material.GOLD_INGOT,  Material.IRON_INGOT);
        ItemCraftingFactory.registerGlobal(recipe);


        //###BasicWand###

        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(basicBasicWandItem, 1),
                null,                   Material.BLAZE_POWDER, Material.DIAMOND,
                VisContainerItem.class, Material.STICK,        Material.BLAZE_POWDER,
                Material.IRON_BLOCK,    visContainerItem,      null);
        ItemCraftingFactory.registerGlobal(recipe);


        //###BlankWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(blankWandforcyItem, 1),
                Material.REDSTONE,   Material.IRON_INGOT,  Material.REDSTONE,
                Material.IRON_INGOT, noId(Material.STONE), Material.IRON_INGOT,
                lapislazuli,         Material.IRON_INGOT,  lapislazuli);
        ItemCraftingFactory.registerGlobal(recipe);

        //###ArrowWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(arrowWandforcyItem, 1),
                lapislazuli,         new VanillaEnchantedBookCraftingIngredient(Enchantment.ARROW_INFINITE, 1), lapislazuli,
                Material.IRON_INGOT, blankWandforcyItem,                                                        Material.IRON_INGOT,
                Material.ARROW,      Material.ARROW,                                                            Material.ARROW);
        ItemCraftingFactory.registerGlobal(recipe);


        //###ReplaceWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(replaceWandforcyItem, 1),
                lapislazuli,  Material.DISPENSER,             lapislazuli,
                allGoldTools, blankWandforcyItem,             allGoldTools,
                allGoldTools, noId(Material.DIAMOND_PICKAXE), allGoldTools);
        ItemCraftingFactory.registerGlobal(recipe);


        //###OreMagnetWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(oreMagnetWandforcyItem, 1),
                Material.REDSTONE_BLOCK, Material.DIAMOND,    Material.REDSTONE_BLOCK,
                Material.IRON_BLOCK,     blankWandforcyItem,  Material.IRON_BLOCK,
                Material.IRON_BLOCK,     Material.IRON_BLOCK, Material.IRON_BLOCK);
        ItemCraftingFactory.registerGlobal(recipe);


        //###FlameBeamWandforcyItem###
        recipe = ItemCraftingFactory.createShapelessRecipe(new OilItemOilCraftingResult(flameBeamWandforcyItem, 1),
                Material.FIREBALL, blankWandforcyItem, Material.FIREBALL);
        ItemCraftingFactory.registerGlobal(recipe);


        //###LifestealBeamWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(lifestealBeamWandforcyItem, 1),
                Material.DIAMOND,    witherskull,        Material.DIAMOND,
                poisonLongNotNormal, blankWandforcyItem, regenLongNotNormal,
                lapislazuli,         lapislazuli,        lapislazuli);
        ItemCraftingFactory.registerGlobal(recipe);


        //###SniperWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(sniperWandforcyItem, 1),
                Material.REDSTONE,   new VanillaEnchantedBookCraftingIngredient(Enchantment.ARROW_DAMAGE, 5), Material.REDSTONE,
                Material.IRON_INGOT, blankWandforcyItem,                                                      Material.IRON_INGOT,
                Material.IRON_INGOT, Material.DIAMOND,                                                        Material.IRON_INGOT);
        ItemCraftingFactory.registerGlobal(recipe);


        //###DamageBeamWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(damageBeamWandforcyItem, 1),
                null,              noId(Material.IRON_SWORD), null,
                Material.REDSTONE, blankWandforcyItem,        Material.REDSTONE,
                lapislazuli,       lapislazuli,               lapislazuli);
        ItemCraftingFactory.registerGlobal(recipe);


        //###ItemMagnetWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(itemMagnetWandforcyItem, 1),
                Material.REDSTONE  , Material.ENDER_PEARL, Material.REDSTONE,
                Material.IRON_INGOT, blankWandforcyItem,   Material.IRON_INGOT,
                Material.IRON_INGOT, Material.IRON_INGOT,  Material.IRON_INGOT);
        ItemCraftingFactory.registerGlobal(recipe);


    }

    private static void mirrorVertical(Object[] matrix, int width, int height) {
        Object temp;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width/2; j++) {
                temp = matrix[i*width+j];
                matrix[i*width+j] = matrix[(i+1)*width-j-1];
                matrix[(i+1)*width-j-1] = temp;
            }
        }
    }

    private static ItemStack noId(Material material) {
        return new ItemStack(material, 1, (short) 0);
    }
}
