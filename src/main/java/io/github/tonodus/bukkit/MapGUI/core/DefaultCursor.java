package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Cursor;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
class DefaultCursor implements Cursor {
    byte mx, my;
    private int x, y;
    private Type type;

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
        if (x < 0 || x >= 128 || y < 0 || y >= 128)
            throw new IllegalArgumentException("Range for x|y is 0 up to 127! (x=" + x + "|y=" + y + ")");

        this.x = x;
        this.y = y;
        this.mx = (byte) ((x * 2) - 127);
        this.my = (byte) ((y * 2) - 127);
    }

    public byte getMapX() {
        return mx;
    }

    public byte getMapY() {
        return my;
    }
}
