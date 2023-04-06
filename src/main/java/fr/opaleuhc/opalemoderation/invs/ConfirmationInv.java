package fr.opaleuhc.opalemoderation.invs;

import fr.mrmicky.fastinv.FastInv;
import fr.opaleuhc.opalemoderation.ItemUtils;
import fr.opaleuhc.opalemoderation.enums.Sanction;
import fr.opaleuhc.opalemoderation.utils.DateUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmationInv extends FastInv {

    public ConfirmationInv(Sanction sanction, String target, Sanction bonusSanction) {
        super(27, "§cOM - Confirmation");

        for (int i = 0; i < 27; i++) {
            if (ItemUtils.white_slots.contains(i)) {
                setItem(i, ItemUtils.getWhitePanel());
                continue;
            }
            setItem(i, ItemUtils.getRedPanel());
        }

        setItem(4, ItemUtils.getSkullForPlayer(target));

        /*
          Confirmation
          Vous êtes sur le point de [type] [target] pour [reason] pendant [time]
          Confirmer
          Annuler
         */

        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("§cVous êtes sur le point de " + sanction.getType().getName() + " " + target + " pour " + sanction.getReason() +
                " pendant " + DateUtils.getDurationIn(sanction.getDuration()));
        info.setItemMeta(infoMeta);
        setItem(13, info);

        if (bonusSanction != null) {
            ItemStack bonus = new ItemStack(Material.PAPER);
            ItemMeta bonusMeta = bonus.getItemMeta();
            bonusMeta.setDisplayName("§cVous êtes sur le point de " + bonusSanction.getType().getName() + " " + target + " pour " + bonusSanction.getReason() +
                    " pendant " + DateUtils.getDurationIn(bonusSanction.getDuration()));
        }

        ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName("§a§lConfirmer");
        confirm.setItemMeta(confirmMeta);
        setItem(11, confirm, e -> {
            //sanction
            //apply bonus sanction
        });

        ItemStack cancel = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName("§c§lAnnuler");
        cancel.setItemMeta(cancelMeta);
        setItem(15, cancel, e -> new MainInv(target).open((Player) e.getWhoClicked()));
    }
}
