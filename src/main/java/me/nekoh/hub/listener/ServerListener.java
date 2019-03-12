package me.nekoh.hub.listener;

import me.nekoh.hub.AstroHub;
import me.nekoh.hub.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ServerListener implements Listener {

    private AstroHub astroHub;

    public ServerListener(AstroHub astroHub) {
        this.astroHub = astroHub;
        Bukkit.getPluginManager().registerEvents(this, astroHub);
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        event.setMotd(StringUtil.translate("&e&lAstro&eHub"));
    }
}
