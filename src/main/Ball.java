package main;

import java.awt.*;

public class Ball extends GameObject{

    private float r;
    private final float speed = 180.0f;
    private float vx, vy;
    private float preX;
    private float preY;

    public Ball(Point p, Color color, float r) {
        super(p, color);
        this.r = r;

        preX = p.x;
        preY = p.y;

        this.vx = speed;
        this.vy = speed;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (p.x - r), (int) (p.y - r), (int) (r * 2), (int) (r * 2));
    }

    @Override
    void collision(GameObject o) {
        if(o instanceof Block block) {
            objectCollision(block);
        }
        if(o instanceof Stick stick) {
            objectCollision(stick);
        }
    }

    @Override
    void update(float dt) {
        preX = p.x;
        preY = p.y;

        p.x += (vx * dt);
        p.y += (vy * dt);
    }

    boolean isCollide(GameObject object) {
        if(object instanceof Block block) {
            return collideCondition(block);
        }
        if(object instanceof Stick stick) {
            return collideCondition(stick);
        }
        return false;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    private boolean collideCondition(Block block) {
        return p.x > (block.p.x - r) && p.x < (block.p.x + block.getWidth() + r) &&
                p.y > (block.p.y - r) && p.y < (block.p.y + block.getHeight() + r);
    }

    private boolean collideCondition(Stick stick) {
        return p.x > (stick.p.x - r) && p.x < (stick.p.x + stick.getWidth() + r) &&
                p.y > (stick.p.y - r) && p.y < (stick.p.y + stick.getHeight() + r);
    }

    private void objectCollision(Stick stick) {
        if(preX < (stick.p.x - r)) {
            p.x = (int) (stick.p.x - r);
            vx = -vx;
        }
        if(preX > (stick.p.x + stick.getWidth() + r)) {
            p.x = (int) (stick.p.x + stick.getWidth() + r);
            vx = -vx;
        }
        if(preY < (stick.p.y - r)) {
            p.y = (int) (stick.p.y - r);
            vy = -vy;
        }
        if(preY > (stick.p.y + stick.getHeight() + r)) {
            p.y = (int) (stick.p.y + stick.getHeight() + r);
            vy = -vy;
        }
    }

    private void objectCollision(Block block) {
        if(preX < block.p.x - r) {
            p.x = (int) (block.p.x - r);
            vx = -vx;
        }
        if(preX > block.p.x + block.getWidth() + r) {
            p.x = (int) (block.p.x + block.getWidth() + r);
            vx = -vx;
        }
        if(preY < block.p.y - r) {
            p.y = (int) (block.p.y - r);
            vy = -vy;
        }
        if(preY > block.p.y + block.getHeight() + r) {
            p.y = (int) (block.p.y + block.getHeight() + r);
            vy = -vy;
        }
    }

    public float getPreX() {
        return preX;
    }

    public void setPreX(float preX) {
        this.preX = preX;
    }

    public float getPreY() {
        return preY;
    }

    public void setPreY(float preY) {
        this.preY = preY;
    }
}
