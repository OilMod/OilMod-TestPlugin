package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemRegistry;
import org.oilmod.api.items.crafting.InterchangeableCraftingIngredient;
import org.oilmod.api.items.crafting.ItemCraftingFactory;
import org.oilmod.api.items.crafting.OilCraftingRecipe;
import org.oilmod.api.items.crafting.OilItemCraftingResult;
import org.oilmod.api.items.crafting.VanillaEnchantmentIngredient;
import org.oilmod.api.items.crafting.VanillaPotionIngredient;
import org.oilmod.test.plugin1.magic2.wandforcy.ArrowWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.DamageBeamWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.FlameBeamWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.ItemMagnetWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.LifestealBeamWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.OreMagnetWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.ReplaceWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.SniperWandforcyItem;
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
        OilMod m = registry.getMod();
        //register items
        VisContainerItem visContainerItem=new VisContainerItem(m);
        registry.register(visContainerItem);
        BasicWandItem basicBasicWandItem = new BasicWandItem(m);
        registry.register(basicBasicWandItem);
        BlankWandforcyItem blankWandforcyItem = new BlankWandforcyItem(m);
        registry.register(blankWandforcyItem);
        ArrowWandforcyItem arrowWandforcyItem = new ArrowWandforcyItem(m);
        registry.register(arrowWandforcyItem);
        ReplaceWandforcyItem replaceWandforcyItem = new ReplaceWandforcyItem(m);
        registry.register(replaceWandforcyItem);
        OreMagnetWandforcyItem oreMagnetWandforcyItem = new OreMagnetWandforcyItem(m);
        registry.register(oreMagnetWandforcyItem);
        FlameBeamWandforcyItem flameBeamWandforcyItem = new FlameBeamWandforcyItem(m);
        registry.register(flameBeamWandforcyItem);
        LifestealBeamWandforcyItem lifestealBeamWandforcyItem = new LifestealBeamWandforcyItem(m);
        registry.register(lifestealBeamWandforcyItem);
        SniperWandforcyItem sniperWandforcyItem = new SniperWandforcyItem(m);
        registry.register(sniperWandforcyItem);
        DamageBeamWandforcyItem damageBeamWandforcyItem = new DamageBeamWandforcyItem(m);
        registry.register(damageBeamWandforcyItem);
        ItemMagnetWandforcyItem itemMagnetWandforcyItem = new ItemMagnetWandforcyItem(m);
        registry.register(itemMagnetWandforcyItem);

        //register crafting
        OilCraftingRecipe recipe;


        ItemStack lapislazuli = new ItemStack(Material.INK_SACK, 1, (short) 4);
        ItemStack witherskull = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
        VanillaPotionIngredient poisonLongNotNormal = new VanillaPotionIngredient(PotionType.POISON, VanillaPotionIngredient.PotionUpgraded.NotUpgraded, VanillaPotionIngredient.PotionExtended.Extended, VanillaPotionIngredient.PotionBottleType.Throwable, VanillaPotionIngredient.PotionBottleType.Lingering);
        VanillaPotionIngredient regenLongNotNormal = new VanillaPotionIngredient(PotionType.REGEN, VanillaPotionIngredient.PotionUpgraded.NotUpgraded, VanillaPotionIngredient.PotionExtended.Extended, VanillaPotionIngredient.PotionBottleType.Throwable, VanillaPotionIngredient.PotionBottleType.Lingering);
        InterchangeableCraftingIngredient allGoldTools = new InterchangeableCraftingIngredient(noId(Material.GOLD_SWORD), noId(Material.GOLD_SPADE), noId(Material.GOLD_PICKAXE), noId(Material.GOLD_AXE));


        //###VisContainerItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_vis_container"), 3,3, new OilItemCraftingResult(visContainerItem, 2),
                Material.IRON_INGOT,  Material.ENDER_PEARL, Material.IRON_INGOT,
                Material.LAPIS_BLOCK, Material.LAPIS_BLOCK, Material.LAPIS_BLOCK,
                Material.IRON_INGOT,  Material.GOLD_INGOT,  Material.IRON_INGOT);
        ItemCraftingFactory.registerGlobal(recipe);


        //###BasicWand###

        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wand_basic"), 3,3, new OilItemCraftingResult(basicBasicWandItem, 1),
                null,                   Material.BLAZE_POWDER, Material.DIAMOND,
                VisContainerItem.class, Material.STICK,        Material.BLAZE_POWDER,
                Material.IRON_BLOCK,    visContainerItem,      null);
        ItemCraftingFactory.registerGlobal(recipe);


        //###BlankWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_blank"), 3,3, new OilItemCraftingResult(blankWandforcyItem, 1),
                Material.REDSTONE,   Material.IRON_INGOT,  Material.REDSTONE,
                Material.IRON_INGOT, noId(Material.STONE), Material.IRON_INGOT,
                lapislazuli,         Material.IRON_INGOT,  lapislazuli);
        ItemCraftingFactory.registerGlobal(recipe);

        //###ArrowWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_arrow"), 3,3, new OilItemCraftingResult(arrowWandforcyItem, 1),
                lapislazuli,         new VanillaEnchantmentIngredient(Enchantment.ARROW_INFINITE, 1), lapislazuli,
                Material.IRON_INGOT, blankWandforcyItem,                                                        Material.IRON_INGOT,
                Material.ARROW,      Material.ARROW,                                                            Material.ARROW);
        ItemCraftingFactory.registerGlobal(recipe);


        //###ReplaceWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_replace"), 3,3, new OilItemCraftingResult(replaceWandforcyItem, 1),
                lapislazuli,  Material.DISPENSER,             lapislazuli,
                allGoldTools, blankWandforcyItem,             allGoldTools,
                allGoldTools, noId(Material.DIAMOND_PICKAXE), allGoldTools);
        ItemCraftingFactory.registerGlobal(recipe);


        //###OreMagnetWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_ore_magnet"), 3,3, new OilItemCraftingResult(oreMagnetWandforcyItem, 1),
                Material.REDSTONE_BLOCK, Material.DIAMOND,    Material.REDSTONE_BLOCK,
                Material.IRON_BLOCK,     blankWandforcyItem,  Material.IRON_BLOCK,
                Material.IRON_BLOCK,     Material.IRON_BLOCK, Material.IRON_BLOCK);
        ItemCraftingFactory.registerGlobal(recipe);


        //###FlameBeamWandforcyItem###
        recipe = ItemCraftingFactory.createShapelessRecipe(m.createKey("crafting_wandforcy_flame_beam"), new OilItemCraftingResult(flameBeamWandforcyItem, 1),
                Material.FIREBALL, blankWandforcyItem, Material.FIREBALL);
        ItemCraftingFactory.registerGlobal(recipe);


        //###LifestealBeamWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_lifesteal_beam"), 3,3, new OilItemCraftingResult(lifestealBeamWandforcyItem, 1),
                Material.DIAMOND,    witherskull,        Material.DIAMOND,
                poisonLongNotNormal, blankWandforcyItem, regenLongNotNormal,
                lapislazuli,         lapislazuli,        lapislazuli);
        ItemCraftingFactory.registerGlobal(recipe);


        //###SniperWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_sniper"), 3,3, new OilItemCraftingResult(sniperWandforcyItem, 1),
                Material.REDSTONE,   new VanillaEnchantmentIngredient(Enchantment.ARROW_DAMAGE, 5), Material.REDSTONE,
                Material.IRON_INGOT, blankWandforcyItem,                                                      Material.IRON_INGOT,
                Material.IRON_INGOT, Material.DIAMOND,                                                        Material.IRON_INGOT);
        ItemCraftingFactory.registerGlobal(recipe);


        //###DamageBeamWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_damage_beam"), 3,3, new OilItemCraftingResult(damageBeamWandforcyItem, 1),
                null,              noId(Material.IRON_SWORD), null,
                Material.REDSTONE, blankWandforcyItem,        Material.REDSTONE,
                lapislazuli,       lapislazuli,               lapislazuli);
        ItemCraftingFactory.registerGlobal(recipe);


        //###ItemMagnetWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_item_magnet"), 3,3, new OilItemCraftingResult(itemMagnetWandforcyItem, 1),
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
