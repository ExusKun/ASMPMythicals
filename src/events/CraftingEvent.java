package events;

import main.UberItemsAddon;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.holoeasy.HoloEasy;
import org.holoeasy.config.HologramKey;
import org.holoeasy.pool.IHologramPool;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.holoeasy.builder.HologramBuilder.*;

public class CraftingEvent {

    private UberItemsAddon main;
    private IHologramPool pool = HoloEasy.startInteractivePool(main.getPlugin(), 60, 0f, 0f);

    public void craftEvent(Player player, Location location, ItemStack item, long tTime) {
        String holoID = player.getUniqueId() + "-" + LocalDateTime.now() + "-" + item.getItemMeta().getCustomModelData();
        ItemMeta meta = item.getItemMeta();
        var holo = hologram(new HologramKey(pool, holoID), location, () -> {
            textline(meta.getDisplayName());
            textline("Time Remaining: ");
            item(item);
        }); //edit this within runnable below
        new BukkitRunnable() {
            long totalTime = tTime;
            long hours = TimeUnit.SECONDS.toHours(totalTime);
            long minutes = TimeUnit.SECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(totalTime));
            long seconds = TimeUnit.SECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalTime));

            @Override
            public void run() {
                totalTime--;
                long hours = TimeUnit.SECONDS.toHours(totalTime);
                long minutes = TimeUnit.SECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(totalTime));
                long seconds = TimeUnit.SECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalTime));
                //edit hologram
                if(totalTime <= 0) this.cancel();
            }
        }.runTaskTimer(main.getPlugin(),0,20);
    }

}
