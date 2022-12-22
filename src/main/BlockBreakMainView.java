package main;

import javax.swing.*;
import java.awt.*;

import static game.StageStep.*;

public class BlockBreakMainView extends JFrame {

    public BlockBreakMainView() throws HeadlessException {
        setTitle("Block Breaking!!!");
        setSize(800, 800);

        BlockBreakingMain stageOne = new BlockBreakingMain(ONE);
        add(stageOne);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
