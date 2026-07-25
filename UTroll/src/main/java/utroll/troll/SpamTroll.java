package utroll.troll;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpamTroll {

    public static void spam(Player player) {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            player.sendMessage("§cProtocolLib не найден!");
            return;
        }

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (!player.isOnline() || count > 100) {
                    this.cancel();
                    return;
                }

                try {
                    for (int i = 0; i < 20; i++) {
                        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
                        packet.getGameStateIDs().write(0, 10);
                        packet.getFloat().write(0, 0.0F);
                        protocolManager.sendServerPacket(player, packet);
                    }

                    StringBuilder longText = new StringBuilder();
                    for (int i = 0; i < 100; i++) {
                        longText.append("§c§k█§r§e§k█§r§a§k█§r§b§k█§r");
                    }

                    player.sendTitle(longText.toString(), longText.toString(), 0, 50, 0);
                    player.sendActionBar(longText.toString());

                    if (count % 10 == 0) {
                        player.sendMessage("§c§l" + longText);
                    }

                    count++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(utroll.UTroll.getInstance(), 0L, 1L);
    }
}