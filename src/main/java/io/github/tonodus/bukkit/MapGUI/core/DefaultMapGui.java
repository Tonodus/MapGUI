package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public class DefaultMapGui extends AbstractMapGUI implements SinglePlayerMapGUI, Listener {
    public static final MapItemModifier defaultGetter = new DefaultMapItemModifier();

    static final int HEIGHT = 128;
    static final int WIDTH = 128;

    private MoveHelper moveHelper;
    private DrawHelper drawHelper;

    private DefaultCursor cursor;
    private Player showTo = null;
    private ItemStack itemBefore;
    private MapItemModifier mapGetter;

    private Listener bukkitListener;

    public DefaultMapGui(Plugin plugin, Player player, WorkerThread worker) {
        this(plugin, player, worker, defaultGetter);
    }

    public DefaultMapGui(Plugin plugin, Player player, WorkerThread worker, MapItemModifier getter) {
        super(plugin);
        this.showTo = player;
        this.cursor = new DefaultCursor();
        this.mapGetter = getter;
        this.drawHelper = new DrawHelper(WIDTH, HEIGHT, worker, player);
        this.moveHelper = new MoveHelper(plugin, this);
        this.bukkitListener = this;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if (player != showTo)
            throw new IllegalStateException("Player to show != toShow!");

        super.render(mapView, mapCanvas, player);

        drawHelper.onRenderTick(mapCanvas);

        if (mapCanvas.getCursors().size() < 1)
            mapCanvas.getCursors().addCursor(0, 0, (byte) 6);
        MapCursor c = mapCanvas.getCursors().getCursor(0);
        cursor.getType().applyOn(c);
        c.setX(cursor.getMapX());
        c.setY(cursor.getMapY());
    }

    private ItemStack toItemStack() {
        ItemStack stack = new ItemStack(Material.MAP);
        ItemStack map = mapGetter.modifyItem(stack);
        map.setDurability(getMapView().getId());
        return map;
    }

    @Override
    public void show() {
        super.show();
        itemBefore = showTo.getItemInHand();
        showTo.setItemInHand(toItemStack());
        moveHelper.start();

        Bukkit.getPluginManager().registerEvents(bukkitListener, getPlugin());

        invalidate();
    }

    @Override
    public void hide() {
        super.hide();

        HandlerList.unregisterAll(bukkitListener);

        showTo.setItemInHand(itemBefore);
        itemBefore = null;
        this.moveHelper.stop();
    }

    @Override
    public void dispose() {
        super.dispose();

        cursor = null;
        drawHelper.dispose();
        drawHelper = null;
        showTo = null;
        itemBefore = null;
        mapGetter = null;
        moveHelper = null;
    }

    @Override
    public void setWindow(Window window) {
        super.setWindow(window);
        drawHelper.update(window);
    }

    @Override
    protected InputController getInputController() {
        return new DefaultInputController();
    }

    //========= E V E N T S ===========
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (event.getPlayer() != showTo)
            return;

        if (isVisible())
            hide();

        dispose();
    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent event) {
        if (!isVisible() || event.getPlayer() != showTo)
            return;

        event.setCancelled(true);

        if (Math.abs(event.getPreviousSlot() - event.getNewSlot()) > 1)
            return;

        super.onWheel(cursor.getX(), cursor.getY(), 1, event.getPlayer());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!isVisible() || event.getPlayer() != showTo)
            return;

        event.setCancelled(true);

        super.tryDrop(event.getPlayer());
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (!isVisible() || event.getPlayer() != showTo)
            return;

        event.setCancelled(true);

        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR)
            super.onLeftClick(cursor.getX(), cursor.getY(), event.getPlayer());
        else if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
            super.onRightClick(cursor.getX(), cursor.getY(), event.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        moveHelper.onMove(event);
    }

    @EventHandler
    public void onClick(EntityDamageByEntityEvent e) {
        if (!isVisible() || !(e.getDamager() instanceof Player) || e.getDamager() != showTo)
            return;

        e.setCancelled(true);

        super.onLeftClick(cursor.getX(), cursor.getY(), (Player) e.getDamager());
    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent e) {
        if (!isVisible() || e.getPlayer() != showTo)
            return;

        e.setCancelled(true);

        super.onRightClick(cursor.getX(), cursor.getY(), e.getPlayer());
    }

    // ============= GETTER / SETTER ==================
    @Override
    public final Cursor getCursor() {
        return cursor;
    }

    @Override
    public void invalidate() {
        drawHelper.invalidate();
    }

    @Override
    public final Player getAssignedPlayer() {
        return showTo;
    }
}
