package me.nekoh.hub.scoreboard;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.nekoh.hub.AstroHub;
import me.nekoh.hub.server.Server;
import me.nekoh.hub.server.Status;
import me.nekoh.hub.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreboardAdapter implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        return StringUtil.translate("&6&l" + "AstroHub");
    }

    @Override
    public List<String> getLines(Player player) {
        final List<String> toReturn = new ArrayList<>();

        int totalOnlinePlayers = AstroHub.getAstroHub().getOnlinePlayers().size();
        int totalMaxPlayers = Bukkit.getMaxPlayers();

        if (Bukkit.getPluginManager().getPlugin("PermissionsEx") != null) {
            PermissionUser user = PermissionsEx.getUser(player);
            toReturn.add(StringUtil.translate("&eRank&7: " + user.getGroups()[0].getName()));
        }

        toReturn.add(StringUtil.translate("&eTotal&7: &e" + totalOnlinePlayers + "&7/&e" + totalMaxPlayers));
        toReturn.add("");

        toReturn.add(StringUtil.translate("&eHub&7: " + "&e" + AstroHub.getAstroHub().getOnlinePlayers().size() + "&7/&e" + Bukkit.getMaxPlayers()));
        for (Server server : Server.getServers()) {
            totalOnlinePlayers += server.getOnlinePlayers();
            totalMaxPlayers += server.getMaxPlayers();
            toReturn.add(StringUtil.translate("&e" + server.getName() + "&7: " + (server.getStatus() == Status.OFFLINE ? " &cOFFLINE" : "&e" + server.getOnlinePlayers() + "&7/&e" + server.getMaxPlayers())));
        }
        StringBuilder line = new StringBuilder(ChatColor.STRIKETHROUGH.toString());

        int longestLineLength = Collections.max(toReturn, Comparator.comparing(String::length)).length();
        for (int x = 0; x < longestLineLength; x++) {
            line.append("-");
        }

        toReturn.add(0,  line.toString());
        toReturn.add(toReturn.size(), line.toString());
        return toReturn;
    }
}
