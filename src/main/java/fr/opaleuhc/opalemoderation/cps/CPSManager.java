package fr.opaleuhc.opalemoderation.cps;

import fr.opaleuhc.opalemoderation.OpaleModeration;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CPSManager {

    public static final long CPS_ALERT_DELAY = 2000;
    public static final int CPS_LIMIT = 16;
    public static CPSManager instance;
    public HashMap<UUID, ArrayList<Long>> cps = new HashMap<>();

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
