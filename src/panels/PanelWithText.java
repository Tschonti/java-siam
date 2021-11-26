package panels;

import javax.swing.*;
import java.awt.*;

abstract public class PanelWithText extends JPanel {
    protected final JTextArea ta = new JTextArea();
    protected final JLabel title = new JLabel();
    protected static final Font hugeFont = new Font(Font.SANS_SERIF, Font.BOLD, 50);
    protected static final Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    protected static final Font smallFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    public PanelWithText() {
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(smallFont);
        ta.setBackground(UIManager.getColor ( "Panel.background" ));

        title.setFont(bigFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
