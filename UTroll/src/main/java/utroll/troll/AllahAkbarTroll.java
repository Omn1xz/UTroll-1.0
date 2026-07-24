package utroll.troll;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import utroll.UTroll;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AllahAkbarTroll {

    private static final Map<UUID, TNTPrimed> followingTNT = new HashMap<>();

    public static void spawnTNT(Player player) {
        if (followingTNT.containsKey(player.getUniqueId())) {
            return;
        }

        Location location = player.getLocation().clone();
        TNTPrimed tnt = player.getWorld().spawn(location, TNTPrimed.class);
        tnt.setFuseTicks(80);
        followingTNT.put(player.getUniqueId(), tnt);

        UTroll.getInstance().getServer().getScheduler().runTaskTimer(UTroll.getInstance(), () -> {
            if (!player.isOnline() || tnt.isDead() || !followingTNT.containsKey(player.getUniqueId())) {
                followingTNT.remove(player.getUniqueId());
                return;
            }

            Location playerLoc = player.getLocation().clone();
            tnt.teleport(playerLoc.add(0, 1, 0));
        }, 0L, 1L);
    }
}
