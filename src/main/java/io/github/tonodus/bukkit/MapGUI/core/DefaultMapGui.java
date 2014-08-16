package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.*;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public class DefaultMapGui extends MapRenderer implements MapGUI {
    public static final MapItemGetter defaultGetter = new DefaultMapGetter();
    static final int HEIGHT = 128;
    static final int WIDTH = 128;
    DefaultInputController<MapGUI, MapGUI> inputController;
    MoveHelper moveHelper;
    private Window window;
    private DefaultCursor cursor;
    private Plugin plugin;
    private DrawHelper drawHelper;
    private Player showTo = null;
    private ItemStack itemBefore;
    private MapItemGetter mapGetter;
    private boolean visible = false;
    private Collection<DropListener> dropListeners;
    private MapView mapView;

    public DefaultMapGui(Plugin plugin, Player player, WorkerThread worker) {
        this(plugin, player, worker, defaultGetter);
    }

    public DefaultMapGui(Plugin plugin, Player player, WorkerThread worker, MapItemGetter getter) {
        this.showTo = player;
        this.window = null;
        this.cursor = new DefaultCursor();
        this.plugin = plugin;
        this.inputController = new SameInputController<MapGUI>();
        this.mapGetter = getter;
        this.drawHelper = new DrawHelper(WIDTH, HEIGHT, worker, player);
        this.moveHelper = new MoveHelper(plugin, this);
        this.dropListeners = new ArrayList<DropListener>();
    }


    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if (player != showTo)
            throw new IllegalStateException("Player to show != toShow!");

        drawHelper.onRenderTick(mapCanvas);

        if (mapCanvas.getCursors().size() < 1)
            mapCanvas.getCursors().addCursor(0, 0, (byte) 6);
        mapCanvas.getCursors().getCursor(0).setX(cursor.getMapX());
        mapCanvas.getCursors().getCursor(0).setY(cursor.getMapY());
    }

    private ItemStack toItemStack() {
        ItemStack map = mapGetter.getMap();
        map.setDurability(mapView.getId());
        return map;
    }

    @Override
    public void show() {
        this.visible = true;
        itemBefore = showTo.getItemInHand();

        mapView = Bukkit.createMap(Bukkit.getWorld("world"));
        for (MapRenderer render : mapView.getRenderers())
            mapView.removeRenderer(render);

        showTo.setItemInHand(toItemStack());

        mapView.addRenderer(this);

        moveHelper.start();

        invalidate();
    }

    @Override
    public void dispose() {
        if (visible)
            throw new IllegalStateException("Must hide before dispose gui!");

        for (DropListener listener : dropListeners)
            listener.onDispose(this);

        if (window != null)
            window.detachedFrom(this);

        window = null;
        cursor = null;
        plugin = null;
        drawHelper.dispose();
        drawHelper = null;
        inputController = null;
        showTo = null;
        itemBefore = null;
        mapGetter = null;
        moveHelper = null;
        dropListeners.clear();
        dropListeners = null;
    }


    @Override
    public void hide() {
        this.visible = false;
        showTo.setItemInHand(itemBefore);
        itemBefore = null;
        this.moveHelper.stop();
        this.mapView.removeRenderer(this);
        this.mapView = null;
    }

    public void onQuit(PlayerQuitEvent event) {
        if (event.getPlayer() != showTo)
            return;

        if (visible)
            hide();

        dispose();
    }

    public void onScroll(PlayerItemHeldEvent event) {
        if (!visible || event.getPlayer() != showTo)
            return;

        event.setCancelled(true);

        if (Math.abs(event.getPreviousSlot() - event.getNewSlot()) > 1)
            return;

        inputController.onMouseWheel(1, this);
    }

    public void onDrop(PlayerDropItemEvent event) {
        if (!visible || event.getPlayer() != showTo)
            return;

        event.setCancelled(true);

        Player p = event.getPlayer();

        boolean result = false;
        for (DropListener listener : dropListeners)
            result |= listener.onPossibleDrop(this, p);

        if (!result) {
            for (DropListener listener : dropListeners)
                listener.onPreDispose(this, p);

            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    hide();
                    dispose();
                }
            });
        }
    }

    public void onClick(PlayerInteractEvent event) {
        if (!visible || event.getPlayer() != showTo)
            return;

        event.setCancelled(true);

        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR)
            inputController.onLeftClick(getCursor().getX(), getCursor().getY(), event.getPlayer().isSneaking(), this);
        else if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
            inputController.onRightClick(getCursor().getX(), getCursor().getY(), event.getPlayer().isSneaking(), this);
    }

    @Override
    public void setWindow(Window window) {
        if (this.window != null)
            this.window.detachedFrom(this);
        this.window = window;
        this.window.attachedOn(this);
        drawHelper.update(window);
    }

    @Override
    public Window getCurrentWindow() {
        return window;
    }

    @Override
    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public void invalidate() {
        drawHelper.invalidate();
    }

    @Override
    public void setBackground(DyeColor color) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addMouseListener(MouseListener<MapGUI> listener) {
        inputController.addMouseListener(listener);
    }

    @Override
    public void addScrollListener(MouseWheelListener<MapGUI> listener) {
        inputController.addScrollListener(listener);
    }

    @Override
    public void addInputListener(TextInputListener<MapGUI> listener) {
        inputController.addInputListener(listener);
    }

    @Override
    public void removeMouseListener(MouseListener<MapGUI> listener) {
        inputController.removeMouseListener(listener);
    }

    @Override
    public void removeScrollListener(MouseWheelListener<MapGUI> listener) {
        inputController.removeScrollListener(listener);
    }

    @Override
    public void removeInputListener(TextInputListener<MapGUI> listener) {
        inputController.removeInputListener(listener);
    }

    @Override
    public void addDropListener(DropListener listener) {
        dropListeners.add(listener);
    }

    @Override
    public void removeDropListener(DropListener listener) {
        dropListeners.remove(listener);
    }

    public boolean isVisible() {
        return visible;
    }

    public Player assignedPlayer() {
        return showTo;
    }

}
