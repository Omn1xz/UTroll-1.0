package utroll.troll;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import utroll.UTroll;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AllahAkbarTroll {

    private static final List<UUID> activePlayers = new ArrayList<>();
    private static final List<TNTPrimed> allTNT = new ArrayList<>();

    public static void spawnTNT(Player player) {
        if (activePlayers.contains(player.getUniqueId())) {
            return;
        }

        int tntCount = UTroll.getInstance().getConfig().getInt("troll.allahakbar", 1);
        activePlayers.add(player.getUniqueId());

        for (int i = 0; i < tntCount; i++) {
            Location location = player.getLocation().clone().add(0, 2.5, 0);
            TNTPrimed tnt = player.getWorld().spawn(location, TNTPrimed.class);
            tnt.setFuseTicks(80);
            tnt.setGravity(false);
            tnt.setYield(6.0f);
            tnt.setIsIncendiary(false);
            allTNT.add(tnt);
        }

        UTroll.getInstance().getServer().getScheduler().runTaskTimer(UTroll.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (!player.isOnline() || !activePlayers.contains(player.getUniqueId())) {
                    activePlayers.remove(player.getUniqueId());
                    for (TNTPrimed tnt : allTNT) {
                        if (!tnt.isDead()) {
                            tnt.remove();
                        }
                    }
                    allTNT.clear();
                    return;
                }

                Location playerLoc = player.getLocation().clone().add(0, 2.5, 0);
                for (TNTPrimed tnt : allTNT) {
                    if (!tnt.isDead()) {
                        tnt.teleport(playerLoc);
                    }
                }
            }
        }, 0L, 1L);
    }
}
