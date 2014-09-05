package io.github.tonodus.bukkit.MapGUI.core;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.plugin.Plugin;

import java.awt.image.BufferedImage;

/**
 * Created by Tonodus (http://tonodus.github.io) on 03.09.2014.
 */
public class ItemFrameGUI extends AbstractMapGUI {
    private ItemFrame bukkitEntity;
    private Location location;
    private short mapId;
    private ItemFrameDrawHelper helper;

    ItemFrameGUI(Plugin plugin, Location location, short mapId, WorkerThread worker) {
        super(plugin, new DefaultInputController(), null);
        this.location = location;
        this.mapId = mapId;
    }

    private static DrawHelper getDrawHelper(ItemFrameGUI g) {
        return null;
    }

    @Override
    public void show() {
        super.show();
        bukkitEntity = (ItemFrame) location.getWorld().spawnEntity(location, EntityType.ITEM_FRAME);
        ItemStack mapItem = new ItemStack(Material.MAP);
        mapItem.setDurability(mapId);
        bukkitEntity.setItem(mapItem);
    }

    @Override
    public void hide() {
        super.hide();
        bukkitEntity.remove();
        bukkitEntity = null;
    }

    @Override
    public void dispose() {
        super.dispose();
        location = null;
    }

    private static class ItemFrameDrawHelper extends AbstractDrawHelper {
        public Entity bukkitEntity;

        public ItemFrameDrawHelper(int width, int height, WorkerThread worker) {
            super(width, height, worker);
        }

        @Override
        protected void progressImageAndSend(BufferedImage image, MapCanvas on) {
            on.drawImage(0, 0, image);

            for (Entity e : bukkitEntity.getNearbyEntities(20, 20, 20))
                if (e instanceof Player)
                    ((Player) e).sendMap(on.getMapView());
        }
    }
}
