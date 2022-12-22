package front;

import javax.swing.*;
import java.awt.*;

public class BlockBreakFrontView extends JFrame {

    public BlockBreakFrontView() throws HeadlessException {
        setTitle("Block Breaking!!!");
        setSize(800, 800);

        add(new BlockBreakingFront());
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new BlockBreakFrontView();
    }
}
