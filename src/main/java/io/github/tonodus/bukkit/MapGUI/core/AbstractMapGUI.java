package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 17.08.2014.
 */
abstract class AbstractMapGUI implements MapGUI {
    private Window window;
    private Plugin plugin;
    private boolean visible = false;
    private Collection<MapGUIStateListener> stateListeners;
    private MapView mapView;
    private InputController inputController;
    private MapRenderer internRenderer;
    private DrawHelper drawHelper;

    public AbstractMapGUI(Plugin plugin, InputController inputController, DrawHelper drawHelper) {
        this.window = null;
        this.plugin = plugin;
        this.stateListeners = new ArrayList<MapGUIStateListener>();
        this.inputController = inputController;
        this.drawHelper = drawHelper;
        internRenderer = new MapRenderer() {
            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                AbstractMapGUI.this.render(mapView, mapCanvas, player);
            }
        };
    }

    protected void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        drawHelper.onTick(mapView, mapCanvas, player);
    }

    @Override
    public void show() {
        this.visible = true;

        mapView = Bukkit.createMap(Bukkit.getWorld("world"));
        for (MapRenderer render : mapView.getRenderers())
            mapView.removeRenderer(render);

        mapView.addRenderer(internRenderer);
    }

    @Override
    public void dispose() {
        if (visible)
            hide();

        for (MapGUIStateListener listener : stateListeners)
            listener.onDispose(this);

        if (window != null)
            window.detachedFrom(this);

        window = null;
        plugin = null;
        inputController = null;
        stateListeners.clear();
        stateListeners = null;
        drawHelper.dispose();
        drawHelper = null;
    }

    @Override
    public void hide() {
        for (MapGUIStateListener l : stateListeners)
            l.onHide(this);

        this.visible = false;
        this.mapView.removeRenderer(internRenderer);
        this.mapView = null;
    }

    @Override
    public void setWindow(Window window) {
        if (this.window != null)
            this.window.detachedFrom(this);
        this.window = window;
        this.drawHelper.setNewWindow(window);
        this.window.attachedOn(this);
    }

    protected void onLeftClick(int x, int y, Player who) {
        inputController.onLeftClick(x, y, who.isSneaking());
    }

    protected void onRightClick(int x, int y, Player player) {
        inputController.onRightClick(x, y, player.isSneaking());
    }

    protected void onWheel(int x, int y, int amount, Player who) {
        inputController.onMouseWheel(amount);
    }

    protected void onMove(int ox, int oy, int nx, int ny, Player who) {
        inputController.onMove(ox, oy, nx, ny);
    }

    protected void tryDrop(Player tried) {
        boolean result = false;
        for (MapGUIStateListener listener : stateListeners)
            result |= listener.shouldBlockDrop(this, tried);


        if (!result) {
            hide();
            dispose();
        }
    }

    @Override
    public final Window getCurrentWindow() {
        return window;
    }

    @Override
    public final boolean isVisible() {
        return visible;
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        inputController.addMouseListener(listener);
    }

    @Override
    public void addScrollListener(MouseWheelListener listener) {
        inputController.addScrollListener(listener);
    }

    @Override
    public void addInputListener(TextInputListener listener) {
        inputController.addInputListener(listener);
    }

    @Override
    public void removeMouseListener(MouseListener listener) {
        inputController.removeMouseListener(listener);
    }

    @Override
    public void removeScrollListener(MouseWheelListener listener) {
        inputController.removeScrollListener(listener);
    }

    @Override
    public void removeInputListener(TextInputListener listener) {
        inputController.removeInputListener(listener);
    }

    @Override
    public void addStateListener(MapGUIStateListener listener) {
        stateListeners.add(listener);
    }

    @Override
    public void removeDropListener(MapGUIStateListener listener) {
        stateListeners.remove(listener);
    }

    @Override
    public void invalidate() {
        drawHelper.invalidate();
    }

    protected final MapView getMapView() {
        return mapView;
    }

    protected final Plugin getPlugin() {
        return plugin;
    }
}
