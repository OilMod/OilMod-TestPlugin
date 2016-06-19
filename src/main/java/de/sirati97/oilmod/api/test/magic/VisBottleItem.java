package de.sirati97.oilmod.api.test.magic;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;
import de.sirati97.oilmod.api.items.OilSpecificItemstackFactory;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class VisBottleItem  extends OilItemBase<VisBottleItemStack>{
    private final OilSpecificItemstackFactory[] creativeItems = new OilSpecificItemstackFactory[] {new VisBottleItemstackFactory((short)1),new VisBottleItemstackFactory((short)9),new VisBottleItemstackFactory((short)81),new VisBottleItemstackFactory((short)729)};

    public VisBottleItem() {
        super(Material.DRAGONS_BREATH, 0, "VisBottle", 64, "Vis Bottle");
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

    @Override
    public boolean canCombineAnvil(VisBottleItemStack visBottle, ItemStack itemStack, HumanEntity human) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof TransportableVisHolder;
    }

    @Override
    public void prepareCombineAnvil(VisBottleItemStack visBottle, ItemStack itemStack, HumanEntity human) {
        if (canCombineAnvil(visBottle, itemStack, human)) {
            TransportableVisHolder visHolder = (TransportableVisHolder) ((OilBukkitItemStack) itemStack).getOilItemStack();
            visBottle.setVis(visBottle.getVis()+visHolder.getVis());
        }
    }
}
