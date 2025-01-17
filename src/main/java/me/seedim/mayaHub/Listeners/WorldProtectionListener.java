package me.seedim.mayaHub.Listeners;

import me.seedim.mayaHub.Commands.ToggleEditCommand;
import me.seedim.mayaHub.MayaHub;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WorldProtectionListener implements Listener {

    // Prevent players breaking blocks
    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event) {

        // Check if world protection is on
        if (!MayaHub.getInstance().getConfig().getBoolean("world-protection", true)) {
            return;
        }

        // Check if player in edit mode
        if (ToggleEditCommand.isPlayerInEditMode(event.getPlayer())) {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().sendActionBar(MiniMessage.miniMessage().deserialize("<red><bold>Sorry!</bold></red> You cannot do that!"));
    }

    //  Prevent players damaging entities
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        // Check if world protection is on
        if (!MayaHub.getInstance().getConfig().getBoolean("world-protection", true)) {
            return;
        }

        // Check if player in edit mode
        if (ToggleEditCommand.isPlayerInEditMode(player)) {
            return;
        }

        // Cancel the event
        event.setCancelled(true);

        // Notify the player
        player.sendActionBar(MiniMessage.miniMessage().deserialize("<red><bold>Sorry!</bold></red> You cannot do that!"));
    }

    //  Prevent player interaction with blocks
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        // Check if world protection is on
        if (!MayaHub.getInstance().getConfig().getBoolean("world-protection", true)) {
            return;
        }

        // Check if player in edit mode
        if (ToggleEditCommand.isPlayerInEditMode(event.getPlayer())) {
            return;
        }

        // Cancel the event
        event.setCancelled(true);
        event.getPlayer().sendActionBar(MiniMessage.miniMessage().deserialize("<red><bold>Sorry!</bold></red> You cannot do that!"));
    }
}
