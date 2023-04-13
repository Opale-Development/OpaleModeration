package fr.opaleuhc.opalemoderation.ore;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.UUID;

public class OreTopper {

    private final UUID uuid;
    private final String name;
    private HashMap<Material, Long> ores;
    private long total;

    public OreTopper(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.ores = new HashMap<>();
        this.total = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public HashMap<Material, Long> getOres() {
        return ores;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void incrementTotal() {
        this.total++;
    }

    public String getPercent(Material material) {
        return String.format("%.3f", (double) ores.get(material) / total * 100) + "% §6x" + getOres().get(material);
    }
}
