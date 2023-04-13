package fr.opaleuhc.opalemoderation.ore;

import org.bukkit.Material;

public enum OresControlled {

    CHARBON(Material.COAL_ORE, "Charbon", false),
    FER(Material.IRON_ORE, "Fer", false),
    OR(Material.GOLD_ORE, "Or", false),
    DIAMANT(Material.DIAMOND_ORE, "Diamant", true),
    EMERAUDE(Material.EMERALD_ORE, "Emeraude", false),
    LAPIS(Material.LAPIS_ORE, "Lapis", false),
    REDSTONE(Material.REDSTONE_ORE, "Redstone", false),
    QUARTZ(Material.QUARTZ_ORE, "Quartz", false);

    private final Material material;
    private final String name;
    private final boolean alert;

    OresControlled(Material material, String name, boolean alert) {
        this.material = material;
        this.name = name;
        this.alert = alert;
    }

    public static OresControlled getOre(Material material) {
        for (OresControlled oresControlled : OresControlled.values()) {
            if (oresControlled.getMaterial().equals(material)) {
                return oresControlled;
            }
        }
        return null;
    }

    public static boolean isOre(Material material) {
        return getOre(material) != null;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public boolean isAlert() {
        return alert;
    }

}
