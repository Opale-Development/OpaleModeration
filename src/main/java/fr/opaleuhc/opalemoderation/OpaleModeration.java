package fr.opaleuhc.opalemoderation;

import fr.mrmicky.fastinv.FastInvManager;
import fr.opaleuhc.opalemoderation.cmd.OpaleModerationCmd;
import fr.opaleuhc.opalemoderation.cpm.CPMListener;
import fr.opaleuhc.opalemoderation.cps.CPSCmd;
import fr.opaleuhc.opalemoderation.cps.CPSListener;
import fr.opaleuhc.opalemoderation.cps.CPSManager;
import fr.opaleuhc.opalemoderation.ore.OreCmd;
import fr.opaleuhc.opalemoderation.ore.OreManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpaleModeration extends JavaPlugin {

    public static OpaleModeration instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("OpaleModeration is starting...");

        getLogger().info("Registering FASTINV...");
        FastInvManager.register(this);

        getLogger().info("Registering managers...");
        new OreManager();
        new CPSManager();

        getLogger().info("Registering cpm...");
        getServer().getMessenger().registerIncomingPluginChannel(instance, CPMListener.channel, new CPMListener());
        getServer().getMessenger().registerOutgoingPluginChannel(instance, CPMListener.channel);

        getLogger().info("Registering commands...");
        getCommand("om").setExecutor(new OpaleModerationCmd());
        getCommand("ore").setExecutor(new OreCmd());
        getCommand("cps").setExecutor(new CPSCmd());

        getLogger().info("Registering listeners...");
        getServer().getPluginManager().registerEvents(new CPSListener(), this);

        getLogger().info("OpaleModeration is started !");
    }

    @Override
    public void onDisable() {
        getLogger().info("OpaleModeration is stopping...");

        getLogger().info("OpaleModeration is stopped !");
    }
}
