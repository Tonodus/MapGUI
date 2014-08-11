package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public interface InputListenerController<O> {
    public void addMouseListener(MouseListener<O> listener);

    public void addScrollListener(MouseWheelListener<O> listener);

    public void addInputListener(TextInputListener<O> listener);

    public void removeMouseListener(MouseListener<O> listener);

    public void removeScrollListener(MouseWheelListener<O> listener);

    public void removeInputListener(TextInputListener<O> listener);
}
