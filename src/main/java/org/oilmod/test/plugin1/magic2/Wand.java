package org.oilmod.test.plugin1.magic2;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public interface Wand {
    void useVis(int amount);
    boolean checkVis(int amount);
    Wandforcy getWandforcy();
    void setWandforcy(Wandforcy wandforcy);
    String getCurrentDisplayName();
}
