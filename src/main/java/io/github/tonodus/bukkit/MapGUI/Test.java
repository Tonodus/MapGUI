package io.github.tonodus.bukkit.MapGUI;

import io.github.tonodus.bukkit.MapGUI.api.MapGUI;
import io.github.tonodus.bukkit.MapGUI.api.Window;
import io.github.tonodus.bukkit.MapGUI.core.BaseWindow;
import io.github.tonodus.bukkit.MapGUI.core.MapGuiHelper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public class Test extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void pc(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equals("/gui")) {
            MapGUI gui = MapGuiHelper.createGui(this);
            Window myWindow = new BaseWindow();
            gui.setWindow(myWindow);

            gui.show(e.getPlayer());
        }
    }
}
