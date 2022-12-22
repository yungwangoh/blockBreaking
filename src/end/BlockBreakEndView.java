package end;

import javax.swing.*;
import java.awt.*;

public class BlockBreakEndView extends JFrame {
    public BlockBreakEndView() throws HeadlessException {
        setTitle("BlockBreak!!!");
        setSize(800,800);

        add(new BlockBreakingEnd());

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
