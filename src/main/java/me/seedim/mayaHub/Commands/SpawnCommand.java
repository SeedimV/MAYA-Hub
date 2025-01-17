package me.seedim.mayaHub.Commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.seedim.mayaHub.MayaHub;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static io.papermc.paper.command.brigadier.Commands.literal;

public class SpawnCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> registerCommand() {

        // Define '/spawn' command
        return literal("spawn")
                .executes(SpawnCommand::handleMainCommand);
    }

    // Handle '/spawn' command
    private static int handleMainCommand(CommandContext<CommandSourceStack> context) {

        //  Check if sender is player
        if (!(context.getSource().getSender() instanceof Player player)) {
            Bukkit.getLogger().info("You have to be player to issue command: /spawn");
            return 1;
        }

        // Get spawn location
        Location spawnLocation = MayaHub.getInstance().getConfig().getLocation("spawn");

        // If spawn is not set, get world spawn
        if (spawnLocation == null) {
            spawnLocation = player.getWorld().getSpawnLocation();
            player.sendActionBar(MiniMessage.miniMessage().deserialize("Spawn location not set. Teleporting to world spawn."));
            spawnLocation.setX(spawnLocation.getX() + 0.5);
            spawnLocation.setZ(spawnLocation.getZ() + 0.5);
            spawnLocation.setPitch(0.0f);
        } else {
            player.sendActionBar(MiniMessage.miniMessage().deserialize("Teleported to spawn."));
        }

        // Teleport to spawn and display action bar message
        player.teleport(spawnLocation);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1, 1);
        return 1;
    }

}
