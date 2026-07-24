package utroll.troll;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import utroll.UTroll;

public class DownTroll {

    public static void teleportDown(Player player) {
        int blocks = UTroll.getInstance().getConfig().getInt("troll.down-blocks", 3);
        Location location = player.getLocation().clone();
        location.setY(location.getY() - blocks);
        player.teleport(location);
    }
}
