package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Cursor;
import io.github.tonodus.bukkit.MapGUI.api.*;
import io.github.tonodus.bukkit.MapGUI.api.Window;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
class DefaultMapGui extends MapRenderer implements MapGUI {
    public static final MapItemGetter defaultGetter = new DefaultMapGetter();
    static final int HEIGHT = 100;
    static final int WIDTH = 100;

    private Window window;
    private Cursor cursor;
    private Plugin plugin;
    private boolean needRedraw;

    private DefaultInputController<MapGUI, MapGUI> inputController;

    private Player showTo = null;
    private ItemStack itemBefore;

    private MapItemGetter mapGetter;

    private BufferedImage image;
    private Graphics graphics;

    DefaultMapGui(Plugin plugin) {
        this(plugin, defaultGetter);
    }

    DefaultMapGui(Plugin plugin, MapItemGetter getter) {
        this.window = null;
        this.cursor = new DefaultCursor();
        this.plugin = plugin;
        this.inputController = new SameInputController<MapGUI>();
        this.mapGetter = getter;

        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.graphics = image.getGraphics();
    }


    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if (player != showTo)
            return;

        if (!needRedraw || window == null)
            return;
        needRedraw = false;

        window.draw(graphics);
    }

    @Override
    public void show(Player player, boolean force) {
        if (showTo == null)
            throw new IllegalStateException("You must hide the map from " + showTo + " before you can show " + player + " the map!");

        showTo = player;
        itemBefore = showTo.getItemInHand();

        ItemStack map = mapGetter.getMap();
        showTo.setItemInHand(map);

        MapView mapView = Bukkit.createMap(Bukkit.getWorld("world"));
        map.setDurability(mapView.getId());

        mapView.getRenderers().clear();

        mapView.addRenderer(this);
    }

    @Override
    public void hide() {
        this.showTo.setItemInHand(itemBefore);
        itemBefore = null;
        this.showTo = null;
    }

    @Override
    public void show(Player player) {
        show(player, false);
    }

    public void onMove(PlayerMoveEvent event) {
        event.setCancelled(true);

        //TODO
    }

    public void onClick(PlayerInteractEvent event) {
        event.setCancelled(true);

        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR)
            inputController.onLeftClick(getCursor().getX(), getCursor().getY(), event.getPlayer().isSneaking(), this);
        else if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
            inputController.onRightClick(getCursor().getX(), getCursor().getY(), event.getPlayer().isSneaking(), this);
    }

    @Override
    public void setWindow(Window window) {
        this.window = window;
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
        needRedraw = true;
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
}
