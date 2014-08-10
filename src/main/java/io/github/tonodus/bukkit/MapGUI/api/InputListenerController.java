package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public interface InputListenerController {
    public void addMouseListener(MouseListener listener);

    public void addScrollListener(MouseWheelListener listener);

    public void addInputListener(TextInputListener listener);

    public void removeMouseListener(MouseListener listener);

    public void removeScrollListener(MouseWheelListener listener);

    public void removeInputListener(TextInputListener listener);
}
