package front;

import main.BlockBreakMainView;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BlockBreakingFront extends JPanel implements KeyListener, Runnable {

    private BufferedImage image;
    private Clip clip;

    public BlockBreakingFront() {
        try {
            image = ImageIO.read(new FileInputStream("image/space.jpeg"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File("audio/frontBGM.wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            clip.start();
            clip.loop(10);

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        JLabel jLabelTitle = new CustomLabel("Block Breaking!!!", Color.RED, Font.ROMAN_BASELINE, 80);
        JLabel jLabelBottom =  new CustomLabel("Press Space Bar!!", Color.BLUE, Font.BOLD, 40);

        setLayout(new GridLayout(2, 1));

        add(jLabelTitle);
        add(jLabelBottom);

        addKeyListener(this);
        requestFocus();
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0,0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            clip.stop();
            new BlockBreakMainView(1);
            setVisible(false);
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

    }
}
