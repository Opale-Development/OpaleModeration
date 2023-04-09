package fr.opaleuhc.opalemoderation.invs;

import fr.mrmicky.fastinv.FastInv;
import fr.opaleuhc.opalemoderation.ItemUtils;
import fr.opaleuhc.opalemoderation.enums.Bans;
import fr.opaleuhc.opalemoderation.enums.Mutes;
import org.bukkit.entity.Player;

public class BanInv extends FastInv {

    private final int[] slots = {10, 11, 12, 13, 14, 15, 16, 17, 18};

    public BanInv(String target) {
        super(27, "§cOM - Bans");

        for (int i = 0; i < 27; i++) {
            if (ItemUtils.white_slots.contains(i)) {
                setItem(i, ItemUtils.getWhitePanel());
                continue;
            }
            setItem(i, ItemUtils.getRedPanel());
        }

        setItem(4, ItemUtils.getSkullForPlayer(target));

        int total_Passed = 0;
        for (int i : slots) {
            if (total_Passed == Mutes.values().length) break;
            Bans ban = Bans.values()[total_Passed];
            setItem(i, ban.getItem(), e -> new ConfirmationInv(ban.getSanction(), target, ban.getBonusSanction()).open((Player) e.getWhoClicked()));
            total_Passed++;
        }

        setItem(26, ItemUtils.getBack(), e -> new MainInv(target).open((Player) e.getWhoClicked()));
    }
}
