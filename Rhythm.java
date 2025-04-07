import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Rhythm extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private java.util.List<Note> notes = new ArrayList<>();
    private String lastResult = "";

    public Rhythm() {
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(16, this);
        timer.start();

        notes.add(new Note(400, 300, 50, 50, System.currentTimeMillis()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.DARK_GRAY);
        g.fillOval(30, 30, 40, 40); // 左上角

        g.setColor(Color.CYAN);
        for (Note note : notes) {
            g.fillOval((int) note.x - 10, (int) note.y - 10, 20, 20);
        }

        g.setColor(Color.WHITE);
        g.drawString("Result: " + lastResult, 20, 580);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.currentTimeMillis();

        Iterator<Note> iterator = notes.iterator();
        while (iterator.hasNext()) {
            Note note = iterator.next();
            note.update();
            if (note.distanceToTarget() < 10) {
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = Character.toUpperCase(e.getKeyChar());

        if (key == 'Q') {
            boolean hit = false;

            Iterator<Note> iterator = notes.iterator();
            while (iterator.hasNext()) {
                Note note = iterator.next();
                if (note.targetX == 50 && note.targetY == 50 && note.distanceToTarget() < 40) {
                    hit = true;
                    lastResult = "HIT!";
                    iterator.remove();
                    break;
                }
            }

            if (!hit) {
                lastResult = "MISS";
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rhythm Game - Input Detection");
        Rhythm gamePanel = new Rhythm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(gamePanel);
        frame.setVisible(true);
    }

    static class Note {
        float x, y;
        final float targetX, targetY;
        final float speed = 2f;
        final long spawnTime;

        public Note(float startX, float startY, float targetX, float targetY, long spawnTime) {
            this.x = startX;
            this.y = startY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.spawnTime = spawnTime;
        }

        public void update() {
            float dx = targetX - x;
            float dy = targetY - y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist > 1) {
                x += (dx / dist) * speed;
                y += (dy / dist) * speed;
            }
        }

        public float distanceToTarget() {
            float dx = targetX - x;
            float dy = targetY - y;
            return (float) Math.sqrt(dx * dx + dy * dy);
        }
    }
}
