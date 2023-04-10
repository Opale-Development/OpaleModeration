package fr.opaleuhc.opalemoderation.enums;

import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Mutes {
    /*    Mutes
          Langage vulgaire / Publicité / Spam / Provocation > Mute 2h
          Insultes (violence, haine) > mute 1j
          Insultes 2ème degré (racisme, diffamation, menaces) > mute 7j + ban 3j
         */


    LANGAGE_VULGAIRE(new Sanction("Langage vulgaire", 1000 * 60 * 60 * 2, Sanction.Type.MUTE), new ItemBuilder(Material.BONE).name("§cLangage vulgaire").build(), null),
    PUBLICITE(new Sanction("Publicité", 1000 * 60 * 60 * 2, Sanction.Type.MUTE), new ItemBuilder(Material.COAL_BLOCK).name("§cPublicité").build(), null),
    SPAM(new Sanction("Spam", 1000 * 60 * 60 * 2, Sanction.Type.MUTE), new ItemBuilder(Material.PAPER).name("§cSpam").build(), null),
    PROVOCATION(new Sanction("Provocation", 1000 * 60 * 60 * 2, Sanction.Type.MUTE), new ItemBuilder(Material.BLAZE_ROD).name("§cProvocation").build(), null),
    INSULTES(new Sanction("Insultes", 1000 * 60 * 60 * 24, Sanction.Type.MUTE), new ItemBuilder(Material.REDSTONE_COMPARATOR).name("§cInsultes (violence, haine)").build(), null),
    INSULTES_2(new Sanction("Insultes 2ème degré", 1000 * 60 * 60 * 24 * 7, Sanction.Type.MUTE), new ItemBuilder(Material.REDSTONE_BLOCK).
            name("§cInsultes 2ème degré (racisme, diffamation, menaces)").build(), new Sanction("Insultes 2ème degré", 1000 * 60 * 60 * 24 * 3, Sanction.Type.BAN));

    private final Sanction sanction;
    private final ItemStack item;
    private final Sanction bonusSanction;

    Mutes(Sanction sanction, ItemStack item, Sanction bonusSanction) {
        this.sanction = sanction;
        this.item = item;
        this.bonusSanction = bonusSanction;
    }

    public Sanction getSanction() {
        return sanction;
    }

    public ItemStack getItem() {
        return item;
    }

    public Sanction getBonusSanction() {
        return bonusSanction;
    }
}
