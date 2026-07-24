package utroll;

import org.bukkit.plugin.java.JavaPlugin;
import utroll.command.TrollCommand;

public final class UTroll extends JavaPlugin {

    private static UTroll instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getCommand("troll").setExecutor(new TrollCommand());
    }

    @Override
    public void onDisable() {
    }

    public static UTroll getInstance() {
        return instance;
    }
}
