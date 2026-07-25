package utroll.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import utroll.troll.AllahAkbarTroll;

import java.lang.reflect.Field;
import java.util.List;

public class TNTListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof TNTPrimed) {
            try {
                Field field = AllahAkbarTroll.class.getDeclaredField("allTNT");
                field.setAccessible(true);
                List<?> allTNT = (List<?>) field.get(null);
                
                if (allTNT.contains(entity)) {
                    event.blockList().clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
