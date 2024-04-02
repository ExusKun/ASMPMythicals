package items;

import java.util.List;

//import hm.zelha.particlesfx.particles.ParticleCrit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.*;

public class soulrender extends UberItem {

    public soulrender(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(3987001);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD  + "" + ChatColor.MAGIC + "K" + ChatColor.DARK_PURPLE + " " + ChatColor.BOLD  + "Soulrender " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD  + "" + ChatColor.MAGIC + "K");
        item.setItemMeta(meta);
    }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) { }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean rightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean middleClickAction(Player player, ItemStack item) { return false; }
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item)
    {
        if(target.getWorld() == player.getWorld())
        {
            int rand = (int)(Math.random() * 10) + 1;
            if(rand >= 6)
            {
                // new ParticleCrit(new Location(target.getWorld(), target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ()))
                //         .display(player.getLocation());
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "The Soulrender consumes a soul..");
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 0));
            }
        }
        return false;
    }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) {

        return false;
    }
}
