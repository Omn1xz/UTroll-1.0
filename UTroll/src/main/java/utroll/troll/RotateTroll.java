package utroll.troll;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import utroll.UTroll;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RotateTroll {

    private static final Map<UUID, Integer> rotatingPlayers = new HashMap<>();

    public static void rotate(Player player) {
        if (rotatingPlayers.containsKey(player.getUniqueId())) {
            return;
        }

        int timer = UTroll.getInstance().getConfig().getInt("troll.rotate-timer", 15);
        rotatingPlayers.put(player.getUniqueId(), timer);

        UTroll.getInstance().getServer().getScheduler().runTaskTimer(UTroll.getInstance(), new Runnable() {
            int ticks = 0;
            float currentYaw = player.getLocation().getYaw();

            @Override
            public void run() {
                if (!player.isOnline() || ticks >= timer * 20) {
                    rotatingPlayers.remove(player.getUniqueId());
                    // Отменяем таск, если используется runTaskTimer
                    return;
                }

                currentYaw += 45;
                Location loc = player.getLocation();
                loc.setYaw(currentYaw);
                player.teleport(loc);
                ticks++;
            }
        }, 0L, 1L);
    }

    public static void stopRotation(Player player) {
        rotatingPlayers.remove(player.getUniqueId());
    }
}