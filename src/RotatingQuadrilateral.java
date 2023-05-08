import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RotatingQuadrilateral extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int ROTATION_ANGLE = 1; // Кут обертання

    private int centerX;
    private int centerY;
    private int angle;

    public RotatingQuadrilateral() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        centerX = WIDTH / 2;
        centerY = HEIGHT / 2;
        angle = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Створюємо координати вершин чотирикутника
        int[] xPoints = {centerX - 50, centerX + 50, centerX + 50, centerX - 50};
        int[] yPoints = {centerY - 50, centerY - 50, centerY + 50, centerY + 50};

        // Створюємо об'єкт AffineTransform для обертання
        AffineTransform rotation = new AffineTransform();
        rotation.setToRotation(Math.toRadians(angle), centerX, centerY);
        Shape rotatedShape = rotation.createTransformedShape(new Polygon(xPoints, yPoints, 4));

        // Заливаємо чотирикутник
        g2d.setColor(Color.RED);
        g2d.fill(rotatedShape);

        // Встановлюємо новий кут обертання
        angle += ROTATION_ANGLE;
        if (angle >= 360) {
            angle = 0;
        }

        // Оновлюємо відображення
        repaint();

        // Затримка
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void RenderingFrame() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Task 1");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new RotatingQuadrilateral(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
