package main;

import end.BlockBreakEndView;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;

import static game.StageStep.THREE;
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
    private Clip clip;
    public static int score = 0;
    private URL f1, f2;

    public BlockBreakingMain(int stage) {
        setBackground(black);

        /*f1 = new File("audio/ballCollision.wav");
        f2 = new File("audio/stickCollision.wav");*/
        f1 = this.getClass().getResource("audio/ballCollision.wav");
        f2 = this.getClass().getResource("audio/stickCollision.wav");

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

    private void ballCollisionSound(URL f1) {
        soundControl(f1);
    }
    private void stickCollisionSound(URL f2) {
        soundControl(f2);
    }

    private void soundControl(URL f) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f);

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            clip.start();

        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ?????? ?????????
        stick.draw(g);

        // ?????? ?????????
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

    /**
     * ?????? ???????????? ????????? ????????? -> ???????????? ?????? ????????? ????????????.
     * @param g
     * @param o
     */
    private void blockBreakAllow(Graphics g, GameObject o) {
        if (((Block) o).isColorCheck()) {
            o.color = YELLOW;
            o.draw(g);
        } else {
            o.draw(g);
        }
    }

    /**
     * ?????? ?????????, ??? ?????????
     * @param stage ?????? ??????
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

    /**
     * ?????? ??????????????? ????????????.
     * @param stage
     */
    public void nextStage(int stage) {
        new BlockBreakMainView(stage);
        nextStage = false;
    }

    /**
     * ?????? ??????
     */
    public void end() {
        new BlockBreakEndView();
        end = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            keyLeftMove();

        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keyRightMove();
        }
        repaint();
    }

    private void keyRightMove() {
        stick.p.x += 50;
        stick.p.y = 700;
        stick.setP(stick.getP());

        if(stick.p.x + stickWidth > 800) stick.p.x = 800 - stickWidth;
    }

    private void keyLeftMove() {
        stick.p.x -= 50;
        stick.p.y = 700;
        stick.setP(stick.getP());

        if(stick.p.x < 0) stick.p.x = 0;
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

                    blockCollisionControl(ball, block);
                }
                stickCollisionControl(ball);

                outOfBall(ball);
            }

            // ?????? ??????????????? ????????? ??????
            if(!nextStageCheck()) {
                nextStage = true;
                break;
            }

            // ?????? ?????? ??????
            if (!endCheck(stage)) {
                end = true;
                break;
            }
            repaint();
        }

        // ?????? ????????????
        if(nextStage) {
            nextStage(stage + 1);
        }

        // ?????? ??????
        if(end) {
            end();
        }

        repaint();
    }

    private void outOfBall(Ball ball) {
        if (ball.ballRemove(this)) {
            objects.remove(ball);
        }
    }

    private void stickCollisionControl(Ball ball) {
        if (ball.isCollide(stick)) {
            stickCollisionSound(f2);
            ball.collision(stick);
        }
    }

    private void blockCollisionControl(Ball ball, Block block) {
        if (ball.isCollide(block)) {
            ballCollisionSound(f1);
            if (!block.isBlockBreakCheck()) {
                objects.remove(block);

                // ?????? ????????? ???????????? 100??? UP
                BlockBreakingMain.score += 100;

                collisionBallInc(block);
            }
            ball.collision(block);
        }
    }

    private void collisionBallInc(Block block) {
        if(block.isColorCheck()) {
            objects.add(new Ball(new Point((int)( block.getP().x + block.getWidth()) / 2,
                    (int) (block.getP().y + block.getHeight()) / 2), WHITE, ballRadius));
        }
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
