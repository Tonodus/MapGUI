package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */

import org.bukkit.map.MapCursor;

/**
 * A cursor on a {@link MapGUI MapGUI} that the player can move with his mouse
 */
public interface Cursor {
    public int getX();

    public int getY();

    public CursorType getType();

    public void setType(CursorType newType);

    void set(int newX, int newY);

    public enum CursorType {
        NO(null), WHITE_POINTER(MapCursor.Type.WHITE_POINTER), GREEN_POINTER(MapCursor.Type.GREEN_POINTER),
        RED_POINTER(MapCursor.Type.RED_POINTER), BLUE_POINTER(MapCursor.Type.BLUE_POINTER), WHITE_CROSS(MapCursor.Type.WHITE_CROSS);
        private MapCursor.Type value;

        private CursorType(MapCursor.Type v) {
            this.value = v;
        }

        public void applyOn(MapCursor cursor) {
            switch (this) {
                case NO:
                    cursor.setVisible(false);
                    break;
                default:
                    cursor.setVisible(true);
                    cursor.setType(this.value);
            }
        }
    }
}
