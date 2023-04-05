package fr.opaleuhc.opalemoderation.cpm;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.opaleuhc.opalemoderation.OpaleModeration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CPMListener implements org.bukkit.plugin.messaging.PluginMessageListener {

    public static final String channel = "opaleuhc:sanctions";
    public static CPMListener instance;

    public CPMListener() {
        instance = this;

        //sendPluginMessage(player, "mute", "10d", "test");
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equalsIgnoreCase(CPMListener.channel)) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();
        System.out.println(subChannel + " " + player.getName());
        /*if (subChannel.equalsIgnoreCase("mute")) {
            String data1 = in.readUTF();
            System.out.println(data1);
        }*/
    }

    public void sendPluginMessage(Player player, String subChannel, String... data) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        for (String s : data) {
            out.writeUTF(s);
        }

        player.sendPluginMessage(OpaleModeration.instance, channel, out.toByteArray());
    }

}
