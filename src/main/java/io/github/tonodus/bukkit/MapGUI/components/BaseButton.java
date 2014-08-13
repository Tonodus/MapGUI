package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.Drawable;
import io.github.tonodus.bukkit.MapGUI.api.MouseAdapter;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public class BaseButton extends Panel {
    private STATE state = STATE.NORMAL;
    private long downTime;

    private Color normalBg;
    private Color clickBg;
    private Color hoverBg;

    public BaseButton(Drawable content) {
        super(content);
        addMouseListener(new InternMouseListener());
    }

    @Override
    protected void drawBgAsync(Graphics2D canvas) {
        if (state == STATE.CLICKING)
            if (System.currentTimeMillis() - downTime >= 700)
                state = STATE.NORMAL;
            else
                super.invalidate();

        updateBG();

        super.drawBgAsync(canvas);

        //draw border
        Color out = Color.GRAY;
        Color in = Color.LIGHT_GRAY;

        if (state == STATE.CLICKING) {
            out = Color.LIGHT_GRAY;
            in = Color.GRAY;
        }

        canvas.setColor(out);
        canvas.drawRect(getX(), getY(), getWidth(), getHeight());
        canvas.setColor(in);
        canvas.drawRect(getX() + 2, getY() + 2, getWidth() - 2, getHeight() - 2);

    }

    @Override
    public void setBackground(Color c) {
        normalBg = c;
        invalidate();
    }

    public void setClickingBackground(Color c) {
        clickBg = c;
        invalidate();
    }

    public void setHoveringBackground(Color c) {
        hoverBg = c;
        invalidate();
    }

    private void updateBG() {
        switch (state) {
            case NORMAL:
                super.setBackground(normalBg);
                break;
            case HOVERING:
                super.setBackground(hoverBg);
                break;
            case CLICKING:
                super.setBackground(clickBg);
                break;
        }
    }

    private static enum STATE {
        NORMAL, HOVERING, CLICKING;
    }

    private class InternMouseListener extends MouseAdapter<Component> {
        @Override
        public void onLeftClick(int x, int y, boolean withShift, Component owner) {
            state = STATE.CLICKING;
            downTime = System.currentTimeMillis();
        }

        @Override
        public void onMouseEnter(int newX, int newY, Component owner) {
            if (state == STATE.NORMAL)
                state = STATE.HOVERING;
        }

        @Override
        public void onMouseLeave(int lastX, int lastY, Component owner) {
            if (state == STATE.HOVERING)
                state = STATE.NORMAL;
        }
    }
}
