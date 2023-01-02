package live.helixsmp.reedspec;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReedSpec extends JavaPlugin implements CommandExecutor {
    private Map<Player, Location> spectatingPlayers = new HashMap();

    public ReedSpec() {
    }

    public void onLoad() {
        this.getLogger().info("ReedSpec has loaded");
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("spec").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.GOLD + "This command can only be used by players.");
            return true;
        } else {
            Player player = (Player)sender;
            if (!player.hasPermission("myspecplugin.use")) {
                player.sendMessage(ChatColor.GOLD + "You do not have permission to use this command.");
                return true;
            } else {
                Location savedLocation;
                if (this.spectatingPlayers.containsKey(player)) {
                    savedLocation = (Location)this.spectatingPlayers.get(player);
                    player.teleport(savedLocation);
                    player.setGameMode(GameMode.SURVIVAL);
                    this.spectatingPlayers.remove(player);
                    player.sendMessage(ChatColor.GOLD + "You are no longer in spectator mode.");
                } else {
                    savedLocation = player.getLocation();
                    this.spectatingPlayers.put(player, savedLocation);
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.GOLD + "You are now in spectator mode.");
                }

                return true;
            }
        }
    }
}
