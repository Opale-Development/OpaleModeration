package fr.opaleuhc.opalemoderation.ore;

import fr.opaleuhc.opalemoderation.OpaleModeration;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OreManager {

    public static OreManager instance;
    public ArrayList<OreTopper> oreToppers = new ArrayList<>();
    public HashMap<Material, HashMap<Player, Long>> alerts = new HashMap<>();

    /***
     * when a player mine a diamond ore, he will be added to an HashMap
     * in the clock it will check if there is other diamond ore nearby his location and if theren't any left send a message to every
     * staff member online to tell them that the player has just mined x X diamond ore
     */

    public OreManager() {
        instance = this;

        for (OresControlled oresControlled : OresControlled.values()) {
            if (oresControlled.isAlert()) alerts.put(oresControlled.getMaterial(), new HashMap<>());
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(OpaleModeration.instance, this::checkForAlertsValidity, 0, 20);

        OpaleModeration.instance.getServer().getPluginManager().registerEvents(new OreListener(), OpaleModeration.instance);
    }

    public void blockBreakEvent(Player p, Block block) {
        checkForPlayer(p);
        OreTopper oreTopper = getOreTopper(p);
        if (oreTopper != null) {
            oreTopper.incrementTotal();
            if (!OresControlled.isOre(block.getType())) return;
            if (OresControlled.getOre(block.getType()) == null) return;
            if (Objects.requireNonNull(OresControlled.getOre(block.getType())).isAlert()) checkForAlert(p, block);
            HashMap<Material, Long> ores = oreTopper.getOres();
            if (ores.containsKey(block.getType())) ores.put(block.getType(), ores.get(block.getType()) + 1);
            else ores.put(block.getType(), 1L);
        }
    }

    public ArrayList<Block> getBlocksNearby(Location loc, Material mat, boolean withoutCenter) {
        ArrayList<Location> locations = new ArrayList<>();
        loc.clone();
        for (int x = loc.getBlockX() - 4; x <= loc.getBlockX() + 4; x++) {
            for (int z = loc.getBlockZ() - 4; z <= loc.getBlockZ() + 4; z++) {
                for (int y = loc.getBlockY() - 4; y <= loc.getBlockY() + 4; y++) {
                    locations.add(new Location(loc.getWorld(), x, y, z));
                }
            }
        }
        ArrayList<Block> blocks = new ArrayList<>();
        for (Location location : locations) {
            if (withoutCenter && location.equals(loc)) continue;
            if (location.getBlock() != null && location.getBlock().getType() == mat) blocks.add(location.getBlock());
        }
        return blocks;
    }

    public void checkForAlert(Player p, Block block) {
        if (alerts.get(block.getType()).containsKey(p)) {
            alerts.get(block.getType()).put(p, alerts.get(block.getType()).get(p) + 1);
        } else {
            alerts.get(block.getType()).put(p, 1L);
        }
    }

    public void checkForAlertsValidity() {
        for (Map.Entry<Material, HashMap<Player, Long>> entry : alerts.entrySet()) {
            for (Map.Entry<Player, Long> entry1 : entry.getValue().entrySet()) {
                ArrayList<Block> blocks = getBlocksNearby(entry1.getKey().getLocation(), entry.getKey(), false);
                if (blocks.size() == 0) {
                    BaseComponent base = new TextComponent("§7[§eOre§7] §e" + entry1.getKey().getName() + " §7a miné §e" + entry1.getValue() + " §7" + getMaterial(entry.getKey()));
                    base.addExtra(getTpComponent(entry1.getKey().getLocation()));
                    sendToStaffs(base);
                    alerts.get(entry.getKey()).remove(entry1.getKey());
                }
            }
        }
    }

    public BaseComponent getTpComponent(Location loc) {
        BaseComponent baseComponent = new TextComponent(" §7[§6§lTP§7]");
        baseComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ()));
        baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§7Cliquez pour vous téléporter en §e" + loc.getBlockX()
                + " " + loc.getBlockY() + " " + loc.getBlockZ())}));
        return baseComponent;
    }

    public BaseComponent getTpToComponent(Player p) {
        BaseComponent baseComponent = new TextComponent(" §7[§6§lTP§7]");
        baseComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + p.getName()));
        baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§7Cliquez pour vous téléporter à §e" + p.getName())}));
        return baseComponent;
    }

    public void sendToStaffs(BaseComponent message) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("opaleuhc.ore") && (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)) {
                player.spigot().sendMessage(message);
            }
        });
    }

    public String getMaterial(Material material) {
        OresControlled oresControlled = OresControlled.getOre(material);
        if (oresControlled != null) {
            return oresControlled.getName();
        }
        return material.name().toLowerCase();
    }

    public String getPlayerOres(Player p) {
        OreTopper oreTopper = getOreTopper(p);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("§7Total de blocs minés pour ").append(p.getName()).append(" : §e").append(oreTopper.getTotal()).append("\n§7");
        try {
            for (Material material : oreTopper.getOres().keySet()) {
                stringBuilder.append("§e").append(oreTopper.getPercent(material)).append(" §7de §e").append(getMaterial(material)).append("§7\n");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        } catch (Exception e) {
            return "§cErreur lors de la récupération des données.";
        }
    }

    public BaseComponent getTopOre(OresControlled ore) {
        BaseComponent baseComponent = new TextComponent("§7[§eTop §e" + ore.getName() + "§7]\n");
        ArrayList<OreTopper> oreToppers = new ArrayList<>();
        this.oreToppers.forEach(oreTopper -> {
            if (oreTopper.getOres().containsKey(ore.getMaterial())) {
                oreToppers.add(oreTopper);
            }
        });
        oreToppers.sort((o1, o2) -> {
            if (o1.getOres().get(ore.getMaterial()) > o2.getOres().get(ore.getMaterial())) {
                return -1;
            } else if (o1.getOres().get(ore.getMaterial()) < o2.getOres().get(ore.getMaterial())) {
                return 1;
            }
            return 0;
        });
        int i = 1;
        for (OreTopper oreTopper : oreToppers) {
            if (i > 15) break;
            baseComponent.addExtra(new TextComponent("§e" + i + " - §7" + oreTopper.getName() + " §e" + oreTopper.getPercent(ore.getMaterial())));
            Player player = Bukkit.getPlayer(oreTopper.getUuid());
            if (player != null) baseComponent.addExtra(getTpToComponent(player));
            if (i < oreToppers.size()) baseComponent.addExtra("\n");
            i++;
        }
        return baseComponent;
    }

    public void addOreTopper(OreTopper oreTopper) {
        try {
            oreToppers.add(oreTopper);
        } catch (Exception e) {
            Bukkit.getScheduler().runTask(OpaleModeration.instance, () -> addOreTopper(oreTopper));
        }
    }

    public OreTopper getOreTopper(Player p) {
        for (OreTopper oreTopper : oreToppers) {
            if (oreTopper.getUuid().equals(p.getUniqueId())) {
                return oreTopper;
            }
        }
        return null;
    }

    public void removeOreTopper(Player p) {
        try {
            oreToppers.remove(getOreTopper(p));
        } catch (Exception e) {
            Bukkit.getScheduler().runTask(OpaleModeration.instance, () -> removeOreTopper(p));
        }
    }

    public void checkForPlayer(Player p) {
        OreTopper oreTopper = getOreTopper(p);
        if (oreTopper == null) {
            addOreTopper(new OreTopper(p.getUniqueId(), p.getName()));
        }
    }

}
