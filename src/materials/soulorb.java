package materials;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import thirtyvirus.uber.UberMaterial;
import thirtyvirus.uber.helpers.*;

public class soulorb extends UberMaterial {

    public soulorb(Material material, String name, UberRarity rarity, boolean enchantGlint, boolean stackable, boolean isVanillaCraftable, String description, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, enchantGlint, stackable, isVanillaCraftable, description, craftingRecipe);
    }

    //Set material texture
    public void onItemStackCreate(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(3987002);
        item.setItemMeta(meta);
    }
}
