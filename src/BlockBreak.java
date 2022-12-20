import front.BlockBreakingFrontView;
import main.BlockBreakingMain;

import javax.swing.*;
import java.awt.*;

public class BlockBreak extends JFrame {

    public BlockBreak() throws HeadlessException {
        setTitle("Block Breaking!!!");
        setSize(800, 800);

        add(new BlockBreakingMain(1));

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new BlockBreak();
    }
}
