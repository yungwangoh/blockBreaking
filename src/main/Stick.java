package main;

import java.awt.*;

public class Stick extends GameObject{

    private float width;
    private float height;

    public Stick(Point p, Color color, float width, float height) {
        super(p, color);
        this.width = width;
        this.height = height;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(p.x, p.y, (int) width, (int) height);
    }

    @Override
    void collision(GameObject o) {

    }

    @Override
    void update(float dt) {

    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
