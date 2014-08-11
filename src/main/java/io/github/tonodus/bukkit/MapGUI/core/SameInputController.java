package io.github.tonodus.bukkit.MapGUI.core;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
public class SameInputController<O> extends DefaultInputController<O, O> {
    @Override
    protected O convert(O input) {
        return input;
    }
}
