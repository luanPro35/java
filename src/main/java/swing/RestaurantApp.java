package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class RestaurantApp {
    private JFrame mainFrame;
    private JPanel tablePanel;
    private Map<Integer, String> tableOrders;
    private Map<Integer, Boolean> tableStatus;
    private Map<Integer, Long> tableStartTime;
    private Map<Integer, JButton> tableButtons;
    private Timer timer;

    public RestaurantApp() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("Quản lý Nhà hàng");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(4, 3, 10, 10));

        initializeTables();
        startTimer();

        mainFrame.add(tablePanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private void initializeTables() {
        tableOrders = new HashMap<>();
        tableStatus = new HashMap<>();
        tableStartTime = new HashMap<>();
        tableButtons = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            tableOrders.put(i, "Chưa có đơn hàng");
            tableStatus.put(i, false);
            tableStartTime.put(i, 0L);

            JButton tableButton = createTableButton(i);
            tableButtons.put(i, tableButton);
            tablePanel.add(tableButton);
        }
    }

    private JButton createTableButton(int tableNumber) {
        JButton button = new JButton();
        updateTableButton(button, tableNumber);

        button.addActionListener(e -> toggleTableStatus(tableNumber, button));

        return button;
    }

    private void updateTableButton(JButton button, int tableNumber) {
        boolean isOccupied = tableStatus.get(tableNumber);
        long elapsedTime = isOccupied
                ? (System.currentTimeMillis() - tableStartTime.get(tableNumber)) / 1000
                : 0;

        String timeText = isOccupied ? formatTime(elapsedTime) : "Trống";
        button.setText("<html><center>Bàn " + tableNumber + "<br>" + timeText + "</center></html>");

        // Đổi màu nền
        if (isOccupied) {
            button.setBackground(new Color(173, 216, 230)); // Màu xanh dương nhạt (#ADD8E6)
        } else {
            button.setBackground(Color.WHITE);
        }

        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    private void toggleTableStatus(int tableNumber, JButton button) {
        boolean isOccupied = tableStatus.get(tableNumber);

        if (!isOccupied) {
            tableStatus.put(tableNumber, true);
            tableStartTime.put(tableNumber, System.currentTimeMillis());
        } else {
            long elapsedTime = (System.currentTimeMillis() - tableStartTime.get(tableNumber)) / 1000;
            JOptionPane.showMessageDialog(mainFrame, "Bàn " + tableNumber + " đã được sử dụng trong " + formatTime(elapsedTime), "Thời gian sử dụng", JOptionPane.INFORMATION_MESSAGE);
            tableStatus.put(tableNumber, false);
            tableStartTime.put(tableNumber, 0L);
        }

        updateTableButton(button, tableNumber);
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            for (int i = 1; i <= 12; i++) {
                if (tableStatus.get(i)) {
                    updateTableButton(tableButtons.get(i), i);
                }
            }
        });
        timer.start();
    }

    private String formatTime(long seconds) {
        long mins = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantApp::new);
    }
}
