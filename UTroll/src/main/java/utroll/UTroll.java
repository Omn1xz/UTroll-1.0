package utroll;

import org.bukkit.plugin.java.JavaPlugin;
import utroll.command.TrollCommand;
import utroll.command.TrollTabCompleter;
import utroll.listener.TNTListener;

public final class UTroll extends JavaPlugin {

    private static UTroll instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getCommand("troll").setExecutor(new TrollCommand());
        getCommand("troll").setTabCompleter(new TrollTabCompleter());
        getServer().getPluginManager().registerEvents(new TNTListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static UTroll getInstance() {
        return instance;
    }
}
