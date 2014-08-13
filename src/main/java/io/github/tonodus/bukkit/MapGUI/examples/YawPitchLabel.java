package io.github.tonodus.bukkit.MapGUI.examples;

import io.github.tonodus.bukkit.MapGUI.components.TextLabel;
import org.bukkit.entity.Player;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public class YawPitchLabel extends TextLabel {
    private final Player toWatch;

    public YawPitchLabel(Player toWatch) {
        this.toWatch = toWatch;
    }

    @Override
    public void updateSync() {
        setText("Y: " + toWatch.getLocation().getYaw() + "|P: " + toWatch.getLocation().getPitch());
        calcPreferredSizeNextFrame();
    }
}
