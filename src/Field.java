import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public class Field extends JPanel implements ActionListener {
    private final int SIZE = 520;
    private final int DOT_SIZE = 26;
    private final int ALL_DOTS = 520;
    private Image dot;
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];
    private int dots = ThreadLocalRandom.current().nextInt(2, 10);
    private Timer timer;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    boolean flag = false;
    int speed = 250;

    public Field() {
        setBackground(Color.black);
        LoadImage();
        addKeyListener(new FieldKeyListener());
        Choice_Start_Direction();
        initGame();
        setFocusable(true);
    }

    public void Choice_Start_Direction() {
        int direction = ThreadLocalRandom.current().nextInt(1, 8);
        if (direction == 1) {
            left = true;
            up = true;
        } else if (direction == 2) {
            up = true;
        } else if (direction == 3) {
            left = true;
            up = true;
        } else if (direction == 4) {
            right = true;
        } else if (direction == 5) {
            right = true;
            down = true;
        } else if (direction == 6) {
            down = true;
        } else if (direction == 7) {
            left = true;
            down = true;
        } else if (direction == 8) {
            left = true;
        }
    }

    public void initGame() {
        int start_position = ThreadLocalRandom.current().nextInt(26, SIZE - 100);
        for (int i = 0; i < dots; i++) {
            x[i] = start_position - i * DOT_SIZE;
            y[i] = start_position;
        }
        timer = new Timer(speed, this);
        timer.start();
        flag = true;
    }

    public void LoadImage() {
        dot = new ImageIcon(Objects.requireNonNull(Field.class.getResource("dot1.png"))).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < dots; i++) {
            g.drawImage(dot, x[i], y[i], this);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkCollisions() {
        if (x[0] >= SIZE - DOT_SIZE * 1.8) {
            left = true;
            right = false;
        } else if (x[0] < DOT_SIZE * 0.5) {
            left = false;
            right = true;
        } else if (y[0] >= SIZE - DOT_SIZE * 1.8 - 25) {
            up = true;
            down = false;
        } else if (y[0] < DOT_SIZE * 0.5) {
            up = false;
            down = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkCollisions();
        move();
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP && speed >= 100) {
                speed -= 50;
                timer.setDelay(speed);
            }
            if (key == KeyEvent.VK_DOWN) {
                speed += 50;
                timer.setDelay(speed);
            }
            if (key == KeyEvent.VK_W && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_S && !up) {
                right = false;
                down = true;
                left = false;
            }
            if (key == KeyEvent.VK_A && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_D && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_P) {
                if (flag) {
                    timer.stop();
                    flag = false;
                } else if (!flag) {
                    timer.start();
                    flag = true;
                }
            }
        }
    }
}
