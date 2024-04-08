package items;

import java.util.List;
import events.CraftingEvent;
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
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.*;


public class soulrender extends UberItem {
    private UberItemsAddon main;
    private Utils utils;
    private CraftingEvent cE;

    public soulrender(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(3987001);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD  + "" + ChatColor.MAGIC + "K" + ChatColor.DARK_PURPLE + " " + ChatColor.BOLD  + "Soulrender " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD  + "" + ChatColor.MAGIC + "K");
        item.setItemMeta(meta);
        Utilities.storeIntInItem(item, 10, "soulCountMax");
        Utilities.storeIntInItem(item, 1, "craftingEvent");
        Utilities.storeIntInItem(item, 1800, "craftingDuration");
        onCraft(item);
    }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) { }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean rightClickAirAction(Player player, ItemStack item) {
        if (Utilities.getIntFromItem(item, "soulCount") >= 1 && Utilities.enforceCooldown(player, "soul-siphon-cooldown", 30, item, true)) { return false; }
        return siphonAbility(player, item);
    }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) {
        if (Utilities.getIntFromItem(item, "soulCount") > 0 && Utilities.enforceCooldown(player, "soul-siphon-cooldown", 30, item, true)) { return false; }
        return siphonAbility(player, item);
    }
    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean middleClickAction(Player player, ItemStack item) { return false; }
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) { return false; }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) {
            main.getPlugin().getLogger().info("Inventory " + event.getClickedInventory().getHolder().toString());
        return false;
    }
    public boolean activeEffect(Player player, ItemStack item) { return false; }
    private boolean siphonAbility(Player player, ItemStack item) {
        double soulCount = Utilities.getIntFromItem(player.getItemInHand(), "soulCount");
        double soulCountMax = Utilities.getIntFromItem(player.getItemInHand(), "soulCountMax");
        if (soulCount == 0) {
            utils.sendActionBar(player,ChatColor.RED + "" + ChatColor.ITALIC + "Soulrender has no stored souls..");
            Utilities.playSound(ActionSound.ERROR, player);
            return false;
        }
        //If 100% of souls are siphoned
        if(soulCount == soulCountMax)
        {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) (soulCount*60), 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (soulCount*70), 1));
            Utilities.storeIntInItem(player.getItemInHand(), 0, "soulCount");
            player.playSound(player.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 3.0F, 1.0F);
            utils.helixParticles(player, player.getLocation(), 3, 4);
            return false;
        }
        //If 75% of souls are siphoned
        else if(soulCount >= soulCountMax * 0.75)
        {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) (soulCount*60), 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (soulCount*60), 0));
            Utilities.storeIntInItem(player.getItemInHand(), 0, "soulCount");
            player.playSound(player.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 2.0F, 1.0F);
            utils.helixParticles(player, player.getLocation(), 3, 4);
            return false;
        }
        //If less than 75% of souls are siphoned
        else if(soulCount < soulCountMax * 0.75)
        {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) (soulCount*60), 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (soulCount*60), 0));
            Utilities.storeIntInItem(player.getItemInHand(), 0, "soulCount");
            player.playSound(player.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 1.0F, 1.0F);
            utils.helixParticles(player, player.getLocation(), 3, 4);
            return false;
        }
        return false;
    }

    private void onCraft(ItemStack item) {
        new BukkitRunnable() {
            List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
            @Override
            public void run() {
                for(int i = 0; i < players.size(); i++) {
                    for(ItemStack invItem : players.get(i).getInventory().getContents()) {
                        if(invItem != null && invItem.getItemMeta().hasCustomModelData()
                                && invItem.getItemMeta().getCustomModelData() == item.getItemMeta().getCustomModelData()) {
                            int craftEvent = Utilities.getIntFromItem(invItem,"craftingEvent");
                            if(craftEvent >= 1)
                            {
                                main.getPlugin().getLogger().info("Found player: " + players.get(i).getDisplayName() + " Inventory Size: " + players.get(i).getInventory().getSize() + " Crafting Event Toggle: " + craftEvent );
                                Utilities.storeIntInItem(invItem,0, "craftingEvent");
                                players.get(i).getInventory().removeItem(invItem);
                                cE.craftEvent(players.get(i), players.get(i).getLocation().add(0,2,0),invItem, Utilities.getIntFromItem(invItem,"craftingDuration"));
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(main.getPlugin(), 1, 1);
        return;
    }
}


