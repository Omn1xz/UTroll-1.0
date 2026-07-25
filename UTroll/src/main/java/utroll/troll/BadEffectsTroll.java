package utroll.troll;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import utroll.UTroll;

public class BadEffectsTroll {

    public static void applyBadEffects(Player player) {
        int timer = UTroll.getInstance().getConfig().getInt("troll.badeff-timer", 15) * 20;

        PotionEffect[] badEffects = {
            new PotionEffect(PotionEffectType.BLINDNESS, timer, 1),
            new PotionEffect(PotionEffectType.WITHER, timer, 1),
            new PotionEffect(PotionEffectType.SLOWNESS, timer, 2),
            new PotionEffect(PotionEffectType.MINING_FATIGUE, timer, 2),
            new PotionEffect(PotionEffectType.NAUSEA, timer, 1),
            new PotionEffect(PotionEffectType.POISON, timer, 1),
            new PotionEffect(PotionEffectType.INSTANT_DAMAGE, timer, 1),
            new PotionEffect(PotionEffectType.WEAKNESS, timer, 1),
            new PotionEffect(PotionEffectType.HUNGER, timer, 1),
            new PotionEffect(PotionEffectType.DARKNESS, timer, 1)
        };

        for (PotionEffect effect : badEffects) {
            player.addPotionEffect(effect);
        }
    }
}
