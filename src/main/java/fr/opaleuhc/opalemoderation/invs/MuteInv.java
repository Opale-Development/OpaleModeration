package fr.opaleuhc.opalemoderation.invs;

import fr.mrmicky.fastinv.FastInv;
import fr.opaleuhc.opalemoderation.ItemUtils;
import fr.opaleuhc.opalemoderation.enums.Mutes;
import org.bukkit.entity.Player;

public class MuteInv extends FastInv {

    private final int[] slots = {10, 11, 12, 13, 14, 15, 16, 17, 18};

    public MuteInv(String target) {
        super(27, "§cOM - Mutes");

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
            Mutes mute = Mutes.values()[total_Passed];
            setItem(i, mute.getItem(), e -> new ConfirmationInv(mute.getSanction(), target, mute.getBonusSanction()).open((Player) e.getWhoClicked()));
        }
    }
}
