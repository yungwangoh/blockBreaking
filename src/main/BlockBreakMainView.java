package main;

import javax.swing.*;
import java.awt.*;

import static game.StageStep.ONE;
import static game.StageStep.TWO;

public class BlockBreakMainView extends JFrame {

    public BlockBreakMainView() throws HeadlessException {
        setTitle("Block Breaking!!!");
        setSize(800, 800);

        add(new BlockBreakingMain(ONE));

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
