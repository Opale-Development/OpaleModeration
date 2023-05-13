package fr.opaleuhc.opalemoderation.utils;

import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {

    public static final List<Integer> white_slots = Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26);
    private static final ItemStack whitePanel = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
    private static final ItemStack redPanel = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
    private static final ItemStack back = new ItemBuilder(Material.ARROW).name("§cRetour").build();

    public static ItemStack getSkullForPlayer(String pN) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setDisplayName("§c" + pN);
        meta.setOwner(pN);
        skull.setItemMeta(meta);
        return skull;
    }

    public static ItemStack getWhitePanel() {
        return whitePanel;
    }

    public static ItemStack getRedPanel() {
        return redPanel;
    }

    public static ItemStack getBack() {
        return back;
    }

}
