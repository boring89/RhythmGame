import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Rhythm extends JPanel implements ActionListener {

    private double noteX = 512;
    private double noteY = 384;

    private final double targetX = 50;
    private final double targetY = 50;

    private final double spd = 2f;

    private Timer timer;

    public Rhythm() {
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.GRAY);
        g.fillOval((int) targetX - 20, (int) targetY -20, 40, 40);

        g.setColor(Color.CYAN);
        g.fillOval((int) noteX - 10, (int) noteY - 10, 20, 20);
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        double dx = targetX - noteX;
        double dy = targetY - noteY;
        double dist = (double) Math.sqrt(dx * dx + dy * dy);

        if (dist > 1) {
            noteX += (dx / dist) * spd;
            noteY += (dy / dist) * spd;
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rhythm Prototype");
        Rhythm gamePanel = new Rhythm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.add(gamePanel);
        frame.setVisible(true);
    }
}