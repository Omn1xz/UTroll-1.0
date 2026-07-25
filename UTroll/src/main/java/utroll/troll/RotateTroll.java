package utroll.troll;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import utroll.UTroll;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class RotateTroll {

    private static final Map<UUID, Integer> rotatingPlayers = new HashMap<>();
    private static final Random random = new Random();

    public static void rotate(Player player) {
        if (rotatingPlayers.containsKey(player.getUniqueId())) {
            return;
        }

        int timer = UTroll.getInstance().getConfig().getInt("troll.rotate-timer", 15);
        rotatingPlayers.put(player.getUniqueId(), timer);

        UTroll.getInstance().getServer().getScheduler().runTaskTimer(UTroll.getInstance(), new Runnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (!player.isOnline() || ticks >= timer * 20) {
                    rotatingPlayers.remove(player.getUniqueId());
                    return;
                }

                Location loc = player.getLocation();
                float yaw = loc.getYaw() + (random.nextFloat() - 0.5f) * 90;
                float pitch = Math.max(-90, Math.min(90, loc.getPitch() + (random.nextFloat() - 0.5f) * 90));
                loc.setYaw(yaw);
                loc.setPitch(pitch);
                player.teleport(loc);
                ticks++;
            }
        }, 0L, 1L);
    }

    public static void stopRotation(Player player) {
        rotatingPlayers.remove(player.getUniqueId());
    }
}