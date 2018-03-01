package org.oilmod.test.plugin1.magic2.ui;

import org.oilmod.test.plugin1.magic2.WandItemStackBase;
import org.oilmod.test.plugin1.magic2.Wandforcy;
import org.oilmod.api.userinterface.IInteractableUIElement;
import org.oilmod.api.userinterface.SingleStackInteractableUIElement;
import org.oilmod.api.userinterface.UIElementResult;
import org.oilmod.api.userinterface.UIElementResultFactory;
import org.oilmod.api.userinterface.UIFormedPanel;
import org.oilmod.api.userinterface.UIPanel;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-TestPlugin.
 */
public class WandUIPanel extends UIFormedPanel {
    private final WandItemStackBase wandItemStack;
//    private final IInteractableUIElement visElement;
//    private final IInteractableUIElement wandforcyElement;
    private final IInteractableUIElement activeElement;



    public WandUIPanel(int width, int height, WandItemStackBase wandItemStack) {
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
