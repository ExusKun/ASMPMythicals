package main;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Utils {

    private static UberItemsAddon main;

    public static void sendActionBar(Player p, String msg) { p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg)); }

    public static void sendTitle(Player p, String msg, String sub, int fadeIn, int stay, int fadeOut) { p.sendTitle(msg, sub, fadeIn, stay, fadeOut); }

    public static void announceCraftEvent(String msg, String sub, int fadeIn, int stay, int fadeOut) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(msg, sub, fadeIn, stay, fadeOut);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
            main.getPlugin().getLogger().info("Announced " + msg + " at " + sub);
            p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + ChatColor.ITALIC + "ANCHOR" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + ChatColor.ITALIC + "SMP" + ChatColor.GRAY + " > " + "A " + msg + ChatColor.GRAY + " has begun!");
            p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + ChatColor.ITALIC + "ANCHOR" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + ChatColor.ITALIC + "SMP" + ChatColor.GRAY + " > " + sub);
        }
    }

    public static ParticleNativeAPI api = ParticleNativeCore.loadAPI(main.getPlugin());

    public static void helixParticles(Player player, Location location, double speed, double time) {
        new BukkitRunnable() {
            Location loc1 = location;
            Location loc2 = location;
            double t = 0;
            double r = 1;
            public void run() {
                t = t + Math.PI/speed;
                double x1 = r*cos(t);
                double y = t/2;
                double z1 = r*sin(t);
                double z2 = r*cos(t);
                double x2 = r*sin(t);
                loc1.add(x1, y, z1);
                loc2.add(x2, y, z2);
                api.LIST_1_8.SPELL_MOB.packetColored(true, loc1, Color.PURPLE).sendTo(player);
                api.LIST_1_8.SPELL_MOB.packetColored(true, loc2, Color.PURPLE).sendTo(player);
                loc1.subtract(x1, y, z1);
                loc2.subtract(x2, y, z2);
                if(t > Math.PI*time) this.cancel();
            }
        }.runTaskTimer(main.getPlugin(), 0, 1);
    }

    public static void randomParticles(Player player, double time, int tick, int speed) {
        new BukkitRunnable() {
            Location loc1 = player.getLocation();
            Location loc2 = player.getLocation();
            int localTime = 0;
            double t = 0;
            double r = 1;
            public void run() {
                localTime++;
                t = t + Math.PI/speed;
                double x1 = r*cos(t);
                double y = 2;
                double z1 = r*sin(t);
                double z2 = r*cos(t);
                double x2 = r*sin(t);
                loc1.add(x1, y, z1);
                loc2.add(x2, y, z2);
                api.LIST_1_8.PORTAL.packet(true, loc1).sendTo(player);
                api.LIST_1_8.ENCHANTMENT_TABLE.packet(true, loc2).sendTo(player);
                loc1.subtract(x1, y, z1);
                loc2.subtract(x2, y, z2);
                if(localTime >= time) this.cancel();
            }
        }.runTaskTimer(main.getPlugin(), 0, tick);
    }

}
