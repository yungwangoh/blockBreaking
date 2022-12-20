package main;

import java.awt.*;

public class Block extends GameObject{

    private float width;
    private float height;
    private boolean colorCheck;
    private boolean blockBreakCheck = false;

    public Block(Point p, Color color, float width, float height, boolean colorCheck) {
        super(p, color);
        this.width = width;
        this.height = height;
        this.colorCheck = colorCheck;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(p.x - 1, p.y - 1, (int) (width + 1), (int) (height + 1));
        g.setColor(color);
        g.fillRect(p.x, p.y, (int) width, (int) height);
    }

    @Override
    void collision(GameObject o) {

    }

    @Override
    void update(float dt) {}

    public boolean isColorCheck() {
        return colorCheck;
    }

    public void setColorCheck(boolean colorCheck) {
        this.colorCheck = colorCheck;
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

    public boolean isBlockBreakCheck() {
        return blockBreakCheck;
    }

    public void setBlockBreakCheck(boolean blockBreakCheck) {
        this.blockBreakCheck = blockBreakCheck;
    }
}
