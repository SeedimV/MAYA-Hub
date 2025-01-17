package me.seedim.mayaHub.Commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static io.papermc.paper.command.brigadier.Commands.literal;

public class ToggleEditCommand {

    // A map to store player edit mode states
    private static final HashMap<UUID, Boolean> editModePlayers = new HashMap<>();

    public static LiteralArgumentBuilder<CommandSourceStack> registerCommand() {

        // Define '/editmode' command
        return literal("editmode")
                .requires(sender -> sender.getSender().hasPermission("MayaHub.editmode"))
                .executes(ToggleEditCommand::handleMainCommand);
    }

    // Handle '/editmode' command
    private static int handleMainCommand(CommandContext<CommandSourceStack> context) {

        //  Check if sender is player
        if (!(context.getSource().getSender() instanceof Player player)) {
            Bukkit.getLogger().info("You have to be player to issue command: /editmode");
            return 1;
        }

        // Toggle edit mode for the player
        UUID playerUUID = player.getUniqueId();
        boolean isInEditMode = editModePlayers.getOrDefault(playerUUID, false);

        // Change the state
        editModePlayers.put(playerUUID, !isInEditMode);

        // Notify the player
        player.sendActionBar(MiniMessage.miniMessage().deserialize("Edit mode: " + (isInEditMode ? "<red>disabled" : "<green>enabled") + "<white>."));

        return 1;
    }

    // Get player's edit mode status
    public static boolean isPlayerInEditMode(Player player) {
        return editModePlayers.getOrDefault(player.getUniqueId(), false);
    }

}
