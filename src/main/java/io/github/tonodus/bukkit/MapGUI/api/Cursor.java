package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */

/**
 * A cursor on a {@link MapGUI MapGUI} that the player can move with his mouse
 */
public interface Cursor {
    public int getX();

    public int getY();

    public Type getType();

    public void setType(Type newType);

    public enum Type {
        NO(100), WHITE_POINTER(0), GREEN_POINTER(1), RED_POINTER(2), BLUE_POINTER(3), WHITE_CROSS(4);
        byte value;

        Type(int v) {
            this.value = (byte) v;
        }
    }
}
