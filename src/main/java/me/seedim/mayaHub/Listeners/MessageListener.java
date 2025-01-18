package me.seedim.mayaHub.Listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.seedim.mayaHub.MayaHub;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MessageListener implements Listener {

    // Chat message format
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer((source, sourceDisplayname, message, viewer) -> {

            if (MayaHub.getInstance().getConfig().getString("chat.chat-format").isEmpty()) {
                return MiniMessage.miniMessage().deserialize(source.getName() + ": " + PlainTextComponentSerializer.plainText().serialize(message));
            }

            String formattedMessage = MayaHub.getInstance().getConfig().getString("chat.chat-format")
                    .replace("%player%", source.getName())
                    .replace("%message%", PlainTextComponentSerializer.plainText().serialize(message));

            return MiniMessage.miniMessage().deserialize(formattedMessage);
        });
    }
}
