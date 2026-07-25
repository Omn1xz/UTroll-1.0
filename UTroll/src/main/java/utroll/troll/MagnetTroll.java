package utroll.troll;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import utroll.UTroll;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MagnetTroll {

    private static final Map<UUID, BukkitTask> magnetTasks = new HashMap<>();
    private static final Map<UUID, Player> magnetTargets = new HashMap<>();

    public static void startMagnet(Player target, Player sender) {
        if (magnetTasks.containsKey(target.getUniqueId())) {
            return;
        }

        magnetTargets.put(target.getUniqueId(), sender);

        BukkitTask task = UTroll.getInstance().getServer().getScheduler().runTaskTimer(UTroll.getInstance(), () -> {
            if (!target.isOnline() || !sender.isOnline()) {
                stopMagnet(target);
                return;
            }

            Location senderLoc = sender.getLocation();
            Location targetLoc = target.getLocation();

            double distance = senderLoc.distance(targetLoc);
            if (distance > 30) {
                return;
            }

            if (distance > 2) {
                double speed = 1.5;
                double x = senderLoc.getX() - targetLoc.getX();
                double y = senderLoc.getY() - targetLoc.getY();
                double z = senderLoc.getZ() - targetLoc.getZ();

                double length = Math.sqrt(x * x + y * y + z * z);
                if (length > 0) {
                    x = (x / length) * speed;
                    y = (y / length) * speed;
                    z = (z / length) * speed;
                }

                target.setVelocity(new org.bukkit.util.Vector(x, y, z));
            }
        }, 0L, 1L);

        magnetTasks.put(target.getUniqueId(), task);
    }

    public static void stopMagnet(Player target) {
        BukkitTask task = magnetTasks.remove(target.getUniqueId());
        if (task != null) {
            task.cancel();
        }
        magnetTargets.remove(target.getUniqueId());
    }
}
