package fr.opaleuhc.opalemoderation;

import fr.mrmicky.fastinv.FastInvManager;
import fr.opaleuhc.opalemoderation.cmd.OpaleModerationCmd;
import fr.opaleuhc.opalemoderation.cpm.CPMListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpaleModeration extends JavaPlugin {

    public static OpaleModeration instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("OpaleModeration is starting...");

        getLogger().info("Registering FASTINV...");
        FastInvManager.register(this);

        getServer().getMessenger().registerIncomingPluginChannel(instance, CPMListener.channel, new CPMListener());
        getServer().getMessenger().registerOutgoingPluginChannel(instance, CPMListener.channel);

        getCommand("om").setExecutor(new OpaleModerationCmd());

        getLogger().info("OpaleModeration is started !");
    }

    @Override
    public void onDisable() {
        getLogger().info("OpaleModeration is stopping...");

        getLogger().info("OpaleModeration is stopped !");
    }
}
