import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

public class VisualInterface extends JFrame {
    private JPanel mainPanel;
    private JTextField fileTextField;
    private JButton loadButton;
    private JTable arrayTable;
    private JLabel errorLabel;

    public VisualInterface() {

        setTitle("Task 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        fileTextField = new JTextField();
        loadButton = new JButton("Load");
        arrayTable = new JTable();
        errorLabel = new JLabel();

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadArrayFromFile();
            }
        });

        mainPanel.add(fileTextField, BorderLayout.NORTH);
        mainPanel.add(loadButton, BorderLayout.CENTER);
        mainPanel.add(arrayTable.getTableHeader(), BorderLayout.WEST);
        mainPanel.add(arrayTable, BorderLayout.SOUTH);
        mainPanel.add(errorLabel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);
    }

    private void loadArrayFromFile() {
        String filename = fileTextField.getText();
        try {
            int[][] A = readArrayFromFile(filename);
            displayArray(A);
            errorLabel.setText("");
        } catch (FileNotFoundException ex) {
            errorLabel.setText("File not found");
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            errorLabel.setText("Invalid file format");
            ex.printStackTrace();
        } catch (CustomException ex) {
            errorLabel.setText(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private int[][] readArrayFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int n = scanner.nextInt();

        int[][] A = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = scanner.nextInt();
            }
        }

        scanner.close();

        return A;
    }

    private void displayArray(int[][] A) {
        int n = A.length;
        String[] columnNames = new String[n + 1];
        for (int i = 0; i < n; i++) {
            columnNames[i] = "A[" + i + "]";
        }
        columnNames[n] = "S: ";

        String[][] rowData = new String[n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rowData[i][j] = String.valueOf(A[i][j]);
            }
        }

        double[] B = new double[n];
        double a = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                a += A[i][j];
            }
            B[i] = a / n;
            a = 0;
        }

        for (int i = 0; i < n; ++i)
        {
            rowData[i][n] = String.valueOf(B[i]);
        }

        arrayTable.setModel(new DefaultTableModel(rowData, columnNames));




    }



    private class CustomException extends ArithmeticException {
        public CustomException(String message) {
            super(message);
        }
    }

}
