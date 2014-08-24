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
    private Drawable unchecked, checked, content;
    private int checkWidth;

    public Checkbox(Drawable unchecked, Drawable checked, Drawable content) {
        this.isChecked = false;
        this.unchecked = unchecked;
        this.checked = checked;
        this.content = content;
        this.checkWidth = 5;
        super.addMouseListener(new InternalMouseListener());
    }

    public Checkbox(Drawable content) {
        this(new UncheckedDrawable(), new CheckedDrawable(), content);
    }

    public Checkbox(String text) {
        this(new TextLabel(text).calcPreferredSizeNextFrame());
    }

    @Override
    public void drawAsync(Graphics2D g) {
        Graphics2D cp = (Graphics2D) g.create();

        cp.setClip(getX(), getY(), checkWidth, getHeight());
        cp.translate(getX(), getY());
        if (isChecked)
            checked.drawAsync(cp, checkWidth, getHeight());
        else
            unchecked.drawAsync(cp, checkWidth, getHeight());

        cp.translate(checkWidth, 0);
        cp.setClip(0, 0, getWidth() - checkWidth, getHeight());
        content.drawAsync(cp, getWidth() - checkWidth, getHeight());
        cp.dispose();
    }

    public final boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
        invalidate();
    }

    public void setBoxSize(int width) {
        this.checkWidth = width;
        invalidate();
    }

    @Override
    public void updateSync() {
        if (isChecked)
            checked.updateSync();
        else
            unchecked.updateSync();
        content.updateSync();
    }

    private static class CheckedDrawable extends UncheckedDrawable {
        @Override
        public void drawAsync(Graphics2D canvas, int width, int height) {
            super.drawAsync(canvas, width, height);
            canvas.drawLine(0, height / 2, width / 2, height);
            canvas.drawLine(width / 2, height, width, 0);
        }
    }

    private static class UncheckedDrawable implements Drawable {
        @Override
        public void updateSync() {

        }

        @Override
        public void drawAsync(Graphics2D canvas, int width, int height) {
            canvas.setColor(Color.BLACK);
            canvas.drawRect(0, 0, width, height);
        }
    }

    private class InternalMouseListener extends MouseAdapter {
        @Override
        public void onLeftClick(int x, int y, boolean withShift) {
            setChecked(!isChecked);
        }
    }
}
