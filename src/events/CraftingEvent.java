package events;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import main.UberItemsAddon;
import main.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import thirtyvirus.uber.helpers.Utilities;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class CraftingEvent {

    private static UberItemsAddon main;
    private static Utils util;

    public static void craftEvent(Player player, Location location, ItemStack item, long tTime) {
        ItemStack invItem = item;
        Utilities.storeIntInItem(invItem, 0, "craftingEvent");

        Location loc = location.add(0, 1f, 0);
        String holoID = player.getUniqueId() + "-" + LocalDateTime.now().toString().replace(":", "_").replace(".", "_") + "-" + invItem.getItemMeta().getCustomModelData();
        ItemMeta meta = invItem.getItemMeta();//edit this within runnable below
        DHAPI.createHologram(holoID, loc);
        Hologram holo = DHAPI.getHologram(holoID);
        DHAPI.addHologramLine(holo, invItem);
        DHAPI.addHologramLine(holo, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + ChatColor.ITALIC + "MYTHICAL RITUAL");
        DHAPI.addHologramLine(holo, meta.getDisplayName());
        DHAPI.addHologramLine(holo, "Time Remaining: ");
        util.announceCraftEvent(meta.getDisplayName() + ChatColor.LIGHT_PURPLE + " " + ChatColor.BOLD + "RITUAL",
                "X: " + Math.round(loc.getX()) + " Y: " + Math.round(loc.getY()) + " Z: " + Math.round(loc.getZ()), 20, 100, 20);
        util.randomParticles(player, tTime, 20, 1);
        util.helixParticles(player, loc.subtract(0, 1f, 0), 3, 5);
        ItemStack finalInvItem = invItem;
        new BukkitRunnable() {
            long totalTime = tTime;

            @Override
            public void run() {
                totalTime--;
                long hours = TimeUnit.SECONDS.toHours(totalTime);
                long minutes = TimeUnit.SECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(totalTime));
                long seconds = TimeUnit.SECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalTime));
                DHAPI.setHologramLine(holo, 3, ChatColor.RED + "Time Remaining: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                if (totalTime <= 0) {
                    loc.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_DEATH, 5f, 5f);
                    DHAPI.removeHologram(holoID);
                    util.api.LIST_1_13.FIREWORK.packetMotion(true, loc, 0.2D, 3D, 0D).sendTo(player);
                    util.api.LIST_1_13.FIREWORK.packetMotion(true, loc, 0.4D, 3D, 0D).sendTo(player);
                    util.api.LIST_1_13.FIREWORK.packetMotion(true, loc, 0D, 3D, 0.2D).sendTo(player);
                    util.api.LIST_1_13.FIREWORK.packetMotion(true, loc, 0D, 3D, 0.4D).sendTo(player);
                    util.api.LIST_1_13.FIREWORK.packetMotion(true, loc, -0.2D, 3D, 0D).sendTo(player);
                    util.api.LIST_1_13.FIREWORK.packetMotion(true, loc, -0.4D, 3D, 0D).sendTo(player);
                    util.api.LIST_1_13.FIREWORK.packetMotion(true, loc, 0D, 3D, -0.2D).sendTo(player);
                    util.api.LIST_1_13.FIREWORK.packetMotion(true, loc, 0D, 3D, -0.4D).sendTo(player);
                    util.api.LIST_1_13.FIREWORK.packetMotion(true, loc, 0D, 3D, 0D).sendTo(player);
                    loc.getWorld().dropItemNaturally(loc, finalInvItem);
                    this.cancel();
                }
            }
        }.runTaskTimer(main.getPlugin(), 0, 20);
    }
}
