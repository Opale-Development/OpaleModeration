package fr.opaleuhc.opalemoderation.invs;

import fr.mrmicky.fastinv.FastInv;
import fr.opaleuhc.opalemoderation.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainInv extends FastInv {

    public MainInv(String target) {
        super(27, "§cOpaleModeration");

        ItemStack skull = ItemUtils.getSkullForPlayer(target);
        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§c" + target);
        skull.setItemMeta(meta);
        setItem(4, skull);

        ItemStack ban = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta banMeta = ban.getItemMeta();
        banMeta.setDisplayName("§cBans");
        ban.setItemMeta(banMeta);
        setItem(12, ban, e -> {
            //new BanInv(target).open(e.getWhoClicked());
        });

        ItemStack mute = new ItemStack(Material.BOOK);
        ItemMeta muteMeta = mute.getItemMeta();
        muteMeta.setDisplayName("§cMutes");
        mute.setItemMeta(muteMeta);
        setItem(15, mute, e -> {
            //new MuteInv(target).open(e.getWhoClicked());
        });
    }
}
