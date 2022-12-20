package main;

import end.BlockBreakingEndView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

import static java.awt.Color.*;

public class BlockBreakingMain extends JPanel implements KeyListener, Runnable {

    LinkedList<GameObject> objects = new LinkedList<>();
    int stickWidth = 150;
    int stickHeight = 10;
    int ballRadius = 5;
    int stage;
    Point windowSize = new Point(800, 772);

    public BlockBreakingMain(int stage) {
        this.stage = stage;
        setBackground(black);

        Dimension d = new Dimension(windowSize.x, windowSize.y);
        init(stage, d);

        addKeyListener(this);
        requestFocus();
        setFocusable(true);

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 블록 채우기
        for(GameObject o : objects) {

            if( o instanceof Block) {
                if(!((Block) o).isBlockBreakCheck()) {
                    blockBreakAllow(g, o);
                } else {
                    o.draw(g);
                }
            } else {
                o.draw(g);
            }
        }
    }

    private void blockBreakAllow(Graphics g, GameObject o) {
        if (((Block) o).isColorCheck()) {
            o.color = YELLOW;
            o.draw(g);
        } else {
            o.draw(g);
        }
    }

    /**
     * 블럭 초기화, 공 초기화
     * @param stage 게임 단계
     */
    void init(int stage, Dimension d) {

        int stageBlockSize = 4 * stage;
        int w = d.width / (stageBlockSize);
        int h = d.height / (stageBlockSize) / 3;
        int[] colorCheck = new int[stage * 2];
        int index = 0;
        Block[] blocks = new Block[stageBlockSize * stageBlockSize];

        blockColorInit(stage, stageBlockSize, colorCheck);
        blockInit(stageBlockSize, w, h, colorCheck, index, blocks);

        Block boundaryOne = new Block(new Point(0, 0), GRAY, 1.0f, windowSize.y, false);
        Block boundaryTwo = new Block(new Point(0, 0), GRAY, windowSize.x, 1.0f, false);
        Block boundaryThr = new Block(new Point(windowSize.x, 0), GRAY, 1.0f, windowSize.y, false);

        boundaryOne.setBlockBreakCheck(true);
        boundaryTwo.setBlockBreakCheck(true);
        boundaryThr.setBlockBreakCheck(true);

        objects.add(boundaryOne);
        objects.add(boundaryTwo);
        objects.add(boundaryThr);
        objects.add(new Stick(new Point(350, 700), LIGHT_GRAY, stickWidth, stickHeight));
        objects.add(new Ball(new Point(450, 650), white, ballRadius));
        objects.addAll(Arrays.asList(blocks));
    }

    public static void start() {

    }

    public static void end() {
        new BlockBreakingEndView();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ListIterator<GameObject> it = objects.listIterator();
        GameObject g = getGameObject(it);

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {

            g.p.x -= 50;
            g.p.y = 700;
            g.setP(g.p);

        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {

            g.p.x += 50;
            g.p.y = 700;
            g.setP(g.p);
        }
        repaint();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // ball update
            for(var a : objects) {
                a.update(0.016f);
            }

            // collision
            for(var a : objects) {
                if(!(a instanceof Ball)) continue;

                for(var b : objects) {
                    if(!(b instanceof Block)) continue;

                    for(var c : objects) {
                        if(!(c instanceof Stick stick)) continue;

                        Ball ball = (Ball) a;
                        Block block = (Block) b;

                        if(ball.isCollide(block)) {
                            ball.collision(block);
                        }
                        if(ball.isCollide(stick)) {
                            ball.collision(stick);
                        }
                    }
                }
            }
            repaint();
        }
    }

    private void blockColorInit(int stage, int stageBlockSize, int[] colorCheck) {
        for(int i = 0; i < stage * 2; i++) {
            colorCheck[i] = ((int) (Math.random() * (int) Math.pow(stageBlockSize, 2) - 1) + 1);
        }
    }

    private void blockInit(int stageBlockSize, int w, int h, int[] colorCheck, int index, Block[] blocks) {
        for(int i = 0; i < stageBlockSize; i++)  {
            for(int j = 0; j < stageBlockSize; j++) {
                Point p = new Point(j * w, i * h);
                blocks[index++] = new Block(p, MAGENTA, w, h, false);
            }
        }

        for (int i : colorCheck) {
            blocks[i].setColorCheck(true);
        }
    }

    private GameObject getGameObject(ListIterator<GameObject> it) {
        GameObject g = null;

        while(it.hasNext()) {
            GameObject next = it.next();

            if(next instanceof Stick) {
                g = next;
            }
        }
        return g;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
