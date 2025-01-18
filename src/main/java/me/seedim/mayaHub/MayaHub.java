package me.seedim.mayaHub;

import Utils.Metrics;
import Utils.UpdateChecker;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.seedim.mayaHub.Commands.FlyCommand;
import me.seedim.mayaHub.Commands.SetSpawnCommand;
import me.seedim.mayaHub.Commands.SpawnCommand;
import me.seedim.mayaHub.Commands.ToggleEditCommand;
import me.seedim.mayaHub.Listeners.ConnectionListener;
import me.seedim.mayaHub.Listeners.InvulnerabilityListener;
import me.seedim.mayaHub.Listeners.MessageListener;
import me.seedim.mayaHub.Listeners.WorldProtectionListener;
import me.seedim.mayaHub.Tasks.BroadcastTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MayaHub extends JavaPlugin {

    private static MayaHub instance;

    @Override
    public void onEnable() {
        instance = this;

        // Check for updates
        UpdateChecker.checkUpdate();

        // Save default config if it doesn't exist
        saveDefaultConfig();
        // Load the config into memory
        reloadConfig();

        // Initialize bStats
        new Metrics(this, 24473);

        // Register events
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this); // Join, quit and respawn
        Bukkit.getPluginManager().registerEvents(new MessageListener(), this); // Chat messages
        Bukkit.getPluginManager().registerEvents(new InvulnerabilityListener(), this); // Invulnerability
        Bukkit.getPluginManager().registerEvents(new WorldProtectionListener(), this); // World Protection

        // Start tasks
        if (this.getConfig().getBoolean("chat.enable-broadcasts", false)) {
            BroadcastTask.startTask();
        }

        //  Register Commands
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(SpawnCommand.registerCommand().build(),
                    "Teleport to spawn");
            commands.register(SetSpawnCommand.registerCommand().build(),
                    "Set spawn location");
            commands.register(FlyCommand.registerCommand().build(),
                    "Toggle flight mode");

            if (this.getConfig().getBoolean("world-protection", true)) {
                commands.register(ToggleEditCommand.registerCommand().build(),
                        "Toggle edit mode");
            }
        });
    }

    // get plugin instance
    public static MayaHub getInstance() {
        return instance;
    }
}
