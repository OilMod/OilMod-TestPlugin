package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;
import org.oilmod.api.util.OilKey;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-TestPlugin.
 */
public abstract class WandforcyItemBase<T extends OilItemStack> extends OilItem<T> {
    private final String wandforcyName;

    public WandforcyItemBase(OilKey key, String wandforcyName) {
        super(key, Material.FIREWORK_CHARGE, 0, 1, wandforcyName + " Wandforcy");
        this.wandforcyName = wandforcyName;
    }

    public String getWandforcyName() {
        return wandforcyName;
    }
}
