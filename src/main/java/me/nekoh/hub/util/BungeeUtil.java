package me.nekoh.hub.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.nekoh.hub.AstroHub;
import org.bukkit.entity.Player;

public class BungeeUtil {

    private static AstroHub astroHub;

    public BungeeUtil(AstroHub astroHub) {
        BungeeUtil.astroHub = astroHub;
    }

    //not made by me
    public static void sendToServer(Player player, String server) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendMessage(StringUtil.translate("&eSending to " + server + "..."));
            player.sendPluginMessage(astroHub, "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            player.sendMessage(StringUtil.translate("&cAn error occurred while sending to the server."));
        }
    }
}
