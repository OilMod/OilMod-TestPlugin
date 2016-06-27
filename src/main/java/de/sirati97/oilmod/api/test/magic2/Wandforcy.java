package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.userinterface.UIPanel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public interface Wandforcy {
    boolean onWandLeftClick(Wand wand, Player player, Action action);

    void onWandUse(Wand wand, Player player, Action action);

    void onWandUseOnBlock(Wand wand, Player player, Action action, Block blockClicked, BlockFace blockFace);

    boolean onWandLeftClickOnBlock(Wand wand, Player player, Action action, Block blockClicked, BlockFace blockFace);

    UIPanel getUIPanel();
}
