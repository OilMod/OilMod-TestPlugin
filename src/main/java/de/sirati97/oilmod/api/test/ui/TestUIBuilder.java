package de.sirati97.oilmod.api.test.ui;

import de.sirati97.oilmod.api.userinterface.Click;
import de.sirati97.oilmod.api.userinterface.InteractableUIElementBase;
import de.sirati97.oilmod.api.userinterface.SimpleUIElementBase;
import de.sirati97.oilmod.api.userinterface.UIElement;
import de.sirati97.oilmod.api.userinterface.UIFormedFixedSizePanel;
import de.sirati97.oilmod.api.userinterface.UserInterfaceBuilder;
import de.sirati97.oilmod.api.userinterface.internal.NMSClickData;
import de.sirati97.oilmod.api.userinterface.internal.UserInterface;
import de.sirati97.oilmod.api.userinterface.internal.UserInterfaceFactory;
import de.sirati97.oilmod.api.util.OilUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-TestPlugin.
 */
public class TestUIBuilder extends UserInterfaceBuilder {

    public void displayNewUI(Player player) {
        //noinspection unchecked (unterdrückt eine warnung)
        super.displayNewUI(player, null); //ruft methode auf ohne ein argument zu übergeben
    }

    @Override
    protected UserInterface buildDisplay(Player player, Object argument, UserInterfaceFactory interfaceFactory) {
        UserInterface ui = interfaceFactory.createChestInterface(player, this, "Test Inventory", 2); //erzeugt ein userinterface für spieler player mit dem namen 'Test Inventory' und zwei reihen
        UIFormedFixedSizePanel panel = new UIFormedFixedSizePanel(9, 2); //erzeugt ein panel mit der größe 9*2 (entspricht zwei reihen)
        UIFormedFixedSizePanel playerPanel = new UIFormedFixedSizePanel(9, 4);
        ui.showPanel(playerPanel, panel); //zeigt für das spieler inventar nichts an und für das chest inventar das eben erstellte panel
        UIElement element = new SimpleUIElementBase() { //implementiert die logic für einen item knopf
            {
                setDisplayed(OilUtil.createItemStackNamed(Material.WOOL, 1, (short)13, "Test item 1")); //das angezeigt item entspricht diesem
            }

            @Override
            public void onClick(Player player, int index, Click click, NMSClickData nmsClickData) {
                player.sendMessage(String.valueOf(index+1)); //dieser code wird ausgeführt wenn die spieler einer interaction mit dem item knopf durchführt (rechtklick, linksklick, mittelklick, doppelklick, mit q werfen, shift klick und so was)
            }
        };

        panel.setUIElement(0, 0, element); //sagt dass an der stelle 0|0 der button knopf angezeigt werden soll
        panel.setUIElement(1, 1, element); //sagt dass an der stelle 1|1 der button knopf angezeigt werden soll

        UIElement element2 = new InteractableUIElementBaseTest();
        UIElement element3 = new InteractableUIElementBaseTest(new ItemStack(Material.WOOD));
        panel.setUIElement(1, 0, element3);
        panel.setUIElement(2, 0, element2);
        panel.setUIElement(3, 0, element3);
        playerPanel.setUIElement(0, 0, new InteractableUIElementBaseTest());
        playerPanel.setUIElement(0, 1, new InteractableUIElementBaseTest());
        playerPanel.setUIElement(0, 2, new InteractableUIElementBaseTest());
        playerPanel.setUIElement(1, 2, new InteractableUIElementBaseTest2(new ItemStack(Material.STONE)));
        playerPanel.setUIElement(0, 3, new InteractableUIElementBaseTest());
        playerPanel.setUIElement(1, 3, new InteractableUIElementBaseTest2());


        return ui;
    }

    private class InteractableUIElementBaseTest extends InteractableUIElementBase {
        private ItemStack itemStack;

        public  InteractableUIElementBaseTest() {

        }

        public  InteractableUIElementBaseTest(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        @Override
        public ItemStack getDisplayed(int i) {
            return itemStack;
        }

        @Override
        public void setDisplayed(int i, ItemStack itemStack) {
            this.itemStack = itemStack;
        }
    }

    private class InteractableUIElementBaseTest2 extends InteractableUIElementBaseTest {

        public  InteractableUIElementBaseTest2() {
        }
        public  InteractableUIElementBaseTest2(ItemStack itemStack) {
            super(itemStack);
        }

        @Override
        public boolean isItemstackAllowed(int index, ItemStack itemStack) {
            return itemStack== null || itemStack.getType() == Material.STONE;
        }
    }
}
