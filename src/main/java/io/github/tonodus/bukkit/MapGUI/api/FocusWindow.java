package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
public interface FocusWindow extends Window {
    Component getFocused();

    void setFocused(Component component);
}
