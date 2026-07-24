package utroll.troll;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HoloTroll {

    private static final Map<UUID, ArmorStand> holograms = new HashMap<>();

    public static void showHologram(Player player) {
        if (holograms.containsKey(player.getUniqueId())) {
            return;
        }

        Location location = player.getLocation().clone();
        location.add(0, 2.5, 0);

        ArmorStand armorStand = player.getWorld().spawn(location, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("§e↓ Опущенный позорник ↓");
        armorStand.setGravity(false);

        holograms.put(player.getUniqueId(), armorStand);

        BukkitTask task = utroll.UTroll.getInstance().getServer().getScheduler().runTaskTimer(utroll.UTroll.getInstance(), () -> {
            if (!player.isOnline() || !holograms.containsKey(player.getUniqueId())) {
                armorStand.remove();
                return;
            }
            Location playerLoc = player.getLocation().clone();
            playerLoc.add(0, 2.5, 0);
            armorStand.teleport(playerLoc);
        }, 0L, 1L);
    }

    public static void hideHologram(Player player) {
        ArmorStand armorStand = holograms.remove(player.getUniqueId());
        if (armorStand != null) {
            armorStand.remove();
        }
    }
}
