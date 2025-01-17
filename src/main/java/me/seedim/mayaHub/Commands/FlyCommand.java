package me.seedim.mayaHub.Commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static io.papermc.paper.command.brigadier.Commands.literal;

public class FlyCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> registerCommand() {

        // Define '/fly' command
        return literal("fly")
                .requires(sender -> sender.getSender().hasPermission("MayaHub.fly"))
                .executes(FlyCommand::handleMainCommand);
    }

    // Handle '/fly' command
    private static int handleMainCommand(CommandContext<CommandSourceStack> context) {

        //  Check if sender is player
        if (!(context.getSource().getSender() instanceof Player player)) {
            Bukkit.getLogger().info("You have to be player to issue command: /fly");
            return 1;
        }

        boolean isFlying = player.getAllowFlight();
        player.setAllowFlight(!isFlying);
        player.setFlying(!isFlying);

        // Notify the player
        player.sendActionBar(MiniMessage.miniMessage().deserialize("Flight mode: " + (isFlying ? "<red>disabled" : "<green>enabled") + "<white>."));

        return 1;
    }

}
