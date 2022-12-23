package end;

import front.BlockBreakFrontView;
import front.CustomLabel;
import main.BlockBreakingMain;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BlockBreakingEnd extends JPanel implements KeyListener {

    private BufferedImage image;
    private Clip clip;

    public BlockBreakingEnd() {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("../image/space.jpeg");
            image = ImageIO.read(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            InputStream url = this.getClass().getResourceAsStream("../audio/ballDead.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        JLabel label = new CustomLabel("The End", Color.RED, Font.BOLD, 80);
        JLabel score = new CustomLabel("Score : " + BlockBreakingMain.score, Color.WHITE, Font.BOLD, 80);

        setLayout(new GridLayout(2, 1));

        add(label);
        add(score);

        addKeyListener(this);
        requestFocus();
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            new BlockBreakFrontView();
            setVisible(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
