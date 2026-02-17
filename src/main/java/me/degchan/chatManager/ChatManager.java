package me.degchan.chatManager;

import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Objects;

public final class ChatManager extends JavaPlugin {

    private LuckPerms luckPerms;
    private ConfigManager configManager;

    private void setupLuckPerms() {
        try {
            RegisteredServiceProvider<LuckPerms> provider =
                    Bukkit.getServicesManager().getRegistration(LuckPerms.class);

            if (provider != null) {
                luckPerms = provider.getProvider();
                getLogger().info("LuckPerms API successfully hooked");
            } else {
                getLogger().warning("LuckPerms API not found! Make sure the LuckPerms plugin is installed");
                luckPerms = null;
            }
        } catch (IllegalStateException e) {
            getLogger().warning("LuckPerms API not found! Error: " + e.getMessage());
            luckPerms = null;
        }
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);

        getServer().getScheduler().runTaskLater(this, () -> {
            setupLuckPerms();
            getServer().getPluginManager().registerEvents(new ChatListener(this), this);
            getLogger().info("ChatManager has been enabled");
        }, 20L);

        Objects.requireNonNull(getCommand("chat")).setExecutor(new ChatCommand(this));
        Objects.requireNonNull(getCommand("chat")).setTabCompleter(new ChatCommandCompleter());
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatManager has been disabled");
    }
}
