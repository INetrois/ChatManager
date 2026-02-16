package me.degchan.chatManager;

import org.bukkit.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    public static String hexColor(String message) {
        Pattern hexPattern = Pattern.compile("&x((&[0-9a-fA-F]){6})");
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            String hex = matcher.group(1).replace("&", "");
            String replacement = net.md_5.bungee.api.ChatColor.of("#" + hex).toString();
            message = message.replace(matcher.group(), replacement);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}