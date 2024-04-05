package main;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;
import com.github.fierioziy.particlenativeapi.plugin.ParticleNativePlugin;
import events.DeathListener;
import events.ProjectileHit;
import items.soulrender;
import materials.dragonbone;
import materials.soulorb;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.UberMaterial;
import thirtyvirus.uber.helpers.AbilityType;
import thirtyvirus.uber.helpers.UberAbility;
import thirtyvirus.uber.helpers.UberCraftingRecipe;
import thirtyvirus.uber.helpers.UberRarity;

import java.util.Arrays;

public class UberItemsAddon extends JavaPlugin {

    private static UberItemsAddon plugin;
    public static UberItemsAddon getPlugin()
    {
        return plugin;
    }

    public void onEnable() {
        plugin = this;
        // enforce UberItems dependancy
        if (Bukkit.getPluginManager().getPlugin("UberItems") == null) {
            this.getLogger().severe("UberItems Addons requires UberItems! disabled because UberItems dependency not found");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // register events and UberItems
        registerEvents();
        registerUberMaterials();
        registerUberItems();

        // post confirmation in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been enabled");
    }
    public void onDisable() {
        // posts exit message in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been disabled");
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new ProjectileHit(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }

    // NEW UBER ITEM CHECKLIST

    // - make a new class file, named with all lowercase lettering and underscores for spaces
    // - copy the UberItemTemplate class contents into the new class, extend UberItem
    // - make a putItem entry, follow the format of previous items and make sure to give a unique id
    // - write the unique item ability code in the appropriate method

    // - add the following line of code just after executing the item's ability:
    //      onItemUse(player, item); // confirm that the item's ability has been successfully used

    // - if the ability needs a cooldown, prefix it's code with a variation of the following line of code:
    //      if (!Utilities.enforceCooldown(getMain(), player, "name", 1, item, true)) return;

    // - if the item needs work done on create (like adding enchantments, adding other data) refer to onItemStackCreate
    // - if the item needs a prefix or suffix in its description,
    //   refer to the getSpecificLorePrefix and getSpecificLoreSuffix functions, then add the following:
    //      lore.add(ChatColor.RESET + "text goes here");

    // - if you need to store & retrieve ints and strings from items, you can use the following functions:
    //      Utilities.storeIntInItem(getMain(), item, 1, "number tag");
    //      if (Utilities.getIntFromItem(getMain(), item, "number tag") == 1) // { blah blah blah }
    //      (the same case for strings, just storeStringInItem and getStringFromItem)

    private void registerUberItems() {
        UberItems.putItem("Soulrender", new soulrender(Material.NETHERITE_SWORD, "Soulrender",
                UberRarity.MYTHIC, false, false, true,
                Arrays.asList(
                        new UberAbility("Soul Harvest", AbilityType.RIGHT_CLICK, "The Soulrender siphons its stored souls, granting the wielder temporary buffs that increased speed and strength.")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.NETHERITE_INGOT),
                        UberItems.getMaterial("soul_orb").makeItem(1),
                        new ItemStack(Material.NETHERITE_INGOT),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("dragon_bone").makeItem(1),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("dragon_bone").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR)), false, 1)));
    }
    private void registerUberMaterials() {
        UberItems.putMaterial("dragon_bone", new dragonbone(Material.BONE,
                "Dragon Bone", UberRarity.EPIC, true, false, false,
                "" + ChatColor.GRAY + "" + ChatColor.ITALIC + "A formidable relic harvested from the remains of the mighty " + ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "Ender Dragon" + ChatColor.GRAY + ".",
                null));

        UberItems.putMaterial("soulsand_enchanted", new UberMaterial(Material.SOUL_SAND,
                "Soulsand (Enchanted)", UberRarity.UNCOMMON  , true, false, false,
                "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.SOUL_SAND, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.SOUL_SAND, 32),
                        new ItemStack(Material.SOUL_SAND, 32),
                        new ItemStack(Material.SOUL_SAND, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.SOUL_SAND, 32),
                        new ItemStack(Material.AIR)), false, 1)));

        UberItems.putMaterial("soul_orb", new soulorb(Material.ENDER_PEARL,
                "Soul Orb", UberRarity.UNCOMMON  , true, false, false,
                "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.PLAYER_HEAD),
                        new ItemStack(Material.GHAST_TEAR),
                        new ItemStack(Material.PLAYER_HEAD),
                        new ItemStack(Material.GHAST_TEAR),
                        UberItems.getMaterial("soulsand_enchanted").makeItem(1),
                        new ItemStack(Material.GHAST_TEAR),
                        new ItemStack(Material.PLAYER_HEAD),
                        new ItemStack(Material.GHAST_TEAR),
                        new ItemStack(Material.PLAYER_HEAD)), false, 1)));
    }
}