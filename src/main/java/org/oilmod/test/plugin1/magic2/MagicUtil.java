package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemRegistry;
import org.oilmod.api.items.crafting.InterchangeableCraftingIngredient;
import org.oilmod.api.items.crafting.ItemCraftingFactory;
import org.oilmod.api.items.crafting.OilCraftingRecipe;
import org.oilmod.api.items.crafting.OilItemCraftingResult;
import org.oilmod.api.items.crafting.VanillaEnchantmentIngredient;
import org.oilmod.api.items.crafting.VanillaPotionIngredient;
import org.oilmod.api.rep.providers.BlockStateProvider;
import org.oilmod.api.rep.providers.ItemStackStateProvider;
import org.oilmod.api.rep.providers.ItemStateProvider;
import org.oilmod.api.rep.providers.minecraft.MinecraftBlock;
import org.oilmod.api.rep.providers.minecraft.MinecraftItem;
import org.oilmod.test.plugin1.magic2.wandforcy.ArrowWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.DamageBeamWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.FlameBeamWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.ItemMagnetWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.LifestealBeamWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.OreMagnetWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.ReplaceWandforcyItem;
import org.oilmod.test.plugin1.magic2.wandforcy.SniperWandforcyItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import static org.oilmod.api.rep.providers.minecraft.MinecraftItem.*;
import static org.oilmod.api.rep.providers.minecraft.MinecraftBlock.*;

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


        ItemStateProvider lapislazuli = LAPIS_LAZULI;

        BlockStateProvider witherskull = WITHER_SKELETON_SKULL;
        VanillaPotionIngredient poisonLongNotNormal = new VanillaPotionIngredient(PotionType.POISON, VanillaPotionIngredient.PotionUpgraded.NotUpgraded, VanillaPotionIngredient.PotionExtended.Extended, VanillaPotionIngredient.PotionBottleType.Throwable, VanillaPotionIngredient.PotionBottleType.Lingering);
        VanillaPotionIngredient regenLongNotNormal = new VanillaPotionIngredient(PotionType.REGEN, VanillaPotionIngredient.PotionUpgraded.NotUpgraded, VanillaPotionIngredient.PotionExtended.Extended, VanillaPotionIngredient.PotionBottleType.Throwable, VanillaPotionIngredient.PotionBottleType.Lingering);
        InterchangeableCraftingIngredient allGoldTools = new InterchangeableCraftingIngredient(noId(GOLDEN_SWORD), noId(GOLDEN_SHOVEL), noId(GOLDEN_PICKAXE), noId(GOLDEN_AXE));


        //###VisContainerItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_vis_container"), 3,3, new OilItemCraftingResult(visContainerItem, 2),
                IRON_INGOT,  ENDER_PEARL, IRON_INGOT,
                LAPIS_BLOCK, LAPIS_BLOCK, LAPIS_BLOCK,
                IRON_INGOT,  GOLD_INGOT,  IRON_INGOT);
        ItemCraftingFactory.registerGlobal(recipe);


        //###BasicWand###

        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wand_basic"), 3,3, new OilItemCraftingResult(basicBasicWandItem, 1),
                null,                   BLAZE_POWDER, DIAMOND,
                VisContainerItem.class, STICK,        BLAZE_POWDER,
                MinecraftBlock.IRON_BLOCK,    visContainerItem,      null);
        ItemCraftingFactory.registerGlobal(recipe);


        //###BlankWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_blank"), 3,3, new OilItemCraftingResult(blankWandforcyItem, 1),
                REDSTONE,   IRON_INGOT,  REDSTONE,
                IRON_INGOT, noId(STONE), IRON_INGOT,
                lapislazuli,         IRON_INGOT,  lapislazuli);
        ItemCraftingFactory.registerGlobal(recipe);

        //###ArrowWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_arrow"), 3,3, new OilItemCraftingResult(arrowWandforcyItem, 1),
                lapislazuli,         new VanillaEnchantmentIngredient(Enchantment.ARROW_INFINITE, 1), lapislazuli,
                IRON_INGOT, blankWandforcyItem,                                                        IRON_INGOT,
                ARROW,      ARROW,                                                            ARROW);
        ItemCraftingFactory.registerGlobal(recipe);


        //###ReplaceWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_replace"), 3,3, new OilItemCraftingResult(replaceWandforcyItem, 1),
                lapislazuli,  DISPENSER,             lapislazuli,
                allGoldTools, blankWandforcyItem,             allGoldTools,
                allGoldTools, noId(DIAMOND_PICKAXE), allGoldTools);
        ItemCraftingFactory.registerGlobal(recipe);


        //###OreMagnetWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_ore_magnet"), 3,3, new OilItemCraftingResult(oreMagnetWandforcyItem, 1),
                REDSTONE_BLOCK, DIAMOND,    REDSTONE_BLOCK,
                IRON_BLOCK,     blankWandforcyItem,  IRON_BLOCK,
                IRON_BLOCK,     IRON_BLOCK, IRON_BLOCK);
        ItemCraftingFactory.registerGlobal(recipe);


        //###FlameBeamWandforcyItem###
        recipe = ItemCraftingFactory.createShapelessRecipe(m.createKey("crafting_wandforcy_flame_beam"), new OilItemCraftingResult(flameBeamWandforcyItem, 1),
                FIREWORK_STAR, blankWandforcyItem, FIREWORK_STAR);
        ItemCraftingFactory.registerGlobal(recipe);


        //###LifestealBeamWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_lifesteal_beam"), 3,3, new OilItemCraftingResult(lifestealBeamWandforcyItem, 1),
                DIAMOND,    witherskull,        DIAMOND,
                poisonLongNotNormal, blankWandforcyItem, regenLongNotNormal,
                lapislazuli,         lapislazuli,        lapislazuli);
        ItemCraftingFactory.registerGlobal(recipe);


        //###SniperWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_sniper"), 3,3, new OilItemCraftingResult(sniperWandforcyItem, 1),
                REDSTONE,   new VanillaEnchantmentIngredient(Enchantment.ARROW_DAMAGE, 5), REDSTONE,
                IRON_INGOT, blankWandforcyItem,                                                      IRON_INGOT,
                IRON_INGOT, DIAMOND,                                                        IRON_INGOT);
        ItemCraftingFactory.registerGlobal(recipe);


        //###DamageBeamWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_damage_beam"), 3,3, new OilItemCraftingResult(damageBeamWandforcyItem, 1),
                null,              noId(IRON_SWORD), null,
                REDSTONE, blankWandforcyItem,        REDSTONE,
                lapislazuli,       lapislazuli,               lapislazuli);
        ItemCraftingFactory.registerGlobal(recipe);


        //###ItemMagnetWandforcyItem###
        recipe = ItemCraftingFactory.createShapedRecipe(m.createKey("crafting_wandforcy_item_magnet"), 3,3, new OilItemCraftingResult(itemMagnetWandforcyItem, 1),
                REDSTONE  , ENDER_PEARL, REDSTONE,
                IRON_INGOT, blankWandforcyItem,   IRON_INGOT,
                IRON_INGOT, IRON_INGOT,  IRON_INGOT);
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

    private static ItemStack noId(MinecraftItem.MinecraftItem. {
        return new ItemStack(MinecraftItem. 1, (short) 0);
    }
}
