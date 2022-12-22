package main;

import end.BlockBreakEndView;
import game.StageStep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

import static game.StageStep.*;
import static java.awt.Color.*;

public class BlockBreakingMain extends JPanel implements KeyListener, Runnable {

    private LinkedList<GameObject> objects;
    private final int stickWidth;
    private final Point windowSize;
    private final Stick stick;
    private final Ball ball;
    private boolean end;
    private int stage;
    private boolean nextStage;
    private float ballRadius;
    private static int score = 0;

    public BlockBreakingMain(int stage) {
        setBackground(black);

        objects = new LinkedList<>();
        end = false;
        this.stage = stage;
        nextStage = false;
        ballRadius = 8.5f;
        int stickHeight = 40;
        windowSize = new Point(800, 772);
        stickWidth = 180;
        ball = new Ball(new Point(300, 500), white, ballRadius);

        stick = new Stick(new Point(350, 700), LIGHT_GRAY, stickWidth, stickHeight);

        Dimension d = new Dimension(windowSize.x, windowSize.y);
        init(stage, d);

        addKeyListener(this);
        setFocusable(true);
        requestFocus();

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        stick.draw(g);

        // 블록 채우기
        for(int i = 0; i < objects.size(); i++) {

            if(objects.get(i) instanceof Block block) {
                if(!block.isBlockBreakCheck()) {
                    blockBreakAllow(g, objects.get(i));
                } else {
                    objects.get(i).draw(g);
                }
            } else {
                objects.get(i).draw(g);
            }
        }
        repaint();
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
        int[] colorCheck = new int[stage * stage * 4];
        int index = 0;
        Block[] blocks = new Block[stageBlockSize * stageBlockSize];

        blockColorInit(stage, stageBlockSize, colorCheck);
        blockInit(stageBlockSize, w, h, colorCheck, index, blocks);

        Block boundaryOne = new Block(new Point(0, 0), GRAY, 5.0f, windowSize.y, false);
        Block boundaryTwo = new Block(new Point(0, 0), GRAY, windowSize.x, 5.0f, false);
        Block boundaryThr = new Block(new Point(windowSize.x, 0), GRAY, 5.0f, windowSize.y, false);

        boundaryOne.setBlockBreakCheck(true);
        boundaryTwo.setBlockBreakCheck(true);
        boundaryThr.setBlockBreakCheck(true);

        objects.add(boundaryOne);
        objects.add(boundaryTwo);
        objects.add(boundaryThr);
        objects.add(ball);
        objects.addAll(Arrays.asList(blocks));
    }

    public void nextStage(int stage) {
        new BlockBreakMainView(stage);
        nextStage = false;
    }

    public void end() {
        new BlockBreakEndView();
        end = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            stick.p.x -= 50;
            stick.p.y = 700;
            stick.setP(stick.getP());

            if(stick.p.x < 0) stick.p.x = 0;

        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            stick.p.x += 50;
            stick.p.y = 700;
            stick.setP(stick.getP());

            if(stick.p.x + stickWidth > 800) stick.p.x = 800 - stickWidth;
        }
        repaint();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(35);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // ball update
            for (var a : objects) {
                a.update(0.016f);
            }

            // collision
            for (int i = 0; i < objects.size(); i++) {
                if (!(objects.get(i) instanceof Ball ball)) continue;

                for (int j = 0; j < objects.size(); j++) {
                    if (!(objects.get(j) instanceof Block block)) continue;

                    if (ball.isCollide(block)) {
                        if (!block.isBlockBreakCheck()) {
                            objects.remove(block);
                            BlockBreakingMain.score += 100;
                            if(block.isColorCheck()) {
                                objects.add(new Ball(new Point((int)( block.getP().x + block.getWidth()),
                                        block.getP().y), WHITE, ballRadius));
                            }
                        }
                        ball.collision(block);
                    }
                }
                if (ball.isCollide(stick))
                    ball.collision(stick);

                if (ball.ballRemove(this)) {
                    objects.remove(ball);
                }
            }

            if(!nextStageCheck()) {
                nextStage = true;
                break;
            }

            if (!endCheck(stage)) {
                end = true;
                break;
            }
            repaint();
        }

        if(nextStage) {
            nextStage(stage + 1);
        }

        if(end) {
            end();
        }
        repaint();
    }

    private boolean nextStageCheck() {
        int blockCheck = 0;

        for(GameObject object : objects) {
            if(object instanceof Block) {
                blockCheck++;
            }
        }
        return blockCheck > 3;
    }

    private boolean endCheck(int stage) {
        int ballCheck = 0;

        for (GameObject object : objects) {
            if (object instanceof Ball) {
                ballCheck++;
            }
        }
        return ballCheck >= 1 || stage >= THREE;
    }

    private void blockColorInit(int stage, int stageBlockSize, int[] colorCheck) {
        for(int i = 0; i < stage * stage * 4; i++) {
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

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
