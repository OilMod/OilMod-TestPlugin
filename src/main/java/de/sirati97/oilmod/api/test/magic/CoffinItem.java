package de.sirati97.oilmod.api.test.magic;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import de.sirati97.oilmod.api.items.OilSpecificItemstackFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class CoffinItem extends OilItemBase<VisBottleItemStack>{
    private final OilSpecificItemstackFactory[] creativeItems = new OilSpecificItemstackFactory[] {new CoffinItemstackFactory((short)1),new CoffinItemstackFactory((short)9),new CoffinItemstackFactory((short)81),new CoffinItemstackFactory((short)729)};

    public CoffinItem() {
        super(Material.ENDER_CHEST, 0, 6, 64, "Coffin");
    }

    @Override
    public VisBottleItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new VisBottleItemStack(nmsItemStack, this);
    }

    @Override
    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return true;
    }

    @Override
    protected OilSpecificItemstackFactory[] createCreativeItems() {
        return creativeItems;
    }

    private class CoffinItemstackFactory implements OilSpecificItemstackFactory {
        private final short vis;

        private CoffinItemstackFactory(short vis) {
            this.vis = vis;
        }

        @Override
        public ItemStack create() {
            ItemStack result = createItemStack(1);
            OilBukkitItemStack oilStack = (OilBukkitItemStack) result;
            ((VisBottleItemStack)oilStack.getOilItemStack()).setVis(vis);
            return result;
        }
    }
}
