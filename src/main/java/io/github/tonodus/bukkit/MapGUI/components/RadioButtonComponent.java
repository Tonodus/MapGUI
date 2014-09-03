package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.ComponentsContainer;
import io.github.tonodus.bukkit.MapGUI.api.Drawable;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 23.08.2014.
 */
public class RadioButtonComponent extends Checkbox implements RadioButton {
    private static Drawable uncheckedDrawable = null, checkedDrawable = null;
    private RadioGroup attachedTo = null;

    public RadioButtonComponent(Drawable unchecked, Drawable checked) {
        super(unchecked, checked);
    }

    public RadioButtonComponent() {
        super(getU(), getC());
    }


    private static Drawable getU() {
        return (uncheckedDrawable == null ? (uncheckedDrawable = new UncheckedDrawable()) : uncheckedDrawable);
    }

    private static Drawable getC() {
        return (checkedDrawable == null ? (checkedDrawable = new CheckedDrawable()) : checkedDrawable);
    }

    @Override
    public void onAttachedTo(ComponentsContainer c) {
        if (!(c instanceof RadioGroup))
            throw new IllegalArgumentException("RadioButton can only be attached to a RadioGroup!");
        super.onAttachedTo(c);
        attachedTo = (RadioGroup) c;
    }

    @Override
    public void onDetachedFrom(ComponentsContainer c) {
        attachedTo = null;
    }

    @Override
    public void setChecked(boolean newValue) {
        super.setChecked(newValue);
        if (attachedTo != null)
            if (newValue)
                attachedTo.setChecked(this);
            else attachedTo.setChecked(null);
    }

    @Override
    public void onNotCheckedAnymore() {
        super.setChecked(false);
    }

    @Override
    public void onChecked() {
        super.setChecked(true);
    }

    private static class UncheckedDrawable implements Drawable {

        @Override
        public void updateSync() {

        }

        @Override
        public void drawAsync(Graphics2D canvas, int width, int height) {
            canvas.setColor(Color.BLACK);
            canvas.drawOval(0, 0, width - 1, height - 1);
        }
    }

    private static class CheckedDrawable extends UncheckedDrawable {
        @Override
        public void drawAsync(Graphics2D g, int width, int height) {
            g.setColor(Color.BLACK);
            g.fillOval(0, 0, width - 1, height - 1);
        }
    }
}
