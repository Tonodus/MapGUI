package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */

import java.awt.*;

/**
 * Can be displayed on a {@link MapGUI MapGUI}
 */
public interface Window extends InputListenerController<Window>, Drawable {
    /**
     * Called whenever the window need to redraw itself and it's content.
     * If you want the window to redraw itself, call {@link MapGUI#invalidate() invalidata() on the MapGUI}
     *
     * @param canvas
     */
    void draw(Graphics canvas);

    /**
     * @hide
     */
    void attachedOn(MapGUI gui);

    /**
     * @hide
     */
    void detachedFrom(MapGUI gui);
}
