package io.github.tonodus.bukkit.MapGUI.api;

import org.bukkit.inventory.ItemStack;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public interface MapItemModifier {
    public ItemStack modifyItem(ItemStack defaultItemStack);
}
