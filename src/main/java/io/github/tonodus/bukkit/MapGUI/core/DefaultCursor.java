package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Cursor;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
class DefaultCursor implements Cursor {
    byte mx, my;
    private int x, y;
    private CursorType type = CursorType.WHITE_POINTER;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public CursorType getType() {
        return type;
    }

    @Override
    public void setType(CursorType newType) {
        this.type = newType;
    }

    @Override
    public void set(int x, int y) {
        if (x < 0 || x >= 128 || y < 0 || y >= 128)
            throw new IllegalArgumentException("Range for x|y is 0 up to 127! (x=" + x + "|y=" + y + ")");

        this.x = x;
        this.y = y;

        int cx = type.getClickPoint()[0], cy = type.getClickPoint()[1];
        this.mx = (byte) (((Math.min(x, 127 + cx) * 2) - 127) - cx);
        this.my = (byte) (((Math.min(y, 127 + cy) * 2) - 127) - cy);
    }

    public byte getMapX() {
        return mx;
    }

    public byte getMapY() {
        return my;
    }
}
