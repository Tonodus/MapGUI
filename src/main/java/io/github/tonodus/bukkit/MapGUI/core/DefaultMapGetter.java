package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.MapItemGetter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
class DefaultMapGetter implements MapItemGetter {
    private final ItemStack map;

    public DefaultMapGetter() {
        map = new ItemStack(Material.MAP);
    }

    @Override
    public ItemStack getMap() {
        return map;
    }
}
