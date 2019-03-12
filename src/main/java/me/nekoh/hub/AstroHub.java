package me.nekoh.hub;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import me.nekoh.hub.listener.PlayerListener;
import me.nekoh.hub.listener.ServerListener;
import me.nekoh.hub.scoreboard.ScoreboardAdapter;
import me.nekoh.hub.selector.Selector;
import me.nekoh.hub.task.ServerGather;
import me.nekoh.hub.util.BungeeUtil;
import me.nekoh.hub.tab.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class AstroHub extends JavaPlugin {

    @Getter
    private static AstroHub astroHub;
    private long startTime;

    @Override
    public void onEnable() {
        startTime = System.currentTimeMillis();
        astroHub = this;
        new PlayerListener(this);
        new ServerListener(this);
        new Selector(this);
        new BungeeUtil(this);
        new ServerGather().runTaskTimerAsynchronously(this, 20L, 20L);
        Assemble assemble = new Assemble(this, new ScoreboardAdapter());
        assemble.setTicks(2);
        assemble.setAssembleStyle(AssembleStyle.MODERN);
        TabManager.setPlugin(this);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + getDescription().getName() + " successfully loaded in " + (System.currentTimeMillis() - startTime) + " ms.");
    }

    public List<Player> getOnlinePlayers() {
        ArrayList<Player> ret = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            ret.add(player);
        return ret;
    }
}
