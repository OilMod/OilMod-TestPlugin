package de.sirati97.oilmod.api.test.magic2.ui;

import de.sirati97.oilmod.api.test.magic2.WandItemStack;
import de.sirati97.oilmod.api.userinterface.UIPanel;
import de.sirati97.oilmod.api.userinterface.UserInterfaceBuilder;
import de.sirati97.oilmod.api.userinterface.internal.UserInterface;
import de.sirati97.oilmod.api.userinterface.internal.UserInterfaceFactory;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-TestPlugin.
 */
public class WandUIBuilder extends UserInterfaceBuilder<WandItemStack> {
    public final static WandUIBuilder INSTANCE = new WandUIBuilder();

    private WandUIBuilder(){}

    @Override
    public void displayNewUI(Player player, WandItemStack wandItemStack) {
        super.displayNewUI(player, wandItemStack);
    }

    @Override
    protected UserInterface buildDisplay(Player player, WandItemStack wandItemStack, UserInterfaceFactory factory) {
        int rows = 4+(wandItemStack.getWandforcyContainer().getBukkitInventory().getSize()+8)/9;
        UserInterface ui = factory.createChestInterface(player, this, ChatColor.stripColor(wandItemStack.getCurrentDisplayName()), rows);
        UIPanel playerPanel = ui.createPlayerPanel(true, false, false, true);
        WandUIPanel wandUIPanel = new WandUIPanel(9, rows, wandItemStack);
        ui.showPanel(playerPanel, wandUIPanel);
        return ui;
    }
}
