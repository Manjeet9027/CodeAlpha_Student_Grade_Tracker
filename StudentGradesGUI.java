import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradesGUI extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private DefaultTableModel tableModel;

    public StudentGradesGUI() {
        // Frame setup
        setTitle("🎓 Student Grades Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with background color
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#5D2F77")); // <-- purple background

        // Table
        String[] columns = {"Student Name", "Grade"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Table background white for contrast
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.GRAY);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#5D2F77")); // match background

        JButton addButton = new JButton("➕ Add Student");
        addButton.setBackground(new Color(46, 204, 113)); // green
        addButton.setForeground(Color.WHITE);

        JButton reportButton = new JButton("📊 Show Report");
        reportButton.setBackground(new Color(52, 152, 219)); // blue
        reportButton.setForeground(Color.WHITE);

        JButton exitButton = new JButton("❌ Exit");
        exitButton.setBackground(new Color(231, 76, 60)); // red
        exitButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        // Button Actions
        addButton.addActionListener(e -> addStudent());
        reportButton.addActionListener(e -> showReport());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void addStudent() {
        JTextField nameField = new JTextField(10);
        JTextField gradeField = new JTextField(5);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Enter Student Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Enter Grade (0-100):"));
        inputPanel.add(gradeField);

        int result = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                "Add New Student",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String gradeStr = gradeField.getText().trim();

            if (!name.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this,
                        "⚠ Name must contain only alphabets!",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double grade = Double.parseDouble(gradeStr);
                if (grade < 0 || grade > 100) {
                    JOptionPane.showMessageDialog(this,
                            "⚠ Grade must be between 0 and 100!",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                students.add(new Student(name, grade));
                tableModel.addRow(new Object[]{name, grade});
                JOptionPane.showMessageDialog(this, "✅ Student added successfully!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "⚠ Invalid grade! Enter a number between 0–100.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showReport() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ No students available!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = 0;
        double highest = students.get(0).grade;
        double lowest = students.get(0).grade;
        String topStudent = students.get(0).name;
        String lowStudent = students.get(0).name;

        for (Student s : students) {
            total += s.grade;
            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = total / students.size();

        String report = "📊 Summary Report\n\n" +
                "Total Students: " + students.size() + "\n" +
                "Average Score: " + String.format("%.2f", average) + "\n" +
                "Highest Score: " + highest + " (" + topStudent + ")\n" +
                "Lowest Score : " + lowest + " (" + lowStudent + ")";

        JOptionPane.showMessageDialog(this, report, "Summary Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradesGUI().setVisible(true);
        });
    }
}
