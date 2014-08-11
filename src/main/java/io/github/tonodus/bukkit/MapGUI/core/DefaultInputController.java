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
abstract class DefaultInputController<I, O> implements InputListenerController<O>, TextInputListener<I>, MouseListener<I>, MouseWheelListener<I> {
    private Collection<MouseListener<O>> m;
    private Collection<MouseWheelListener<O>> w;
    private Collection<TextInputListener<O>> t;

    DefaultInputController() {
        m = new ArrayList<MouseListener<O>>();
        w = new ArrayList<MouseWheelListener<O>>();
        t = new ArrayList<TextInputListener<O>>();
    }

    protected abstract O convert(I input);

    @Override
    public void onLeftClick(int x, int y, boolean shift, I owner) {
        for (MouseListener<O> l : m)
            l.onLeftClick(x, y, shift, convert(owner));
    }

    @Override
    public void onRightClick(int x, int y, boolean shift, I owner) {
        for (MouseListener<O> l : m)
            l.onRightClick(x, y, shift, convert(owner));
    }

    @Override
    public void onMove(int oldX, int oldY, int newX, int newY, I owner) {
        for (MouseListener<O> l : m)
            l.onMove(oldX, oldY, newX, newY, convert(owner));
    }

    @Override
    public void onMouseWheel(int amount, I owner) {
        for (MouseWheelListener<O> l : w)
            l.onMouseWheel(amount, convert(owner));
    }

    @Override
    public void onTextInput(String text, I owner) {
        for (TextInputListener<O> l : t)
            l.onTextInput(text, convert(owner));
    }

    @Override
    public void addMouseListener(MouseListener<O> listener) {
        m.add(listener);
    }

    @Override
    public void addScrollListener(MouseWheelListener<O> listener) {
        w.add(listener);
    }

    @Override
    public void addInputListener(TextInputListener<O> listener) {
        t.add(listener);
    }

    @Override
    public void removeMouseListener(MouseListener<O> listener) {
        m.remove(listener);
    }

    @Override
    public void removeScrollListener(MouseWheelListener<O> listener) {
        w.remove(listener);
    }

    @Override
    public void removeInputListener(TextInputListener<O> listener) {
        t.remove(listener);
    }
}
