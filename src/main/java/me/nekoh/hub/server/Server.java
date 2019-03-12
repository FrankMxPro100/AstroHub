package me.nekoh.hub.server;

import lombok.Getter;
import me.nekoh.hub.AstroHub;
import org.bukkit.Bukkit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Server {

    @Getter
    private static List<Server> servers = new ArrayList<>();

    private AstroHub astroHub;
    private String address;
    private int port;
    private String name;
    private int onlinePlayers = 0;
    private int maxPlayers = 0;
    private Status status = Status.OFFLINE;

    public Server(AstroHub astroHub, String address, int port, String name) {
        this.astroHub = astroHub;
        this.address = address;
        this.port = port;
        this.name = name;
        gather();
        servers.add(this);
    }

    //not made by me
    public void gather() {
        Bukkit.getScheduler().runTaskAsynchronously(AstroHub.getAstroHub(), () -> {
            try (Socket socket = new Socket(address, port);
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                 DataInputStream input = new DataInputStream(socket.getInputStream())) {
                output.write(0xFE);
                int next = -1;
                String result = "";
                while ((next = input.read()) != -1) {
                    if (next != 0 && next != 16 && next != 255 && next != 23 && next != 24) {
                        result += (char) next;
                    }
                }
                String[] data = result.split("§");
                status = data[0].toLowerCase().contains("whitelisted")
                        ? Status.WHITELISTED
                        : Status.ONLINE;
                onlinePlayers = Integer.valueOf(data[1]);
                maxPlayers = Integer.valueOf(data[2]);
            } catch (Exception exception) {
                status = Status.OFFLINE;
                onlinePlayers = 0;
                maxPlayers = 0;
            }
        });
    }
}
