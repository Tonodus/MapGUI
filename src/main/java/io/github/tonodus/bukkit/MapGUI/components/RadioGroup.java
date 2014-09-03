package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.MouseAdapter;

/**
 * Created by Tonodus (http://tonodus.github.io) on 23.08.2014.
 */
public class RadioGroup extends ComponentsContainerComponent<RadioButton> {
    private RadioButton active = null;

    public RadioGroup() {
        addMouseListener(new CheckClickListener());
    }

    public RadioButton getActive() {
        return active;
    }

    public void setChecked(RadioButton button) {
        if (button != null && !hasComponent(button))
            throw new IllegalArgumentException("RadioButton (" + button + ") isn't added to this list, can't set it!");
        onCheck(button);
    }

    private void onCheck(RadioButton me) {
        if (active != null) active.onNotCheckedAnymore();
        active = me;
        if (me != null) me.onChecked();
        invalidate();
    }

    private class CheckClickListener extends MouseAdapter {
        @Override
        public void onLeftClick(int x, int y, boolean withShift) {
            RadioButton rb = getComponentAt(x, y);
            if (rb != null && rb != active) {
                onCheck(rb);
            }
        }
    }
}
