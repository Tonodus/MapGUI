package io.github.tonodus.bukkit.MapGUI.api;

import org.bukkit.entity.Player;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public class MapGUIs {
    private static final DropListener dontDrop = new DropListener() {
        @Override
        public boolean onPossibleDrop(MapGUI me, Player player) {
            return true;
        }

        @Override
        public void onPreDispose(MapGUI me, Player player) {

        }

        @Override
        public void onDispose(MapGUI me) {

        }
    };

    public static void forceStayVisible(MapGUI gui) {
        gui.addDropListener(dontDrop);
    }

    public static void unForceStayVisible(MapGUI gui) {
        gui.removeDropListener(dontDrop);
    }
}
