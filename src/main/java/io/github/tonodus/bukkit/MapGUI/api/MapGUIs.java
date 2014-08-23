package io.github.tonodus.bukkit.MapGUI.api;

import org.bukkit.entity.Player;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public class MapGUIs {
    private static final MapGUIStateListener dontDrop = new MapGUIStateListenerAdapter() {
        @Override
        public boolean shouldBlockDrop(MapGUI me, Player who) {
            return true;
        }
    };

    public static void forceStayVisible(MapGUI gui) {
        gui.addStateListener(dontDrop);
    }

    public static void unForceStayVisible(MapGUI gui) {
        gui.removeDropListener(dontDrop);
    }
}
