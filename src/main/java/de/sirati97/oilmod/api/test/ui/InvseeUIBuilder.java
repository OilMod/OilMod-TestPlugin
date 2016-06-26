package de.sirati97.oilmod.api.test.ui;

import de.sirati97.oilmod.api.userinterface.UIPanel;
import de.sirati97.oilmod.api.userinterface.UserInterfaceBuilder;
import de.sirati97.oilmod.api.userinterface.internal.UserInterface;
import de.sirati97.oilmod.api.userinterface.internal.UserInterfaceFactory;
import org.bukkit.entity.Player;

/**
 * Created by sirati97 on 18.06.2016 for OilMod-TestPlugin.
 */
public class InvseeUIBuilder extends UserInterfaceBuilder<Player> {

    public void displayNewUI(Player player, Player other) {
        super.displayNewUI(player, other);
    }

    @Override
    protected UserInterface buildDisplay(Player player, Player other, UserInterfaceFactory userInterfaceFactory) {
        UserInterface ui = userInterfaceFactory.createChestInterface(player, this, other.getName() + "'s Inv", 5);
        UIPanel playerPanel = ui.createPlayerPanel(true, false, false, true);
        UIPanel otherPanel = ui.createPlayerPanel(other, false, true, true, true);
        ui.showPanel(playerPanel, otherPanel);
        return ui;
    }

}