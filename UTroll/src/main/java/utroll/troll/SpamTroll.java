package utroll.troll;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import utroll.UTroll;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class SpamTroll {

    private static final Map<UUID, BossBar> bossBars = new HashMap<>();
    private static final Random random = new Random();
    private static final String[] colors = {"§c", "§a", "§b", "§e", "§d", "§f", "§6", "§9"};
    private static final String[] symbols = {"#", "@", "$", "%", "&", "*", "!", "?"};

    public static void spam(Player player) {
        if (bossBars.containsKey(player.getUniqueId())) {
            return;
        }

        int timer = UTroll.getInstance().getConfig().getInt("troll.spam-timer", 15);
        BossBar bossBar = BossBar.bossBar(Component.text("SPAM"), 1.0f, BossBar.Color.PINK, BossBar.Overlay.PROGRESS);
        player.showBossBar(bossBar);
        bossBars.put(player.getUniqueId(), bossBar);

        new Thread(() -> {
            try {
                for (int i = 0; i < timer * 20; i++) {
                    if (!player.isOnline()) {
                        player.hideBossBar(bossBar);
                        bossBars.remove(player.getUniqueId());
                        return;
                    }

                    String color = colors[random.nextInt(colors.length)];
                    String symbol = symbols[random.nextInt(symbols.length)];
                    String message = color + symbol + " " + color + symbol + " " + color + symbol;

                    final String actionBarMessage = message;
                    final String chatMessage = message;
                    final Component bossBarMessage = Component.text(message);

                    UTroll.getInstance().getServer().getScheduler().runTask(UTroll.getInstance(), () -> {
                        player.sendActionBar(Component.text(actionBarMessage));
                        player.sendMessage(chatMessage);
                        bossBar.name(bossBarMessage);
                    });

                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                UTroll.getInstance().getServer().getScheduler().runTask(UTroll.getInstance(), () -> {
                    player.hideBossBar(bossBar);
                    bossBars.remove(player.getUniqueId());
                });
            }
        }).start();
    }
}
