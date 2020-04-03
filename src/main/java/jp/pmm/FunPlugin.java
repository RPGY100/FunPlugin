package jp.pmm;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class FunPlugin extends JavaPlugin implements Listener {
        @Override
        public void onEnable() {
                getLogger().info("有効化しました");
                getServer().getPluginManager().registerEvents(new AllEvent(), this);
        }
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
                if (cmd.getName().equalsIgnoreCase("super")) {
                        Player player = (Player) sender;
                        for (int i = 0; i < 200; i++) {
                                player.getLocation().setPitch(player.getLocation().getPitch() + 1);
                                player.sendMessage(player.getLocation().getPitch() + "です");
                        }
                        player.sendMessage("test");
                }
                if (cmd.getName().equalsIgnoreCase("explosion")) {
                        Player player = (Player) sender;
                        Location loc = player.getLocation();
                        Entity entity = (Entity) sender;
                        EntityTargetEvent e = (EntityTargetEvent) sender;
                        loc.getWorld().createExplosion(
                                entity.getLocation().getX(),
                                entity.getLocation().getY(),
                                entity.getLocation().getZ(),
                                100,
                                false,
                                false
                        );
                        loc.getWorld().spawnParticle(
                                Particle.EXPLOSION_LARGE,
                                loc,
                                500,
                                10,
                                10,
                                10,
                                0.1
                        );
                }
                return false;
        }

        @Override
        public void onDisable() {
                getLogger().info("無効化しました");
        }
}

