package me.degchan.chatManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ConfigManager {

    private final ChatManager plugin;
    private FileConfiguration config;

    public ConfigManager(ChatManager plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    // ========================
    // CHANNELS
    // ========================

    public int getLocalDistance() {
        return config.getInt("chat.channels.local.distance", 250);
    }

    public String getLocalSymbol() {
        return color(config.getString("chat.channels.local.symbol", "ʟ"));
    }

    public String getLocalColor() {
        return color(config.getString("chat.channels.local.color", "&a"));
    }

    public String getGlobalPrefix() {
        return color(config.getString("chat.channels.global.prefix", "!"));
    }

    public String getGlobalSymbol() {
        return color(config.getString("chat.channels.global.symbol", "ɢ"));
    }

    public String getGlobalColor() {
        return color(config.getString("chat.channels.global.color", "&6"));
    }

    // ========================
    // FORMATS
    // ========================

    public String getLocalFormat() {
        return config.getString(
                "chat.format.local",
                "&7{symbol} &7| &f{prefix}&x&3&1&b&9&f&7{player}&f{suffix} &7» &x&C&E&F&F&B&8{message}"
        );
    }

    public String getGlobalFormat() {
        return config.getString(
                "chat.format.global",
                "&7{symbol} &7| &f{prefix}&x&3&1&b&9&f&7{player}&f{suffix} &7» &f{message}"
        );
    }

    // ========================
    // FORMAT ENGINE
    // ========================

    public String formatLocal(Player player, String message, String prefix, String suffix) {
        String format = getLocalFormat();

        return apply(format, player, message, prefix, suffix, getLocalSymbol());
    }

    public String formatGlobal(Player player, String message, String prefix, String suffix) {
        String format = getGlobalFormat();

        return apply(format, player, message, prefix, suffix, getGlobalSymbol());
    }

    private String apply(String format, Player player, String message, String prefix, String suffix, String symbol) {
        return color(format
                .replace("{player}", player.getName())
                .replace("{message}", message)
                .replace("{prefix}", prefix == null ? "" : prefix)
                .replace("{suffix}", suffix == null ? "" : suffix)
                .replace("{symbol}", symbol)
        );
    }

    // ========================
    // COLOR UTILS
    // ========================

    private String color(String text) {
        if (text == null) return "";
        return text.replace("&", "§");
    }
}
