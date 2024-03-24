import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(532, 532);
        setLocation(700, 268);
        add(new Field());
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        Window main_window = new Window();
    }
}