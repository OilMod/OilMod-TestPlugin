package de.sirati97.oilmod.api.test.magic;

import de.sirati97.oilmod.api.data.ShortData;
import de.sirati97.oilmod.api.items.ItemDescription;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sirati97 on 11.03.2016.
 */
public class VisBottleItemStack extends OilItemStack implements TransportableVisHolder{
    private ShortData vis = new ShortData("vis", this);

    public VisBottleItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        super(nmsItemStack, item);
    }

    public int getVis() {
        return vis.getData();
    }

    public void setVis(int vis) {
        //setDisplayName(getItem().getName());
        ItemDescription description = getItemDescription();
        description.setLine(0, "Stores " + vis + " Vis.", true);
        this.vis.setData((short) vis);
    }

    @Override
    protected List<String> createDescription() {
        return Arrays.asList("Stores " + vis.getData() + " Vis.");
    }
}
