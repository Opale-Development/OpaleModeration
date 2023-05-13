package fr.opaleuhc.opalemoderation.ore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OreListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreakEvent(BlockBreakEvent e) {
        OreManager.instance.blockBreakEvent(e.getPlayer(), e.getBlock());
    }
}
