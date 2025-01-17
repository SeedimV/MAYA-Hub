package me.seedim.mayaHub.Listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MessageListener implements Listener {

    // Render chat messages
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer((source, sourceDisplayname, message, viewer) -> sourceDisplayname
                .append(Component.text(": "))
                .append(message));
    }
}
