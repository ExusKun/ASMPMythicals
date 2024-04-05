package items;

import java.util.List;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;
import com.github.fierioziy.particlenativeapi.plugin.ParticleNativePlugin;
import main.UberItemsAddon;
import main.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.holoeasy.HoloEasy;
import org.holoeasy.pool.IHologramPool;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class soulrender extends UberItem {

    public UberItemsAddon main;
    private ParticleNativeAPI api = ParticleNativeCore.loadAPI(main.getPlugin());
    public Utils utils;

    private void toggleParticles(Player player) {

        new BukkitRunnable() {
            Location loc1 = player.getLocation();
            Location loc2 = player.getLocation();
            double t = 0;
            double r = 1;
            public void run() {
                t = t + Math.PI/3;
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
                if(t > Math.PI*4) this.cancel();
            }
        }.runTaskTimer(main.getPlugin(), 0, 1);
    }

    public soulrender(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(3987001);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD  + "" + ChatColor.MAGIC + "K" + ChatColor.DARK_PURPLE + " " + ChatColor.BOLD  + "Soulrender " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD  + "" + ChatColor.MAGIC + "K");
        item.setItemMeta(meta);
        Utilities.storeIntInItem(item, 10, "soulCountMax");
    }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) { }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean rightClickAirAction(Player player, ItemStack item) {
        if (Utilities.enforceCooldown(player, "soul-siphon-cooldown", 30, item, true)) { return false; }
        double soulCount = Utilities.getIntFromItem(player.getItemInHand(), "soulCount");
        if (soulCount == 0) {
            utils.sendActionBar(player,ChatColor.RED + "" + ChatColor.ITALIC + "Soulrender has no stored souls..");
            Utilities.playSound(ActionSound.ERROR, player);
            return false;
        }
        double soulCountMax = Utilities.getIntFromItem(player.getItemInHand(), "soulCountMax");

        //If 100% of souls are siphoned
        if(soulCount == soulCountMax)
        {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) (soulCount*60), 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (soulCount*70), 1));
            Utilities.storeIntInItem(player.getItemInHand(), 0, "soulCount");
            player.playSound(player.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 3.0F, 1.0F);
            toggleParticles(player);
        }
        //If 75% of souls are siphoned
        else if(soulCount >= soulCountMax * 0.75)
        {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) (soulCount*60), 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (soulCount*60), 0));
            Utilities.storeIntInItem(player.getItemInHand(), 0, "soulCount");
            player.playSound(player.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 2.0F, 1.0F);
            toggleParticles(player);
        }
        //If less than 75% of souls are siphoned
        else if(soulCount < soulCountMax * 0.75)
        {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) (soulCount*60), 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (soulCount*60), 0));
            Utilities.storeIntInItem(player.getItemInHand(), 0, "soulCount");
            player.playSound(player.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 1.0F, 1.0F);
            toggleParticles(player);
        }
        return true;
    }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean middleClickAction(Player player, ItemStack item) { return false; }
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) { return false; }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) {
        return false; }
}
