package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.MapGUI;
import io.github.tonodus.bukkit.MapGUI.api.MapItemGetter;
import io.github.tonodus.bukkit.MapGUI.core.DefaultMapGui;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public class MapGuiHelper {
    public static MapGUI createGui(Plugin plugin) {
        return new DefaultMapGui(plugin);
    }

    public static MapGUI createGui(Plugin plugin, final ItemStack stack) {
        return new DefaultMapGui(plugin, new MapItemGetter() {
            @Override
            public ItemStack getMap() {
                return stack;
            }
        });
    }

    public static MapGUI createGui(Plugin plugin, MapItemGetter getter) {
        return new DefaultMapGui(plugin, getter);
    }
}
