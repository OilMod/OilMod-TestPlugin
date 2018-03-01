package org.oilmod.test.plugin1.magic2.wandforcy;

import org.oilmod.api.items.OilItemBase;
import org.oilmod.api.items.OilItemStack;
import org.bukkit.Material;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-TestPlugin.
 */
public abstract class WandforcyItemBase<T extends OilItemStack> extends OilItemBase<T>{
    private final String wandforcyName;

    public WandforcyItemBase(String itemIdentifier, String wandforcyName) {
        super(Material.FIREWORK_CHARGE, 0, itemIdentifier, 1, wandforcyName + " Wandforcy");
        this.wandforcyName = wandforcyName;
    }

    public String getWandforcyName() {
        return wandforcyName;
    }
}
