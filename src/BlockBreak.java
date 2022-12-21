import main.BlockBreakingMain;

import javax.swing.*;
import java.awt.*;

import static game.StageStep.ONE;

public class BlockBreak extends JFrame {

    public BlockBreak() throws HeadlessException {
        setTitle("Block Breaking!!!");
        setSize(800, 800);

        add(new BlockBreakingMain(ONE));

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new BlockBreak();
    }
}
