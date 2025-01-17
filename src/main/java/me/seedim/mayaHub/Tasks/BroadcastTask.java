package me.seedim.mayaHub.Tasks;

import me.seedim.mayaHub.MayaHub;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

public class BroadcastTask {

    private static int i = 0;

    public static void startTask(){
        // Broadcast every 5 minutes (or 6000 ticks)
        BukkitScheduler scheduler = Bukkit.getScheduler();

        // Return if no messages has been set
        if (MayaHub.getInstance().getConfig().getStringList("chat.broadcast-messages").isEmpty()) {
            return;
        }

        int interval = MayaHub.getInstance().getConfig().getInt("chat.broadcast-interval", 6000);
        List<String> messages = MayaHub.getInstance().getConfig().getStringList("chat.broadcast-messages");

        Runnable runnable =  () -> {
            String msg = messages.get(i++);

            Bukkit.getServer().sendMessage(MiniMessage.miniMessage().deserialize(msg));

            if (i >= messages.size()) {
                i = 0;
            }
        };

        scheduler.runTaskTimer(MayaHub.getInstance(), runnable, 0, interval);
    }

}
