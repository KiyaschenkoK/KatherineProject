package health_blog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class healthblog
{
    public static void main ( String[] args )
    {

        final JFrame[] AppFrame = new JFrame[1];
        try
        {
            // Устанавливаем нативный стиль компонентов
            UIManager.setLookAndFeel ( UIManager.getSystemLookAndFeelClassName () );
        }
        catch ( Throwable e )
        {}

        final JFrame f = new JFrame ();

        f.getRootPane ().setOpaque ( true );
        f.getRootPane ().setBackground ( Color.WHITE );
        f.getRootPane ().setBorder ( BorderFactory.createEmptyBorder ( 10, 10, 10, 10 ) );

        f.getContentPane ().setBackground ( Color.WHITE );
        f.getContentPane ().setLayout ( new GridLayout ( 2, 4, 10, 10 ) );

        f.getContentPane().add(createExamplePanel( 0, "Баланс кКалорий", new Runnable()
        {
            public void run ()
            {
                AppFrame[0] =new kiloKal();
            }
        } ), BorderLayout.CENTER );

        f.getContentPane ().add ( createExamplePanel ( 1, "Индекс массы тела", new Runnable()
        {
            public void run ()
            {
                AppFrame[0]=new IMT();
            }
        } ), BorderLayout.CENTER );

        f.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        f.pack ();
        f.setLocationRelativeTo ( null );
        f.setResizable ( false );
        f.setVisible ( true );
    }

    private static int rollover = -1;

    private static JPanel createExamplePanel ( final int exampleIcon, String exampleName,
                                               final Runnable exampleRun )
    {
        final JPanel panel = new JPanel()
        {
            private Color light = new Color ( 216, 216, 216 );
            private Color dark = new Color ( 166, 166, 166 );

            public void paint ( Graphics g )
            {
                Graphics2D g2d = ( Graphics2D ) g;
                g2d.setRenderingHint ( RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON );

                g2d.setPaint ( new GradientPaint ( 0, 0, rollover == exampleIcon ? dark : light,
                        getWidth (), getHeight (), Color.WHITE ) );
                g2d.fillRoundRect ( 0, 0, getWidth (), getHeight (), 20, 20 );

                g2d.setPaint ( Color.GRAY );
                g2d.drawRoundRect ( 0, 0, getWidth () - 1, getHeight () - 1, 20, 20 );

                super.paint ( g );
            }
        };
        panel.setOpaque ( false );
        panel.setBorder ( BorderFactory.createEmptyBorder ( 10, 10, 10, 10 ) );
        panel.setLayout ( new BorderLayout ( 5, 5 ) );

        panel.add ( new JLabel ( new ImageIcon (healthblog.class.getResource("icons/" + exampleIcon + ".png"))),
                BorderLayout.CENTER );

        panel.add ( new JLabel ( exampleName, JLabel.CENTER ), BorderLayout.SOUTH );

        panel.addMouseListener ( new MouseAdapter()
        {
            public void mouseEntered ( MouseEvent e )
            {
                rollover = exampleIcon;
                panel.repaint ();
            }

            public void mouseExited ( MouseEvent e )
            {
                rollover = -1;
                panel.repaint ();
            }

            public void mousePressed ( MouseEvent e )
            {
                exampleRun.run ();
            }
        } );

        return panel;
    }
}