package de.sirati97.oilmod.api.test.magic2.ui;

import de.sirati97.oilmod.api.test.magic2.BasicWandItemStack;
import de.sirati97.oilmod.api.test.magic2.Wandforcy;
import de.sirati97.oilmod.api.userinterface.IInteractableUIElement;
import de.sirati97.oilmod.api.userinterface.SingleStackInteractableUIElement;
import de.sirati97.oilmod.api.userinterface.UIElementResult;
import de.sirati97.oilmod.api.userinterface.UIElementResultFactory;
import de.sirati97.oilmod.api.userinterface.UIFormedPanel;
import de.sirati97.oilmod.api.userinterface.UIPanel;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-TestPlugin.
 */
public class WandUIPanel extends UIFormedPanel {
    private final BasicWandItemStack wandItemStack;
//    private final IInteractableUIElement visElement;
//    private final IInteractableUIElement wandforcyElement;
    private final IInteractableUIElement activeElement;



    public WandUIPanel(int width, int height, BasicWandItemStack wandItemStack) {
        super(width, height);
        this.wandItemStack = wandItemStack;
//        this.visElement = wandItemStack.getVisContainer().createUIElement();
//        this.wandforcyElement = wandItemStack.getWandforcyContainer().createUIElement();
        this.activeElement = new SingleStackInteractableUIElement(wandItemStack.getWandforcyContainer(), wandItemStack.getWandforcyFilter(), 1);
    }

    @Override
    protected UIElementResult getUIElement(int left, int top, int rawIndex) {
        if (top < 2) {
            Wandforcy wandforcy = wandItemStack.getWandforcy();
            if (wandforcy != null) {
                UIPanel wandforcyPanel = wandforcy.getUIPanel();
                if (wandforcyPanel != null && rawIndex < wandforcyPanel.size()) {
                    return wandforcyPanel.getUIElement(rawIndex);
                }
            }
//        } else if (top+1 <getHeight()) {
//            int localIndex = left + (top-2)*getWidth();
//            if (localIndex < wandItemStack.getWandforcyContainer().getBukkitInventory().getSize()) {
//                return UIElementResultFactory.createResult(wandforcyElement, localIndex);
//            }

        } else {
//            if (left < 5) {
//                return UIElementResultFactory.createResult(visElement, left);
//            } else if (left+1==getWidth()) {
//                return UIElementResultFactory.createResult(activeElement, 0);
//            }
            if (left == getWidth()/2) {
                return UIElementResultFactory.createResult(activeElement, 0);
            }

        }
        return null;
    }
}
