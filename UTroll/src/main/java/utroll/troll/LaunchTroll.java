package utroll.troll;

import org.bukkit.entity.Player;
import utroll.UTroll;

public class LaunchTroll {

    public static void launch(Player player) {
        int blocks = UTroll.getInstance().getConfig().getInt("troll.launch-blocks", 25);
        player.setVelocity(player.getVelocity().setY(blocks * 0.5));
    }
}
