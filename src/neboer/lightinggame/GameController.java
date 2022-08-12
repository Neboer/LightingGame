package neboer.lightinggame;

import com.sun.security.auth.login.ConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import neboer.lightinggame.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;

import static org.bukkit.Bukkit.*;

public class GameController implements CommandExecutor {
    public Main plugin;
    public KyaruGame kyaru_game_listener;
    public boolean kyaru_game_started;
    public FileConfiguration config_file;

    public GameController(Main input_plugin, KyaruGame kyaruGame) {
        plugin = input_plugin;
        kyaru_game_listener = kyaruGame;
        kyaru_game_started = false;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length < 2) return false;
            if (args[0].equals("KyaruGame")) {
                if (args[1].equals("start")) {
                    if (!kyaru_game_started) {
                        getServer().getPluginManager().registerEvents(kyaru_game_listener, plugin);
                        kyaru_game_started = true;
                        getLogger().info("Kyaru Game is start!");
                        sender.sendMessage("Kyaru Game is start!");
                    } else {
                        sender.sendMessage("Kyaru Game has been already started!");
                    }
                    return true;
                } else if (args[1].equals("stop")) {
                    if (kyaru_game_started) {
                        HandlerList.unregisterAll(kyaru_game_listener);
                        kyaru_game_started = false;
                        getLogger().info("Kyaru Game is stop!");
                        sender.sendMessage("Kyaru Game is stop!");
                    } else {
                        sender.sendMessage("Kyaru Game is not running!");
                    }
                    return true;
                } else return false;
            } else return false;
        } else {
            sender.sendMessage("Only ops can execute this command!");
            return true;
        }
    }
}