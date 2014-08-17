package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.MapItemModifier;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
class DefaultMapItemModifier implements MapItemModifier {
    @Override
    public ItemStack modifyItem(ItemStack before) {
        return before;
    }
}
