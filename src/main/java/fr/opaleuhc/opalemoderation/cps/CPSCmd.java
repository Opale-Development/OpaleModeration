package fr.opaleuhc.opalemoderation.cps;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CPSCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /cps <joueur>");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cCe joueur n'est pas connecté !");
            return false;
        }
        sender.sendMessage("§aCPS de " + target.getName() + " : " + CPSManager.instance.getCPS(target.getUniqueId()));
        return false;
    }
}
