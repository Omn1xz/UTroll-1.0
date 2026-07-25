package utroll.troll;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import utroll.UTroll;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InvDropTroll {

    private static final Map<UUID, Boolean> droppingPlayers = new HashMap<>();

    public static void dropInventory(Player player) {
        if (droppingPlayers.containsKey(player.getUniqueId())) {
            return;
        }

        droppingPlayers.put(player.getUniqueId(), true);

        new Thread(() -> {
            try {
                ItemStack[] contents = player.getInventory().getContents().clone();
                
                for (ItemStack item : contents) {
                    if (item == null || item.getType().isAir()) {
                        continue;
                    }
                    
                    if (!player.isOnline()) {
                        droppingPlayers.remove(player.getUniqueId());
                        return;
                    }

                    final ItemStack itemToDrop = item.clone();
                    final int slot = player.getInventory().first(item);

                    UTroll.getInstance().getServer().getScheduler().runTask(UTroll.getInstance(), () -> {
                        if (slot >= 0) {
                            player.getInventory().setItem(slot, null);
                            player.getWorld().dropItemNaturally(player.getLocation(), itemToDrop);
                        }
                    });

                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                droppingPlayers.remove(player.getUniqueId());
            }
        }).start();
    }
}
