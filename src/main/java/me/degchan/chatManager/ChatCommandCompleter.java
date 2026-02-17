package me.degchan.chatManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChatCommandCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>(List.of());

            if (sender.hasPermission("chat.reload")) {
                list.add("reload");
            }
            if (sender.hasPermission("chat.info")) {
                list.add("info");
            }

            return list;
        }
        return List.of();
    }
}