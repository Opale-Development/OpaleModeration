package fr.opaleuhc.opalemoderation.ore;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OreCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("joueur") || args[0].equalsIgnoreCase("j")) {
                try {
                    Player target = Bukkit.getOfflinePlayer(args[1]).getPlayer();
                    if (target == null) {
                        sender.sendMessage("§cCe joueur n'est pas connecté !");
                        return false;
                    }
                    sender.sendMessage(OreManager.instance.getPlayerOres(target));
                } catch (Exception e) {
                    sender.sendMessage("§cCe joueur n'existe pas !");
                    return false;
                }
                return false;
            } else if (args[0].equalsIgnoreCase("top") || args[0].equalsIgnoreCase("t")) {
                try {
                    OresControlled oreType = OresControlled.valueOf(args[1].toUpperCase());
                    sender.sendMessage(OreManager.instance.getTopOre(oreType));
                } catch (Exception e) {
                    sender.sendMessage("§cCette ore n'existe pas !");
                    return false;
                }
                return false;
            }
            return false;
        }
        sender.sendMessage("§cUtilisation : /ore <t> <ore> ou /ore <p> <joueur>");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("joueur") || args[0].equalsIgnoreCase("j")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    list.add(player.getName());
                }
                return list;
            } else if (args[0].equalsIgnoreCase("top") || args[0].equalsIgnoreCase("t")) {
                for (OresControlled oresControlled : OresControlled.values()) {
                    list.add(oresControlled.getName());
                }
                return list;
            }
            return null;
        }
        if (args.length == 1) {
            list.add("joueur");
            list.add("j");
            list.add("top");
            list.add("t");
            return list;
        }
        return null;
    }
}
