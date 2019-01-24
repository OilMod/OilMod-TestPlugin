package org.oilmod.test.plugin1.magic2;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.NMSItemStack;
import org.oilmod.api.items.OilItem;
import org.bukkit.Material;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.items.type.ITool;
import org.oilmod.api.util.InteractionResult;
import org.oilmod.test.plugin1.magic2.ui.WandUIBuilder;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-TestPlugin.
 */
public class BasicWandItem extends WandItemBase {
    public BasicWandItem(OilMod mod) {
        super(mod.createKey("wand_basic"), Material.BLAZE_ROD, "Basic Wand");
    }

    @Override
    protected BasicWandItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new BasicWandItemStack(nmsItemStack, this);
    }


}
