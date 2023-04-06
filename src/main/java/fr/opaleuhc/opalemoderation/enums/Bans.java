package fr.opaleuhc.opalemoderation.enums;

import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Bans {

    /*
          Bans
          Non respect des règles > ban 3j
          Déconnexion en pleine game > ban 2j
          Dérangement en partie/Antijeu > (après kick et avertissement), ban 3j
          Cheat léger (Minimap, coordonnées en LG, DamageIndicator) > Ban 7j
          Cheat certain (X-Ray, Reach, etc) > Ban 2 mois
          Ban evasion > 7j si non cheat, 2 semaines si cheat léger, 4 mois si cheat certain
         */

    NON_RESPECT(new Sanction("Non respect des règles", 1000 * 60 * 60 * 24 * 3, Sanction.Type.BAN),
            new ItemBuilder(Material.BOOK).name("§cNon respect des règles").build(), null),
    DECONNEXION(new Sanction("Déconnexion en pleine game", 1000 * 60 * 60 * 24 * 2, Sanction.Type.BAN),
            new ItemBuilder(Material.REDSTONE).name("§cDéconnexion en pleine game").build(), null),
    DERANGEMENT(new Sanction("Dérangement en partie/Antijeu", 1000 * 60 * 60 * 24 * 3, Sanction.Type.BAN), new
            ItemBuilder(Material.BLAZE_ROD).name("§cDérangement en partie/Antijeu").build(), null),
    CHEAT_LEGER(new Sanction("Cheat léger (Minimap, coordonnées en LG, DamageIndicator)", 1000 * 60 * 60 * 24 * 7, Sanction.Type.BAN),
            new ItemBuilder(Material.DIAMOND_PICKAXE).name("§cCheat léger (Minimap, coordonnées en LG, DamageIndicator)").build(), null),
    CHEAT_CERTAIN(new Sanction("Cheat certain (X-Ray, Reach, etc..)", 1000L * 60 * 60 * 24 * 60, Sanction.Type.BAN),
            new ItemBuilder(Material.DIAMOND_SWORD).name("§cCheat certain (X-Ray, Reach, etc)").build(), null),
    BAN_EVASION_1(new Sanction("Ban evasion (non cheat)", 1000 * 60 * 60 * 24 * 7, Sanction.Type.BAN),
            new ItemBuilder(Material.BARRIER).name("§cBan evasion (non cheat)").build(), null),
    BAN_EVASION_2(new Sanction("Ban evasion (cheat léger)", 1000 * 60 * 60 * 24 * 14, Sanction.Type.BAN),
            new ItemBuilder(Material.BARRIER).name("§cBan evasion (cheat léger").build(), null),
    BAN_EVASION_3(new Sanction("Ban evasion (cheat certain)", 1000L * 60 * 60 * 24 * 120, Sanction.Type.BAN),
            new ItemBuilder(Material.BARRIER).name("§cBan evasion (cheat certain)").build(), null);


    private final Sanction sanction;
    private final ItemStack item;
    private final Sanction bonusSanction;

    Bans(Sanction sanction, ItemStack item, Sanction bonusSanction) {
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
