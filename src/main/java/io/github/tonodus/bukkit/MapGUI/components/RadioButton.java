package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.Component;

/**
 * Created by Tonodus (http://tonodus.github.io) on 23.08.2014.
 */
public interface RadioButton extends Component {
    void onNotCheckedAnymore();

    void onChecked();

    boolean isChecked();
}
