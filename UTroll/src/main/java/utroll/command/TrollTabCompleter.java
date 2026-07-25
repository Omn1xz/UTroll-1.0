package utroll.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TrollTabCompleter implements TabCompleter {

    private static final List<String> ACTIONS = Arrays.asList(
            "launch", "crash", "rotate", "invdrop", "badeffects", "rtp", "down", "spam", "holo", "allahackbar", "magnet"
    );

    private static final List<String> ON_OFF = Arrays.asList("on", "off");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (Player player : sender.getServer().getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 2) {
            completions.addAll(ACTIONS);
        } else if (args.length == 3) {
            String action = args[1].toLowerCase();
            if (action.equals("holo") || action.equals("magnet")) {
                completions.addAll(ON_OFF);
            }
        }

        return completions.stream()
                .filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}
