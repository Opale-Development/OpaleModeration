package fr.opaleuhc.opalemoderation.cps;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CPSListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        CPSManager.instance.addClick(e.getPlayer().getUniqueId());
    }

}
