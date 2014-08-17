package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.MapGUIPlugin;
import io.github.tonodus.bukkit.MapGUI.api.MapGUI;
import io.github.tonodus.bukkit.MapGUI.api.MapGUIStateListenerAdapter;
import io.github.tonodus.bukkit.MapGUI.api.SinglePlayerMapGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public class MapGuiCollection {
    private WorkerThread w;
    private MapGUIPlugin mainPlugin;

    private Collection<DefaultMapGui> guis;

    /**
     * @hide
     */
    public MapGuiCollection(WorkerThread thread, MapGUIPlugin mainPlugin) {
        this.w = thread;
        this.mainPlugin = mainPlugin;
        this.guis = new ArrayList<DefaultMapGui>();
    }

    public void onEnable() {

    }

    public void onDisable() {
        for (DefaultMapGui gui : guis) {
            if (gui.isVisible())
                gui.hide();
            gui.dispose();
        }
    }

    public SinglePlayerMapGUI registerMapGuiForPlayer(Plugin yourPlugin, Player player) {
        final DefaultMapGui gui = new DefaultMapGui(yourPlugin, player, w);
        gui.addDropListener(new MapGUIStateListenerAdapter() {
            @Override
            public void onDispose(final MapGUI me) {
                Bukkit.getScheduler().runTask(mainPlugin, new Runnable() {
                    @Override
                    public void run() {
                        guis.remove(me);
                    }
                });
            }
        });
        guis.add(gui);
        return gui;
    }
}
