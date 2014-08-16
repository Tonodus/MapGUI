package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.Drawable;
import io.github.tonodus.bukkit.MapGUI.api.MouseAdapter;
import io.github.tonodus.bukkit.MapGUI.drawable.ColorDrawable;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public class BaseButton extends Panel {
    private final int clickDownTime;
    private ButtonState state = ButtonState.NORMAL;
    private long downTime;
    private Drawable[] backgrounds;

    private boolean hovering = false;

    public BaseButton(Component content) {
        super(content);
        this.backgrounds = getDefaultBackgrounds();
        this.clickDownTime = getClickDownTime();
        addMouseListener(new InternMouseListener());
    }

    protected int getClickDownTime() {
        return 700;
    }

    protected Drawable[] getDefaultBackgrounds() {
        return new Drawable[]{new ColorDrawable(new Color(229, 229, 229)),
                new ColorDrawable(new Color(164, 164, 205)),
                new ColorDrawable(new Color(102, 102, 134))};
    }

    @Override
    protected void drawBackgroundAsync(Graphics2D canvas) {
        if (state == ButtonState.CLICKING)
            if (System.currentTimeMillis() - downTime >= clickDownTime)
                state = hovering ? ButtonState.HOVERING : ButtonState.NORMAL;
            else
                super.invalidate();

        updateBG();

        super.drawBackgroundAsync(canvas);

        drawBorder(canvas);
    }

    protected void drawBorder(Graphics2D canvas) {
        Color out = Color.GRAY;
        Color in = Color.LIGHT_GRAY;

        if (state == ButtonState.CLICKING) {
            out = Color.LIGHT_GRAY;
            in = Color.GRAY;
        }

        canvas.setColor(out);
        canvas.drawRect(getX(), getY(), getWidth() - 1, getHeight() - 1);
        canvas.setColor(in);
        canvas.drawRect(getX() + 1, getY() + 1, getWidth() - 3, getHeight() - 3);
    }

    @Override
    public void setBackground(Drawable normalBackground) {
        backgrounds[ButtonState.NORMAL.ordinal()] = normalBackground;
        invalidate();
    }

    public void setClickingBackground(Drawable clickingBackground) {
        backgrounds[ButtonState.CLICKING.ordinal()] = clickingBackground;
        invalidate();
    }

    public void setHoveringBackground(Drawable hoveringBackground) {
        backgrounds[ButtonState.HOVERING.ordinal()] = hoveringBackground;
        invalidate();
    }

    private void updateBG() {
        switch (state) {
            case NORMAL:
                super.setBackground(backgrounds[ButtonState.NORMAL.ordinal()]);
                break;
            case HOVERING:
                super.setBackground(backgrounds[ButtonState.HOVERING.ordinal()]);
                break;
            case CLICKING:
                super.setBackground(backgrounds[ButtonState.CLICKING.ordinal()]);
                break;
        }
    }

    public ButtonState getState() {
        return state;
    }

    public static enum ButtonState {
        NORMAL, HOVERING, CLICKING;
    }

    private class InternMouseListener extends MouseAdapter {
        @Override
        public void onLeftClick(int x, int y, boolean withShift) {
            state = ButtonState.CLICKING;
            downTime = System.currentTimeMillis();
            invalidate();
        }

        @Override
        public void onMouseEnter(int newX, int newY) {
            if (state == ButtonState.NORMAL)
                state = ButtonState.HOVERING;
            hovering = true;
            invalidate();
        }

        @Override
        public void onMouseLeave(int lastX, int lastY) {
            if (state == ButtonState.HOVERING)
                state = ButtonState.NORMAL;
            hovering = false;
            invalidate();
        }
    }
}
