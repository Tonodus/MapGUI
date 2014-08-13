package io.github.tonodus.bukkit.MapGUI.components;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
public class Button extends BaseButton {
    private TextLabel text;

    public Button(TextLabel label) {
        super(label);
    }

    public Button(String text) {
        this(new TextLabel(text));
    }

    public void setText(String text) {
        this.text.setText(text);
        invalidate();
    }
}
