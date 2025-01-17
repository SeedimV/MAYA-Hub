package me.seedim.mayaHub.Commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.seedim.mayaHub.MayaHub;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static io.papermc.paper.command.brigadier.Commands.literal;

public class SetSpawnCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> registerCommand() {

        // Define '/setspawn' command
        return literal("setspawn")
                .requires(sender -> sender.getSender().hasPermission("MayaHub.setspawn"))
                .executes(SetSpawnCommand::handleMainCommand);
    }

    // Handle '/setspawn' command
    private static int handleMainCommand(CommandContext<CommandSourceStack> context) {

        //  Check if sender is player
        if (!(context.getSource().getSender() instanceof Player player)) {
            Bukkit.getLogger().info("You have to be player to issue command: /setspawn");
            return 1;
        }

        // Get spawn location
        Location spawnLocation = player.getLocation();
        spawnLocation.setPitch(0.0f);

        // Get and save spawn location in config
        MayaHub.getInstance().getConfig().set("spawn", spawnLocation);
        MayaHub.getInstance().saveConfig();

        player.sendActionBar(MiniMessage.miniMessage().deserialize("<green>Spawn location set."));
        return 1;
    }
}
