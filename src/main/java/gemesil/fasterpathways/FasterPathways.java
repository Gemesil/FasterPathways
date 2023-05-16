package gemesil.fasterpathways;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class FasterPathways extends JavaPlugin implements Listener {

    PotionEffect speed_effect;
    FileConfiguration config = getConfig();
    String actionBarMessage;

    @Override
    public void onEnable() {
        // Save config settings as default
        config.options().copyDefaults(true);
        saveConfig();

        // Register events
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        actionBarMessage = config.getString("actionBarMessage");

        // Get the speed from the config
        int speedLevel = config.getInt("SpeedEffectLevel");
        int speedDuration = config.getInt("SpeedEffectDuration");
        speed_effect = new PotionEffect(PotionEffectType.SPEED, speedDuration * 20, speedLevel);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        final Player player = event.getPlayer();
        final Block block = player.getLocation().getBlock();
        final Block relativeBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        getLogger().info(player.getName() + ":" + block.getType().name() + ":" + relativeBlock.getType().name());


        // player stepping on path block
        if (relativeBlock.getType() == Material.DIRT_PATH || block.getType() == Material.DIRT_PATH) {
            getLogger().info(player.getName() + " stepped on a dirt path!");
            player.addPotionEffect(speed_effect);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + actionBarMessage));
        }
    }
}
