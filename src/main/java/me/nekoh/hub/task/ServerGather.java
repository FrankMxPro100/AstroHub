package me.nekoh.hub.task;

import me.nekoh.hub.server.Server;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerGather extends BukkitRunnable {
    @Override
    public void run() {
        for (Server server : Server.getServers()) {
            server.gather();
        }
    }
}
