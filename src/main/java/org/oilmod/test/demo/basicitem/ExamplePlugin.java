package org.oilmod.test.demo.basicitem;

import org.bukkit.plugin.java.JavaPlugin;
import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemRegistry;

public class ExamplePlugin  extends JavaPlugin{
    private OilMod mod;
    private ItemRegistry itemRegistry;

    @Override
    public void onEnable() {
        mod = new OilMod("example_mod", "Example Mod");
        itemRegistry = new ItemRegistry(mod);
        itemRegistry.register(new BasicExampleItem(mod.createKey("basic_example_item")));
    }
}
