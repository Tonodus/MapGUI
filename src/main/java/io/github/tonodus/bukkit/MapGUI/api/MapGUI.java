package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */

/**
 * The base interface
 */
public interface MapGUI extends InputListenerController {
    /**
     * Sets the current displayed {@link Window window}
     *
     * @param window the window to be displayed
     */
    public void setWindow(Window window);

    /**
     * Returns the current displayed window
     *
     * @return the window
     */
    public Window getCurrentWindow();

    /**
     * Let the map gui redraw itself. Call this, when ever you changed something of the gui.
     */
    public void invalidate();

    public void show();

    public void hide();

    public boolean isVisible();

    /**
     * Call this to dispose the map and the memory it use, if it is visible it will be {@link #hide() hidden}
     */
    public void dispose();

    public void addDropListener(MapGUIStateListener listener);

    public void removeDropListener(MapGUIStateListener listener);
}
