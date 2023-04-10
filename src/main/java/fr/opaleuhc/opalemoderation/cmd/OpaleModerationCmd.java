package fr.opaleuhc.opalemoderation.cmd;

import fr.opaleuhc.opalemoderation.invs.MainInv;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OpaleModerationCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cVous devez être un joueur pour exécuter cette commande !");
            return true;
        }
        if (args.length == 0) {
            p.sendMessage("§cUsage: /om <joueur>");
            return true;
        }
        if (args.length == 1) {
            new MainInv(args[0]).open(p);
            return true;
        }
        p.sendMessage("§cUsage: /om <joueur>");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("opaleuhc.om")) {
            return null;
        }
        if (!(sender instanceof Player p)) {
            return null;
        }
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            for (Player target : sender.getServer().getOnlinePlayers()) {
                if (target.getName().equalsIgnoreCase(p.getName())) continue;
                list.add(target.getName());
            }
            return list;
        }
        return null;
    }

}
