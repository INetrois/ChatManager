package me.degchan.chatManager;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatCommand implements CommandExecutor {

    private final ChatManager plugin;

    public ChatCommand(ChatManager plugin) {
        this.plugin = plugin;
    }

    private void argumentNotFound(@NotNull CommandSender sender, @NotNull String arg) {
        sender.sendMessage("§8[§e§l⚡§8] §7Unknown command argument: §f" + arg + "§7!");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!sender.hasPermission("chat.reload") && !sender.hasPermission("chat.info")) {
            sender.sendMessage("§8[§e§l⚡§8] §7You are not allowed to use this command!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§8[§e§l⚡§8] §7Not enough arguments!");
            return true;
        }

        String arg = args[0];

        ConfigManager configManager = plugin.getConfigManager();
        LuckPerms luckPerms = plugin.getLuckPerms();

        // =======================
        // RELOAD
        // =======================
        if (arg.equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("chat.reload")) {
                argumentNotFound(sender, arg);
                return true;
            }

            configManager.loadConfig();
            sender.sendMessage("§8[§e§l⚡§8] §7Chat configuration has been reloaded!");
        }

        // =======================
        // INFO
        // =======================
        else if (arg.equalsIgnoreCase("info")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§8[§e§l⚡§8] §7This command is only available for players!");
                return true;
            }

            if (!sender.hasPermission("chat.info")) {
                argumentNotFound(sender, arg);
                return true;
            }

            Player player = (Player) sender;

            String prefix = "None";
            String suffix = "None";

            if (luckPerms != null) {
                try {
                    User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                    if (user != null) {
                        CachedMetaData metaData =
                                user.getCachedData().getMetaData(QueryOptions.defaultContextualOptions());

                        prefix = metaData.getPrefix() != null ? metaData.getPrefix() : "None";
                        suffix = metaData.getSuffix() != null ? metaData.getSuffix() : "None";
                    }
                } catch (Exception e) {
                    sender.sendMessage("§8[§e§l⚡§8] §7Error while retrieving chat information!");
                }
            }

            String coloredPrefix = ChatUtil.hexColor(prefix);
            String coloredSuffix = ChatUtil.hexColor(suffix);

            sender.sendMessage("§8[§e§l⚡§8] §7Chat information:");
            sender.sendMessage("§8     ▪ §7Prefix: §f" + coloredPrefix);
            sender.sendMessage("§8     ▪ §7Suffix: §f" + coloredSuffix);
            sender.sendMessage("§8     ▪ §7Global chat: §f" + configManager.getGlobalPrefix() + "§8<§7message§8>");
            sender.sendMessage("§8     ▪ §7Local chat: §fJust type your message normally");
        }

        // =======================
        // UNKNOWN ARG
        // =======================
        else {
            argumentNotFound(sender, arg);
        }

        return true;
    }
}
