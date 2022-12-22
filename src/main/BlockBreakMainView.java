package main;

import end.BlockBreakEndView;

import javax.swing.*;
import java.awt.*;

import static game.StageStep.*;

public class BlockBreakMainView extends JFrame {

    public BlockBreakMainView(int stage) throws HeadlessException {
        setTitle("Block Breaking!!!");
        setSize(800, 800);

        BlockBreakingMain stageGame = new BlockBreakingMain(stage);
        add(stageGame);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
