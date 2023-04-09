package fr.opaleuhc.opalemoderation.invs;

import fr.mrmicky.fastinv.FastInv;
import fr.opaleuhc.opalemoderation.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainInv extends FastInv {

    public MainInv(String target) {
        super(27, "§cOpaleModeration");

        for (int i = 0; i < 27; i++) {
            if (ItemUtils.white_slots.contains(i)) {
                setItem(i, ItemUtils.getWhitePanel());
                continue;
            }
            setItem(i, ItemUtils.getRedPanel());
        }

        setItem(4, ItemUtils.getSkullForPlayer(target));

        ItemStack ban = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta banMeta = ban.getItemMeta();
        banMeta.setDisplayName("§cBans");
        ban.setItemMeta(banMeta);
        setItem(12, ban, e -> new BanInv(target).open((Player) e.getWhoClicked()));

        ItemStack mute = new ItemStack(Material.BOOK);
        ItemMeta muteMeta = mute.getItemMeta();
        muteMeta.setDisplayName("§cMutes");
        mute.setItemMeta(muteMeta);
        setItem(14, mute, e -> new MuteInv(target).open((Player) e.getWhoClicked()));
    }

    /*@Override
    public void onOpen(InventoryOpenEvent e) {

    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }

    @Override
    public void onClick(InventoryClickEvent e) {
        // do something
    }*/
}
