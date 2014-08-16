package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public interface Component extends InputListenerController<Component>, SizelessDrawable {
    public int getX();

    public void setX(int x);

    public int getY();

    public void setY(int y);

    public void setPosition(int x, int y);

    public void setSize(int w, int h);

    public void setBounds(int x, int y, int w, int h);

    public int getWidth();

    public void setWidth(int w);

    public int getHeight();

    public void setHeight(int h);

    void onLostFocus();

    void onFocus();

    void onAttachedTo(ComponentsContainer container);

    void onDetachedFrom(ComponentsContainer container);

    boolean requestFocus();

    void invalidate();
}
