package front;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel{

    public CustomLabel(String content, Color color, int font, int size) {
        setText(content);
        setForeground(color);
        setFont(new Font("Arial", font, size));
        setHorizontalAlignment(CENTER);
    }
}
