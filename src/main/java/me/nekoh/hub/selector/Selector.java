package me.nekoh.hub.selector;

import me.nekoh.hub.AstroHub;
import me.nekoh.hub.server.Server;
import me.nekoh.hub.server.Status;
import me.nekoh.hub.util.BungeeUtil;
import me.nekoh.hub.util.ItemBuilder;
import me.nekoh.hub.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Selector implements Listener {

    private final ItemStack selectorItem = new ItemBuilder(Material.COMPASS).setName(StringUtil.translate("&6Selector")).setLore(StringUtil.translate("&7Click to join the server.")).toItemStack();

    private ItemStack hcfItem;
    private ItemStack practiceItem;
    private ItemStack kitmapItem;

    private Inventory selectorInventory;
    private Server hcfServer;
    private Server practiceServer;
    private Server kitmapServer;

    public Selector(AstroHub astroHub) {
        selectorInventory = Bukkit.createInventory(null, 9, StringUtil.translate("&6Select server:"));

        hcfServer = new Server(astroHub, "127.0.0.1", 25566, "HCF");
        practiceServer = new Server(astroHub, "127.0.0.1", 25567, "Practice");
        kitmapServer = new Server(astroHub, "127.0.0.1", 25568, "KitMap");

        Bukkit.getPluginManager().registerEvents(this, astroHub);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(astroHub, "BungeeCord");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().setItem(0, selectorItem);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (!event.getItem().equals(selectorItem)) {
            return;
        }

        //TODO: make it less shit
        hcfItem = new ItemBuilder(hcfServer.getStatus() == Status.OFFLINE ? Material.REDSTONE_BLOCK : Material.DIAMOND_SWORD)
                .setName(StringUtil.translate("&eHCF"))
                .setLore(
                        StringUtil.translate("&7" + StringUtil.shortLine()),
                        StringUtil.translate("&7Status: " + hcfServer.getStatus().getColor() + hcfServer.getStatus()),
                        StringUtil.translate("&e" + hcfServer.getOnlinePlayers() + "/" + hcfServer.getMaxPlayers()),
                        StringUtil.translate("&7Click to join the server."),
                        StringUtil.translate("&7" + StringUtil.shortLine())
                )
                .toItemStack();
        practiceItem = new ItemBuilder(practiceServer.getStatus() == Status.OFFLINE ? Material.REDSTONE_BLOCK : Material.GOLD_SWORD)
                .setName(StringUtil.translate("&ePractice"))
                .setLore(
                        StringUtil.translate("&7" + StringUtil.shortLine()),
                        StringUtil.translate("&7Status: " + practiceServer.getStatus().getColor() + practiceServer.getStatus()),
                        StringUtil.translate("&e" + practiceServer.getOnlinePlayers() + "/" + practiceServer.getMaxPlayers()),
                        StringUtil.translate("&7Click to join the server."),
                        StringUtil.translate("&7" + StringUtil.shortLine())
                )
                .toItemStack();
        kitmapItem = new ItemBuilder(kitmapServer.getStatus() == Status.OFFLINE ? Material.REDSTONE_BLOCK : Material.IRON_SWORD)
                .setName(StringUtil.translate("&eKitMap"))
                .setLore(
                        StringUtil.translate("&7" + StringUtil.shortLine()),
                        StringUtil.translate("&7Status: " + kitmapServer.getStatus().getColor() + kitmapServer.getStatus()),
                        StringUtil.translate("&e" + kitmapServer.getOnlinePlayers() + "/" + kitmapServer.getMaxPlayers()),
                        StringUtil.translate("&7Click to join the server."),
                        StringUtil.translate("&7" + StringUtil.shortLine())
                )
                .toItemStack();

        selectorInventory.setItem(3, hcfItem);
        selectorInventory.setItem(4, practiceItem);
        selectorInventory.setItem(5, kitmapItem);

        event.getPlayer().openInventory(selectorInventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(selectorInventory)) {
            event.setCancelled(true);
            return;
        }

        if (event.getCurrentItem() == null) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem().equals(hcfItem)) {
            BungeeUtil.sendToServer(player, "hcf");
        } else if (event.getCurrentItem().equals(practiceItem)) {
            BungeeUtil.sendToServer(player, "practice");
        } else if (event.getCurrentItem().equals(kitmapItem)) {
            BungeeUtil.sendToServer(player, "kitmap");
        }
        event.setCancelled(true);
        player.getOpenInventory().close();
    }

}
