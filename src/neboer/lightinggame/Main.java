package neboer.lightinggame;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event;
import neboer.lightinggame.KyaruGame;

import java.util.Objects;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("LightingGame is loaded!");
        GameController command_controller = new GameController(this, new KyaruGame(this));
        Objects.requireNonNull(this.getCommand("lightinggame")).setExecutor(command_controller);
    }

    @Override
    public void onDisable() {

    }
}