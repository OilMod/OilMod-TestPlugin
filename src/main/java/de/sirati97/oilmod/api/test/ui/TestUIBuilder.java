package de.sirati97.oilmod.api.test.ui;

import de.sirati97.oilmod.api.userinterface.Click;
import de.sirati97.oilmod.api.userinterface.UIArgument;
import de.sirati97.oilmod.api.userinterface.UIElementBase;
import de.sirati97.oilmod.api.userinterface.UIFormedFixedSizePanel;
import de.sirati97.oilmod.api.userinterface.UserInterfaceBuilder;
import de.sirati97.oilmod.api.userinterface.internal.Interface;
import de.sirati97.oilmod.api.userinterface.internal.InterfaceFactory;
import de.sirati97.oilmod.api.userinterface.internal.NMSClickData;
import de.sirati97.oilmod.api.util.OilUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-TestPlugin.
 */
public class TestUIBuilder extends UserInterfaceBuilder {

    public void displayNewUI(Player player) {
        //noinspection unchecked
        super.displayNewUI(player, null);
    }

    @Override
    protected Interface buildDisplay(Player player, UIArgument argument, InterfaceFactory interfaceFactory) {
        Interface ui = interfaceFactory.createChestInterface(player, this, "TestInv", 2);
        UIFormedFixedSizePanel panel = new UIFormedFixedSizePanel(9, 2);
        ui.showPanel(null, panel);
        panel.setUIElement(0, 0, new UIElementBase() {
            {
                setDisplayed(OilUtil.createItemStackNamed(Material.WOOL, 1, (short)13, "Test item 1"));
            }

            @Override
            public void setDisplayed(ItemStack displayed) {
                super.setDisplayed(displayed);
                System.out.println(displayed.getClass().toString() + " replaced with");
            }

            @Override
            public void onClick(Player player, Click click, NMSClickData nmsClickData) {
                player.sendMessage("1");
            }
        });
        return ui;
    }
}
