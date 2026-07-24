package utroll.troll;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import utroll.UTroll;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class RandomTPTroll {

    private static final Map<UUID, Boolean> teleportingPlayers = new HashMap<>();
    private static final Random random = new Random();

    public static void randomTeleport(Player player) {
        if (teleportingPlayers.containsKey(player.getUniqueId())) {
            return;
        }

        teleportingPlayers.put(player.getUniqueId(), true);

        Location originalLocation = player.getLocation().clone();
        int blocks = UTroll.getInstance().getConfig().getInt("troll.rtp.blocks", 10);
        int timer = UTroll.getInstance().getConfig().getInt("troll.rtp.timer", 15);

        new Thread(() -> {
            try {
                for (int i = 0; i < timer * 20; i++) {
                    if (!player.isOnline()) {
                        teleportingPlayers.remove(player.getUniqueId());
                        return;
                    }

                    double xOffset = (random.nextDouble() - 0.5) * 2 * blocks;
                    double zOffset = (random.nextDouble() - 0.5) * 2 * blocks;
                    
                    final Location newLocation = new Location(
                        originalLocation.getWorld(),
                        originalLocation.getX() + xOffset,
                        originalLocation.getY(),
                        originalLocation.getZ() + zOffset,
                        player.getLocation().getYaw(),
                        player.getLocation().getPitch()
                    );

                    UTroll.getInstance().getServer().getScheduler().runTask(UTroll.getInstance(), () -> {
                        player.teleport(newLocation);
                    });

                    Thread.sleep(50);
                }
                
                UTroll.getInstance().getServer().getScheduler().runTask(UTroll.getInstance(), () -> {
                    player.teleport(originalLocation);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                teleportingPlayers.remove(player.getUniqueId());
            }
        }).start();
    }
}
