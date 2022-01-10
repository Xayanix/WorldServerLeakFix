package pl.xayanix.nssv.worldserver;

import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/*
    created by: Xayanix at 2022-01-08 00:34
*/
public class WorldServerFix extends JavaPlugin implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onUnload(WorldUnloadEvent worldUnloadEvent) {
        CraftWorld craftWorld = (CraftWorld) worldUnloadEvent.getWorld();
        WorldServer worldServer = craftWorld.getHandle();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            Arrays.stream(worldServer.getClass().getDeclaredFields()).forEach(field -> {
                try {
                    field.setAccessible(true);
                    field.set(worldServer, null);
                } catch (Exception ignored) { }
            });

            Arrays.stream(worldServer.getClass().getSuperclass().getDeclaredFields()).forEach(field -> {
                try {
                    field.setAccessible(true);
                    field.set(worldServer, null);
                } catch (Exception ignored) { }
            });
        }, 20 * 60);


    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

}
