package health_blog;

import javax.swing.border.Border;
import java.awt.*;


public class MyBorder implements Border {
    private Insets insets = new Insets(0,0,0,0);

    public MyBorder() {
        super();
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(0,0,255,50));
        g.fillRoundRect(x, y, width, height,10,10);
        g.setColor(Color.white);
        g.drawRoundRect(x+2, y+2, width-4, height-4,10,10);

    }
    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }
    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
