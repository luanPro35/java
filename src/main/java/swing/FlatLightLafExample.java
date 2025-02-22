//package swing;
//
//import com.formdev.flatlaf.FlatLightLaf;
//
//import javax.swing.*;
//
//public class FlatLightLafExample {
//
//    public static void main(String[] args) {
//        // Set FlatLightLaf as the LookAndFeel
//        try {
//            UIManager.setLookAndFeel(new FlatLightLaf());
//        } catch (UnsupportedLookAndFeelException e) {
//            System.err.println("Failed to initialize FlatLaf: " + e.getMessage());
//        }
//
//        // Create a simple Swing application to test the LookAndFeel
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("FlatLightLaf Example");
//            frame.setSize(400, 200);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setLocationRelativeTo(null);
//
//            JPanel panel = new JPanel();
//            JLabel label = new JLabel("This is a FlatLaf Light Theme example!");
//            JButton button = new JButton("Click Me");
//
//            panel.add(label);
//            panel.add(button);
//            frame.add(panel);
//
//            frame.setVisible(true);
//        });
//    }
//}
