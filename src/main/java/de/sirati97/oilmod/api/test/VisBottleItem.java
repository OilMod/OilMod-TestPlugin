package de.sirati97.oilmod.api.test;

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
public class VisBottleItem  extends OilItemBase{
    private final OilSpecificItemstackFactory[] creativeItems = new OilSpecificItemstackFactory[] {new VisBottleItemstackFactory((short)1),new VisBottleItemstackFactory((short)9),new VisBottleItemstackFactory((short)81),new VisBottleItemstackFactory((short)729)};

    public VisBottleItem() {
        super(Material.POTION, 0, 6, 64, "Vis Bottle");
    }

    @Override
    public VisBottleItemStack createOilStack(NMSItemStack nmsItemStack) {
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

    private class VisBottleItemstackFactory implements OilSpecificItemstackFactory {
        private final short vis;

        private VisBottleItemstackFactory(short vis) {
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
