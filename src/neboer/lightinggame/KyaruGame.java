package neboer.lightinggame;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Map;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;

public class KyaruGame implements Listener {
    public boolean enable_game;
    public Main plugin;

    KyaruGame(Main input_plugin) {
        plugin = input_plugin;
    }

    private boolean isKyaru(Player player) {
        ItemStack player_head = player.getInventory().getHelmet();
        if (player_head == null) {
            return false;
        } else {
            SkullMeta meta = (SkullMeta) player_head.getItemMeta();
            if (meta != null) {
                OfflinePlayer skull_player = meta.getOwningPlayer();
                if (skull_player != null) {
                    return Objects.equals(skull_player.getName(), "kiruya");
                }
            }
        }
        return false;
    }

    private void makeKyaru(Player target) {
        Map<String, Object> remake_kyaru_skull_data = plugin.getConfig().getConfigurationSection("kyaru_head").getValues(true);
        ItemStack remake_kyaru_skull = ItemStack.deserialize(remake_kyaru_skull_data);
        PlayerInventory target_inventory = target.getInventory();
        target_inventory.setHelmet(remake_kyaru_skull);
    }

    private void unmakeKyaru(Player player) {
        player.getInventory().setHelmet(null);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getRightClicked();
        if (target instanceof Player && isKyaru(player) && !isKyaru((Player) target)) {
            getLogger().info("Convert to Kyaru!");
            makeKyaru((Player) target);
            unmakeKyaru(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getSlot() == 39 && isKyaru(player)) {
            // kyaru's head cannot be taken from the helmet slot.
            event.setCancelled(true);
        }
    }
}
