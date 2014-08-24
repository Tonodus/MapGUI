package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.Drawable;
import io.github.tonodus.bukkit.MapGUI.api.MouseAdapter;
import io.github.tonodus.bukkit.MapGUI.core.BaseComponent;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 23.08.2014.
 */
public class Checkbox extends BaseComponent {
    private boolean isChecked;
    private Drawable unchecked, checked;

    public Checkbox(Drawable unchecked, Drawable checked) {
        this.isChecked = false;
        this.unchecked = unchecked;
        this.checked = checked;
        super.addMouseListener(new InternalMouseListener());
    }

    public Checkbox() {
        this(new UncheckedDrawable(), new CheckedDrawable());
    }

    @Override
    public void drawAsync(Graphics2D g) {
        Graphics2D cp = (Graphics2D) g.create();

        // cp.setClip(getX(), getY(), getWidth() + 1, getHeight() + 1);
        cp.translate(getX(), getY());
        if (isChecked)
            checked.drawAsync(cp, getWidth(), getHeight());
        else
            unchecked.drawAsync(cp, getWidth(), getHeight());

        cp.dispose();
    }

    public final boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
        invalidate();
    }

    @Override
    public void updateSync() {
        checked.updateSync();
        unchecked.updateSync();
    }

    private static class CheckedDrawable extends UncheckedDrawable {
        @Override
        public void drawAsync(Graphics2D canvas, int width, int height) {
            super.drawAsync(canvas, width, height);
            canvas.drawLine(0, 0, width - 1, height - 1);
            canvas.drawLine(0, height - 1, width - 1, 0);
        }
    }

    private static class UncheckedDrawable implements Drawable {
        @Override
        public void updateSync() {

        }

        @Override
        public void drawAsync(Graphics2D canvas, int width, int height) {
            canvas.setColor(Color.BLACK);
            canvas.drawRect(0, 0, width - 1, height - 1);
        }
    }

    private class InternalMouseListener extends MouseAdapter {
        @Override
        public void onLeftClick(int x, int y, boolean withShift) {
            setChecked(!isChecked);
        }
    }
}
