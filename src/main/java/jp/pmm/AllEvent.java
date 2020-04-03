package jp.pmm;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AllEvent implements Listener {
    @EventHandler
    public void blocklog(BlockPlaceEvent e) {
        Block block = e.getBlock();
        StringBuilder builder = new StringBuilder("BlockPlaceEventが発生：");
        builder.append("設置した物=").append(block.toString()).append(" ");
        Bukkit.getLogger().info(builder.toString());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeathEvent(PlayerDeathEvent d) throws  InterruptedException {
        Player player = d.getEntity().getPlayer();
        Location location = d.getEntity().getLocation();
        player.setHealth(1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 20 ,5));
        player.teleport(new Location(location.getWorld(),206,247,206));
        player.sendTitle(ChatColor.GREEN + "回復なう", ChatColor.RED + "5秒まて",10,100,120);
        player.wait(2);
        player.teleport(location.getWorld().getSpawnLocation());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractEvent(PlayerInteractEvent e) throws InterruptedException {
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        Location location = player.getLocation();
        Action action = e.getAction();
        Entity entity;
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (itemStack.getType() == Material.WOOD_SWORD) {
                location.getWorld().strikeLightningEffect(location);
                location.getWorld().createExplosion(
                        location.getX(),
                        location.getY(),
                        location.getZ(),
                        10,
                        false,
                        false
                );
            }
            if (itemStack.getType() == Material.STONE_SWORD) {
                player.setAllowFlight(true);
                wait(5);
                player.setAllowFlight(false);
                return;
            }
            if (itemStack.getType() == Material.GOLD_SPADE) {

                return;
            }
        }
    }
    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent d) {
        d.setCancelled(true);
    }
    @EventHandler
    public void onCraftItemEvent(CraftItemEvent c) {
        return;
    }
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent j) {
        j.setJoinMessage(ChatColor.YELLOW + j.getPlayer().getName() + "がRPGandGUNにやってきました");
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)item.getItemMeta();
        meta.setAuthor("Admin");
        meta.setDisplayName("ようこそRPGandGUNへ！ (サーバールールブック)");
        meta.addPage(new String[]{
                // 1ページ目
                "ようこそRPGandGUNへ！\n" +
                        "\n" +
                        "この本はこのサーバーのルールを説明する本です。",
                // 2ページ目
                "★ルール★\n" +
                        "\n" +
                        "§c§l荒らし厳禁！§r\n" +
                        "みんな仲良く！\n" +
                        "何かあったら管理人まで"});
        item.setItemMeta(meta);
        j.getPlayer().getInventory().addItem(item);
    }
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent q) {
        q.setQuitMessage(ChatColor.YELLOW + q.getPlayer().getName() + "がログアウトしました。");
    }
    @EventHandler
    public void onPlayerEggThrowEvent(PlayerEggThrowEvent e) {
        e.setHatching(false);
    }

}