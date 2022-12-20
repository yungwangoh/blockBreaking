package main;

import java.awt.*;

abstract public class GameObject {
    Point p;
    Color color;

    public GameObject(Point p, Color color) {
        this.p = p;
        this.color = color;
    }

    abstract void draw(Graphics g);
    abstract void collision(GameObject o);
    abstract void update(float dt);

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
