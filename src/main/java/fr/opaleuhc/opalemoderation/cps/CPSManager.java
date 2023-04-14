package fr.opaleuhc.opalemoderation.cps;

import fr.opaleuhc.opalemoderation.OpaleModeration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CPSManager {

    public static final long CPS_ALERT_DELAY = 2000;
    public static final int CPS_LIMIT = 16;
    public static CPSManager instance;
    public HashMap<UUID, ArrayList<Long>> cps = new HashMap<>();
    public HashMap<UUID, Long> lastCPSAlert = new HashMap<>();
    public HashMap<UUID, Integer> cpsAlertInt = new HashMap<>();
    public CPSManager() {
        instance = this;

        Bukkit.getScheduler().runTaskTimer(OpaleModeration.instance, () -> {
            for (UUID uuid : cps.keySet()) {
                updateCPS(uuid);
            }
        }, 0, 20);
    }

    public void addClick(UUID uuid) {
        if (!cps.containsKey(uuid)) {
            cps.put(uuid, new ArrayList<>(List.of(System.currentTimeMillis())));
        }
        cps.get(uuid).add(System.currentTimeMillis());
        checkForToMuchCPS(uuid);
    }

    public void checkForToMuchCPS(UUID uuid) {
        if (getCPS(uuid) >= CPS_LIMIT) {
            if (lastCPSAlert.containsKey(uuid) && System.currentTimeMillis() - lastCPSAlert.get(uuid) < CPS_ALERT_DELAY) {
                if (cpsAlertInt.containsKey(uuid)) {
                    if (cpsAlertInt.get(uuid) < getCPS(uuid)) {
                        cpsAlertInt.put(uuid, getCPS(uuid));
                    }
                } else {
                    cpsAlertInt.put(uuid, getCPS(uuid));
                }
                return;
            }
            int cps = getCPS(uuid);
            if (cpsAlertInt.containsKey(uuid)) {
                if (cpsAlertInt.get(uuid) > cps) {
                    cps = cpsAlertInt.get(uuid);
                }
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("opaleuhc.cps")) {
                    p.sendMessage("CPS alert : " + Bukkit.getPlayer(uuid).getName() + " " + cps + " !");
                    lastCPSAlert.put(uuid, System.currentTimeMillis());
                }
            }
        }
    }

    public int getCPS(UUID uuid) {
        if (!cps.containsKey(uuid)) {
            return 0;
        }
        ArrayList<Long> clicks = cps.get(uuid);
        long time = System.currentTimeMillis();
        int cps = 0;
        for (Long click : clicks) {
            if (time - click < 1000) {
                cps++;
            }
        }
        return cps;
    }

    public void updateCPS(UUID uuid) {
        if (!cps.containsKey(uuid)) {
            return;
        }
        ArrayList<Long> clicks = cps.get(uuid);
        clicks.clone();
        long time = System.currentTimeMillis();
        clicks.removeIf(click -> time - click > 1000);
        cps.put(uuid, clicks);
    }


}
