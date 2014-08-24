package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.MapGUIPlugin;
import io.github.tonodus.bukkit.MapGUI.api.MapGUI;
import io.github.tonodus.bukkit.MapGUI.api.MapGUIStateListenerAdapter;
import io.github.tonodus.bukkit.MapGUI.api.PlayerMapGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public class MapGuiCollection {
    private WorkerThread w;
    private MapGUIPlugin mainPlugin;

    private Collection<MapGUI> guis;
    private Collection<MapGUI> toRemove;
    private BukkitRunnable checkTask = new BukkitRunnable() {
        @Override
        public void run() {
            check();
        }
    };

    /**
     * @hide
     */
    public MapGuiCollection(WorkerThread thread, MapGUIPlugin mainPlugin) {
        this.w = thread;
        this.mainPlugin = mainPlugin;
        this.guis = new ArrayList<MapGUI>();
        this.toRemove = new ArrayList<MapGUI>();
    }

    public void onEnable() {

    }

    public void onDisable() {
        check();
        for (MapGUI gui : guis)
            gui.dispose();
        check();
    }

    public PlayerMapGUI registerMapGuiForPlayer(Plugin yourPlugin, Player player) {
        final DefaultPlayerMapGUI gui = new DefaultPlayerMapGUI(yourPlugin, player, w);
        gui.addStateListener(new MapGUIStateListenerAdapter() {
            @Override
            public void onDispose(final MapGUI me) {
                toRemove.add(me);
                Bukkit.getScheduler().runTask(mainPlugin, checkTask);
            }
        });
        guis.add(gui);
        return gui;
    }

    private void check() {
        for (MapGUI gui : toRemove)
            guis.remove(gui);
        toRemove.clear();
    }
}
