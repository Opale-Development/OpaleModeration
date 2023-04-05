package fr.opaleuhc.opalemoderation;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {

    public static ItemStack getSkullForPlayer(String pN) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(pN);
        skull.setItemMeta(meta);
        return skull;
    }

}
