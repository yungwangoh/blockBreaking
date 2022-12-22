package front;

import main.BlockBreakMainView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class BlockBreakingFront extends JPanel implements KeyListener, Runnable {

    private BufferedImage image;

    public BlockBreakingFront() {
        try {
            image = ImageIO.read(new FileInputStream("image/space.jpeg"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel jLabelTitle = new CustomLabel("Block Breaking!!!", Color.RED, Font.ROMAN_BASELINE, 80);
        JLabel jLabelBottom =  new CustomLabel("Press Space Bar!!", Color.BLUE, Font.BOLD, 25);

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
