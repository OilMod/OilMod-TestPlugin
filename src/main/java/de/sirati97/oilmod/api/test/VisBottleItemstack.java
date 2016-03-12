package de.sirati97.oilmod.api.test;

import de.sirati97.oilmod.api.data.ShortData;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.OilItemStack;

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
        setDisplayName(getItem().getName() + " with " + vis + " Vis ");
        this.vis.setData((short) vis);
    }
}
