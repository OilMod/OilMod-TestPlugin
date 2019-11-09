package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.OilItem;
import org.bukkit.Material;
import org.oilmod.api.rep.providers.minecraft.MinecraftItem;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-TestPlugin.
 */
public class BlankWandforcyItem extends OilItem {
    public BlankWandforcyItem(OilMod mod) {
        super(mod.createKey("wandforcy_blank"), MinecraftItem.FIREWORK_STAR, "Blank Wandforcy");
    }
}
