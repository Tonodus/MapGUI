package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Cursor;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
class DefaultCursor implements Cursor {
    private int x, y;
    private Type type;

    /*
        public DefaultCursor() {
            setSize(10, 10);
        }
    */
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type newType) {
        this.type = newType;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
/*
    @Override
    public void updateSync() {

    }

    @Override
    public void drawAsync(Graphics2D canvas) {
        canvas.drawOval(x, y, 10, 10);
    }*/
}
