package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */

import java.awt.*;

/**
 * Can be displayed on a {@link MapGUI MapGUI}
 */
public interface Window extends InputListenerController, Drawable {
    /**
     * Called whenever the window needs to redraw itself and it's content.
     * Should only called from a MapGUI
     * If you want the window to redraw itself, call {@link #invalidate() invalidate()}
     *
     * @param canvas the canvas the window is drawn on
     */
    void drawAsync(Graphics2D canvas);

    /**
     * @hide
     */
    void attachedOn(MapGUI gui);

    /**
     * @hide
     */
    void detachedFrom(MapGUI gui);

    /**
     * Let the window re-draw it content at the next possible moment
     */
    void invalidate();
}
