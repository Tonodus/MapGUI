package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */

/**
 * A listener for text input threw a player
 */
public interface TextInputListener {
    /**
     * Called, when a player inputs text
     *
     * @param text The text the player typed
     */
    public void onTextInput(String text);
}
