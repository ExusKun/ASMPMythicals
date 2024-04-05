package events;

import main.Utils;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import thirtyvirus.uber.helpers.Utilities;

public class DeathListener implements Listener {

    public Utils utils;

    @EventHandler
    private void entityDeathEvent(EntityDeathEvent e)
    {
        LivingEntity monster = e.getEntity();
        //Soulrender
        if(monster.getKiller() != null) {
            if(monster.getKiller() instanceof Player) {
                Player player = monster.getKiller();
                if(player.getItemInHand().getItemMeta().getCustomModelData() == 3987001) {
                    int soulCount = Utilities.getIntFromItem(player.getItemInHand(), "soulCount");
                    int soulCountMax = Utilities.getIntFromItem(player.getItemInHand(), "soulCountMax");
                    int rand = (int)(Math.random() * 10) + 1;
                    if(rand >= 7) {
                        player.playSound(player.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1.0F, 1.0F);
                        if(soulCount < soulCountMax)
                        {
                            soulCount++;
                            Utilities.storeIntInItem(player.getItemInHand(), soulCount, "soulCount");
                            utils.sendActionBar(player, ChatColor.GRAY + "" + ChatColor.ITALIC + "Soulrender consumes a soul.." + ChatColor.DARK_GRAY + " (" + soulCount + "/" + soulCountMax + ")");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void playerDeathEvent(PlayerDeathEvent e){
        Player player = e.getEntity();
        if(!(player.getKiller() == null)) {
            if(player.getItemInHand().getItemMeta().getCustomModelData() == 3987001) {
                int soulCount = Utilities.getIntFromItem(player.getItemInHand(), "soulCount");
                int soulCountMax = Utilities.getIntFromItem(player.getItemInHand(), "soulCountMax");
                int rand = (int)(Math.random() * 10) + 1;
                if(rand >= 7) {
                    player.playSound(player.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 1.0F, 1.0F);
                    if(soulCount < soulCountMax)
                    {
                        soulCount++;
                        Utilities.storeIntInItem(player.getItemInHand(), soulCount, "soulCount");
                        utils.sendActionBar(player, ChatColor.GRAY + "" + ChatColor.ITALIC + "Soulrender consumes a soul.." + ChatColor.DARK_GRAY + " (" + soulCount + "/" + soulCountMax + ")");
                    }
                }
            }
        }
    }

}
