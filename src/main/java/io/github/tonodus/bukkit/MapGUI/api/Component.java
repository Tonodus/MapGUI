package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public interface Component extends MouseListener, TextInputListener, MouseWheelListener, InputListenerController, Drawable {
    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();

    void onLostFocus();

    void onFocus();
}
