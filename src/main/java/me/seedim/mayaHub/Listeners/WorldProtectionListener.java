package me.seedim.mayaHub.Listeners;

import me.seedim.mayaHub.Commands.ToggleEditCommand;
import me.seedim.mayaHub.MayaHub;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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

        // Cancel the event
        event.setCancelled(true);
        event.getPlayer().sendActionBar(MiniMessage.miniMessage().deserialize("<red><bold>Sorry!</bold></red> You cannot do that!"));

        // Display smoke particle
        Location blockLocation = event.getBlock().getLocation();
        blockLocation.setX(blockLocation.getX() + 0.5);
        blockLocation.setY(blockLocation.getY() + 1.2);
        blockLocation.setZ(blockLocation.getZ() + 0.5);
        event.getPlayer().spawnParticle(Particle.LARGE_SMOKE, blockLocation, 2, 0, 0, 0, 0);
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

        // Display smoke particle
        Location blockLocation = event.getEntity().getLocation();
        blockLocation.setY(blockLocation.getY() + 1.2);
        player.spawnParticle(Particle.LARGE_SMOKE, blockLocation, 2, 0, 0, 0, 0);
    }

    //  Prevent player interaction with interactable blocks
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

        // Check the action type
        Action action = event.getAction();

        // Check right clicks
        if (action != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
            return;
        }

        // Cancel the event
        event.setCancelled(true);
        event.getPlayer().sendActionBar(MiniMessage.miniMessage().deserialize("<red><bold>Sorry!</bold></red> You cannot do that!"));

        // Display smoke particle
        Location blockLocation = event.getClickedBlock().getLocation();
        blockLocation.setX(blockLocation.getX() + 0.5);
        blockLocation.setY(blockLocation.getY() + 1.2);
        blockLocation.setZ(blockLocation.getZ() + 0.5);
        event.getPlayer().spawnParticle(Particle.LARGE_SMOKE, blockLocation, 2, 0, 0, 0, 0);
    }
}
