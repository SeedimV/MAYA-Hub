package me.seedim.mayaHub.Listeners;

import me.seedim.mayaHub.MayaHub;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ConnectionListener implements Listener {

    // Send join message
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String joinMessage = MayaHub.getInstance().getConfig().getString("chat.join-message");
        if (joinMessage != null) {
            joinMessage = joinMessage.replace("%player%", event.getPlayer().getName());
            event.joinMessage(MiniMessage.miniMessage().deserialize(joinMessage));
        }

        // Teleport player to the spawn
        event.getPlayer().teleport(spawnLocation(event.getPlayer()));
    }

    // Send quit message
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String joinMessage = MayaHub.getInstance().getConfig().getString("chat.quit-message");
        if (joinMessage != null) {
            joinMessage = joinMessage.replace("%player%", event.getPlayer().getName());
            event.quitMessage(MiniMessage.miniMessage().deserialize(joinMessage));
        }
    }

    // Handle player respawn
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        //  When player dies, respawn them at the world spawn
        event.setRespawnLocation(spawnLocation(event.getPlayer()));
    }

    // Modify spawn location
    private Location spawnLocation(Player player) {

        // Get spawn location
        Location spawnLocation = MayaHub.getInstance().getConfig().getLocation("spawn");

        // If spawn is not set, get world spawn
        if (spawnLocation == null) {
            spawnLocation = player.getWorld().getSpawnLocation();
            spawnLocation.setX(spawnLocation.getX() + 0.5);
            spawnLocation.setZ(spawnLocation.getZ() + 0.5);
            spawnLocation.setPitch(0.0f);
        }

        return spawnLocation;
    }
}
