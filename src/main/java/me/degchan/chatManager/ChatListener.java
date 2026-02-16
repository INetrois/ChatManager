package me.degchan.chatManager;

import net.luckperms.api.query.QueryOptions;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class ChatListener implements Listener {

    private final ChatManager plugin;
    private final ConfigManager configManager;
    private Chat vaultChat;

    public ChatListener(ChatManager plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        setupVault();
    }

    private void setupVault() {
        RegisteredServiceProvider<Chat> rsp =
                plugin.getServer().getServicesManager().getRegistration(Chat.class);

        if (rsp != null) {
            vaultChat = rsp.getProvider();
        }
    }

    private String getPlayerPrefix(Player player) {
        LuckPerms luckPerms = plugin.getLuckPerms();

        if (luckPerms != null) {
            try {
                User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                if (user != null) {
                    CachedMetaData metaData = user.getCachedData()
                            .getMetaData(QueryOptions.defaultContextualOptions());

                    String prefix = metaData.getPrefix();
                    if (prefix != null && !prefix.isEmpty()) {
                        return prefix;
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to get prefix from LuckPerms: " + e.getMessage());
            }
        }

        if (vaultChat != null) {
            String prefix = vaultChat.getPlayerPrefix(player);
            return (prefix != null && !prefix.isEmpty()) ? prefix : "";
        }

        return "";
    }

    private String getSuffix(Player player) {
        LuckPerms luckPerms = plugin.getLuckPerms();

        if (luckPerms != null) {
            try {
                User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                if (user != null) {
                    CachedMetaData metaData = user.getCachedData()
                            .getMetaData(QueryOptions.defaultContextualOptions());

                    String suffix = metaData.getSuffix();
                    return (suffix != null && !suffix.isEmpty()) ? suffix : "";
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to get suffix from LuckPerms: " + e.getMessage());
            }
        }

        if (vaultChat != null) {
            String suffix = vaultChat.getPlayerSuffix(player);
            return (suffix != null && !suffix.isEmpty()) ? suffix : "";
        }

        return "";
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        plugin.getLogger().info("[CHAT] " + player.getName() + ": " + message);

        String rawPrefix = getPlayerPrefix(player);
        String rawSuffix = getSuffix(player);

        String prefix = (!rawPrefix.isEmpty()) ? rawPrefix + " " : "";
        String suffix = (!rawSuffix.isEmpty()) ? " " + rawSuffix : "";

        // =======================
        // GLOBAL CHAT
        // =======================
        if (message.startsWith(configManager.getGlobalPrefix())) {
            event.setCancelled(true);

            String format = configManager.getGlobalFormat()
                    .replace("{symbol}", configManager.getGlobalColor() + configManager.getGlobalSymbol())
                    .replace("{prefix}", prefix)
                    .replace("{player}", player.getName())
                    .replace("{suffix}", suffix)
                    .replace("{message}", message.substring(1).trim());

            String formatted = ChatUtil.hexColor(format);

            for (Player target : Bukkit.getOnlinePlayers()) {
                target.sendMessage(formatted);
            }
            return;
        }

        // =======================
        // LOCAL CHAT
        // =======================
        event.setCancelled(true);

        String format = configManager.getLocalFormat()
                .replace("{symbol}", configManager.getLocalColor() + configManager.getLocalSymbol())
                .replace("{prefix}", prefix)
                .replace("{player}", player.getName())
                .replace("{suffix}", suffix)
                .replace("{message}", message);

        String formatted = ChatUtil.hexColor(format);

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.getWorld().equals(player.getWorld()) &&
                    target.getLocation().distance(player.getLocation()) <= configManager.getLocalDistance()) {
                target.sendMessage(formatted);
            }
        }
    }
}
