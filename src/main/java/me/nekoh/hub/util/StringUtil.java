package me.nekoh.hub.util;

import org.bukkit.ChatColor;

public class StringUtil {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String line() {
        return ChatColor.STRIKETHROUGH + "-------------------------------------------------";
    }

    public static String shortLine() {
        return ChatColor.STRIKETHROUGH + "---------------------";
    }

}
