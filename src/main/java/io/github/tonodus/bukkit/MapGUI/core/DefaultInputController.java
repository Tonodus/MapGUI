package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.InputListenerController;
import io.github.tonodus.bukkit.MapGUI.api.MouseListener;
import io.github.tonodus.bukkit.MapGUI.api.MouseWheelListener;
import io.github.tonodus.bukkit.MapGUI.api.TextInputListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
class DefaultInputController implements InputListenerController, TextInputListener, MouseListener, MouseWheelListener {
    private Collection<MouseListener> m;
    private Collection<MouseWheelListener> w;
    private Collection<TextInputListener> t;

    DefaultInputController() {
        m = new ArrayList<MouseListener>();
        w = new ArrayList<MouseWheelListener>();
        t = new ArrayList<TextInputListener>();
    }

    @Override
    public void onLeftClick(boolean shift) {
        for (MouseListener l : m)
            l.onLeftClick(shift);
    }

    @Override
    public void onRightClick(boolean shift) {
        for (MouseListener l : m) {
            l.onRightClick(shift);
            l.onClick(shift ? MouseListener.TypeOfClick.SHIFT_RIGHT : MouseListener.TypeOfClick.RIGHT);
        }
    }

    @Override
    public void onMove(int oldX, int oldY) {
        for (MouseListener l : m)
            l.onMove(oldX, oldY);
    }

    @Override
    public void onMouseWheel(int amount) {
        for (MouseWheelListener l : w)
            l.onMouseWheel(amount);
    }

    @Override
    public void onTextInput(String text) {
        for (TextInputListener l : t)
            l.onTextInput(text);
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        m.add(listener);
    }

    @Override
    public void addScrollListener(MouseWheelListener listener) {
        w.add(listener);
    }

    @Override
    public void addInputListener(TextInputListener listener) {
        t.add(listener);
    }

    @Override
    public void removeMouseListener(MouseListener listener) {
        m.remove(listener);
    }

    @Override
    public void removeScrollListener(MouseWheelListener listener) {
        w.remove(listener);
    }

    @Override
    public void removeInputListener(TextInputListener listener) {
        t.remove(listener);
    }

    @Override
    public void onClick(TypeOfClick type) {

    }
}
