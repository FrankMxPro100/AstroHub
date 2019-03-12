package me.nekoh.hub.listener;

import me.nekoh.hub.AstroHub;
import me.nekoh.hub.util.ItemBuilder;
import me.nekoh.hub.util.StringUtil;
import me.nekoh.hub.tab.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private static final ItemStack hideItem = new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName(StringUtil.translate("&7Hide players")).setLore(StringUtil.translate("&7Click to hide players")).toItemStack();
    private static final ItemStack showItem = new ItemBuilder(Material.INK_SACK).setDurability((byte) 10).setName(StringUtil.translate("&aShow Players")).setLore(StringUtil.translate("&7Click to show players")).toItemStack();

    public PlayerListener(AstroHub astroHub) {
        Bukkit.getPluginManager().registerEvents(this, astroHub);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();
        String value = "&7" + to.getBlockX() + ", " + to.getBlockY() + ", " + to.getBlockZ();
        TabManager tab = TabManager.getByPlayer(player);
        tab.setSlot(53, value);
    }

    @EventHandler
    public void removeTab(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TabManager.deleteTabList(player);
    }

    @EventHandler
    public void setTab(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        TabManager toReturn = TabManager.createTabList(player);
        final String spacer = "&7&m--------------";
        toReturn.setSlot(1, spacer);
        toReturn.setSlot(2, spacer);
        toReturn.setSlot(3, spacer);

        toReturn.setSlot(4, "&6www.astro.hub");
        toReturn.setSlot(5, "&6www.astro.hub");
        toReturn.setSlot(6, "&6www.astro.hub");

        toReturn.setSlot(7, spacer);
        toReturn.setSlot(8, spacer);
        toReturn.setSlot(9, spacer);

        toReturn.setSlot(58, spacer);
        toReturn.setSlot(59, spacer);
        toReturn.setSlot(60, spacer);

        if (toReturn.isClient18()) {
            toReturn.setSlot(68, "&c&lWarning!");
            toReturn.setSlot(69, "&7We recommend you");
            toReturn.setSlot(70, "&7switch to &c1.7.10");
            toReturn.setSlot(71, "&7for a even greater");
            toReturn.setSlot(72, "&7gaming experience.");

            String header = "&6&lAstroHub";
            String footer = "&6www.astro.hub";

            toReturn.setPlayerListHeaderFooter(header, footer);
        }
    }

    @SuppressWarnings("deprecated")
    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (!event.getItem().equals(hideItem) && !event.getItem().equals(showItem)) {
            return;
        }


        if (event.getItem().equals(hideItem)) {
            AstroHub.getAstroHub().getOnlinePlayers().forEach(player -> event.getPlayer().hidePlayer(player));
            event.getPlayer().getInventory().setItem(8, showItem);
        } else {
            AstroHub.getAstroHub().getOnlinePlayers().forEach(player -> event.getPlayer().showPlayer(player));
            event.getPlayer().getInventory().setItem(8, hideItem);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        event.getPlayer().sendMessage(StringUtil.translate("&cHey man, it's nice to see u"));
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.hidePlayer(event.getPlayer());
            event.getPlayer().hidePlayer(online);
        }
        event.getPlayer().getInventory().setItem(8, showItem);
        event.getPlayer().setWalkSpeed(0.8F);
        event.getPlayer().setHealth(20);
        event.getPlayer().setFoodLevel(20);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!event.getPlayer().hasPermission("hub.chat")) {
            event.getPlayer().sendMessage(StringUtil.translate("&cChat is disabled in the hub."));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("hub.destroy")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("hub.destroy")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract2(PlayerInteractEvent event) {
        event.setCancelled(true);
    }

}
