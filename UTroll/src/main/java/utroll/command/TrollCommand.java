package utroll.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utroll.troll.*;

public class TrollCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("utroll.troll")) {
            sender.sendMessage("§cУ вас нет прав на использование этой команды!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cИспользование: /troll <nick> <action>");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cИспользование: /troll <nick> <action>");
            return true;
        }

        Player target = sender.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cИгрок не найден!");
            return true;
        }

        String action = args[1].toLowerCase();

        switch (action) {
            case "launch":
                LaunchTroll.launch(target);
                sender.sendMessage("§aИгрок " + target.getName() + " подкинут в воздух!");
                break;
            case "crash":
                CrashTroll.crash(target);
                sender.sendMessage("§aИгрок " + target.getName() + " крашнут!");
                break;
            case "rotate":
                RotateTroll.rotate(target);
                sender.sendMessage("§aКамера игрока " + target.getName() + " начнёт вращаться!");
                break;
            case "invdrop":
                InvDropTroll.dropInventory(target);
                sender.sendMessage("§aИнвентарь игрока " + target.getName() + " будет выброшен!");
                break;
            case "badeffects":
                BadEffectsTroll.applyBadEffects(target);
                sender.sendMessage("§aПлохие эффекты применены к игроку " + target.getName() + "!");
                break;
            case "rtp":
                RandomTPTroll.randomTeleport(target);
                sender.sendMessage("§aИгрок " + target.getName() + " начнёт телепортироваться!");
                break;
            case "down":
                DownTroll.teleportDown(target);
                sender.sendMessage("§aИгрок " + target.getName() + " телепортирован вниз!");
                break;
            case "spam":
                SpamTroll.spam(target);
                sender.sendMessage("§aСпам начат для игрока " + target.getName() + "!");
                break;
            case "holo":
                if (args.length < 3) {
                    sender.sendMessage("§cИспользование: /troll <nick> holo <on/off>");
                    return true;
                }
                String holoAction = args[2].toLowerCase();
                if (holoAction.equals("on")) {
                    HoloTroll.showHologram(target);
                    sender.sendMessage("§aГолограмма включена для игрока " + target.getName() + "!");
                } else if (holoAction.equals("off")) {
                    HoloTroll.hideHologram(target);
                    sender.sendMessage("§aГолограмма выключена для игрока " + target.getName() + "!");
                } else {
                    sender.sendMessage("§cИспользование: /troll <nick> holo <on/off>");
                }
                break;
            case "allahackbar":
                AllahAkbarTroll.spawnTNT(target);
                sender.sendMessage("§aTNT запущен за игроком " + target.getName() + "!");
                break;
            case "magnet":
                if (args.length < 3) {
                    sender.sendMessage("§cИспользование: /troll <nick> magnet <on/off>");
                    return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cЭту команду может использовать только игрок!");
                    return true;
                }
                String magnetAction = args[2].toLowerCase();
                if (magnetAction.equals("on")) {
                    MagnetTroll.startMagnet(target, (Player) sender);
                    sender.sendMessage("§aМагнит включён для игрока " + target.getName() + "!");
                } else if (magnetAction.equals("off")) {
                    MagnetTroll.stopMagnet(target);
                    sender.sendMessage("§aМагнит выключен для игрока " + target.getName() + "!");
                } else {
                    sender.sendMessage("§cИспользование: /troll <nick> magnet <on/off>");
                }
                break;
            default:
                sender.sendMessage("§cНеизвестное действие! Используйте /troll help для справки.");
                break;
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§6=== UTroll Команды ===");
        sender.sendMessage("§e/troll help §7- Показать справку");
        sender.sendMessage("§e/troll <nick> launch §7- Подкинуть игрока в воздух");
        sender.sendMessage("§e/troll <nick> crash §7- Крашнуть клиент игрока");
        sender.sendMessage("§e/troll <nick> rotate §7- Вращать камеру игрока");
        sender.sendMessage("§e/troll <nick> invdrop §7- Выбросить инвентарь игрока");
        sender.sendMessage("§e/troll <nick> badeffects §7- Выдать плохие эффекты");
        sender.sendMessage("§e/troll <nick> rtp §7- Случайные телепортации");
        sender.sendMessage("§e/troll <nick> down §7- Телепортировать вниз");
        sender.sendMessage("§e/troll <nick> spam §7- Спамить игроку");
        sender.sendMessage("§e/troll <nick> holo <on/off> §7- Голограмма над головой");
        sender.sendMessage("§e/troll <nick> allahackbar §7- TNT за игроком");
        sender.sendMessage("§e/troll <nick> magnet <on/off> §7- Притягивать игрока к себе");
    }
}
