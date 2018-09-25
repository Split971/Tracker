import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class Frame {

    JFrame startFrame = new JFrame("Tracker");

    JButton createDataBaseButton = new JButton("Создать новую базу данных");
    JButton openDataBaseButton = new JButton("Открыть существующую базу данных");
    ///////////////////////////////////////////////////////////////////////////////////////////
    JFrame mainFrame = new JFrame("Tracker");

    JButton createTaskButton = new JButton("Создать новую задачу");
    JButton getDataButton = new JButton("Вывести все данные");
    JButton getAllUsersButton = new JButton("Вывести список всех пользователей");
    JButton getAllProjectsButton = new JButton("Вывести список всех проектов");
    JButton getAllTasksInProjectButton = new JButton("Вывести список всех задач, в указанном вами проекте");
    JButton getUserInTaskButton = new JButton("Вывести список всех задач, назначенных на конкретного исполнителя");
    JButton deleteButton = new JButton("Удалить задачу");
    JButton backButton = new JButton("Назад");
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    JFrame createFrame = new JFrame("Tracker");

    JButton createButton = new JButton("Создать");
    JButton cancelButton = new JButton("Отмена");

    JLabel taskLabel = new JLabel("Название задачи");
    JLabel projectLabel = new JLabel("Проект задачи");
    JLabel themeLabel = new JLabel("Тема задачи");
    JLabel typeLabel = new JLabel("Тип задачи");
    JLabel priorityLabel = new JLabel("Приоритет задачи");
    JLabel userLabel = new JLabel("Исполнитель задачи");
    JLabel descriptionLabel = new JLabel("Описание задачи");

    JTextField taskField = new JTextField(50);
    JTextField projectField = new JTextField(50);
    JTextField themeField = new JTextField(50);
    JTextField typeField = new JTextField(50);
    JTextField priorityField = new JTextField(50);
    JTextField userField = new JTextField(50);
    JTextField descriptionField = new JTextField(50);
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    JFrame viewFrame = new JFrame("Tracker");

    JTextArea textArea = new JTextArea(100, 100);

    public void start() {
        startFrame.setSize(300, 120);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        startFrame.setLocationRelativeTo(null);

        createDataBaseButton.addActionListener(new createDataBaseButtonActionListener());
        openDataBaseButton.addActionListener(new openDataBaseButtonActionListener());

        startFrame.add(createDataBaseButton);
        startFrame.add(openDataBaseButton);

        startFrame.setVisible(true);
//////////////////////////////////////////////////////////////////////////////////////////////////
        mainFrame.setSize(1200, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(2, 0, 5, 12));
        mainFrame.setLocationRelativeTo(null);

        createTaskButton.addActionListener(new createTaskButtonActionListener());
        getDataButton.addActionListener(new getDataButtonActionListener());
        getAllUsersButton.addActionListener(new getAllUsersButtonActionListener());
        getAllProjectsButton.addActionListener(new getAllProjectsButtonActionListener());
        getAllTasksInProjectButton.addActionListener(new getAllTasksInProjectButtonActionListener());
        getUserInTaskButton.addActionListener(new getUserInTaskButtonActionListener());
        deleteButton.addActionListener(new deleteButtonActionListener());
        backButton.addActionListener(new backButtonActionListener());

        mainFrame.add(createTaskButton);
        mainFrame.add(getDataButton);
        mainFrame.add(getAllUsersButton);
        mainFrame.add(getAllProjectsButton);
        mainFrame.add(getAllTasksInProjectButton);
        mainFrame.add(getUserInTaskButton);
        mainFrame.add(deleteButton);
        mainFrame.add(backButton);

        mainFrame.setVisible(false);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        createFrame.setSize(600, 400);
        createFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
        createFrame.setLocationRelativeTo(null);

        createButton.addActionListener(new createButtonActionListener());
        cancelButton.addActionListener(new cancelButtonActionListener());

        createFrame.add(taskLabel);
        createFrame.add(taskField);
        createFrame.add(projectLabel);
        createFrame.add(projectField);
        createFrame.add(themeLabel);
        createFrame.add(themeField);
        createFrame.add(typeLabel);
        createFrame.add(typeField);
        createFrame.add(priorityLabel);
        createFrame.add(priorityField);
        createFrame.add(userLabel);
        createFrame.add(userField);
        createFrame.add(descriptionLabel);
        createFrame.add(descriptionField);
        createFrame.add(createButton);
        createFrame.add(cancelButton);

        createFrame.setVisible(false);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        viewFrame.setSize(600, 400);
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
        viewFrame.setLocationRelativeTo(null);

        viewFrame.add(textArea);

        viewFrame.setVisible(false);
    }

    public class createDataBaseButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String name = JOptionPane.showInputDialog(null,"Введите название базы данных");

            if (name.trim().length() != 0) {

                boolean check = DataBase.checkDataBase(name);

                if (check == true) {

                    try {
                        DataBase.createDataBase(name);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        DataBase.createTable();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    startFrame.setVisible(false);
                    mainFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "База данных с таким названием уже существует!");
                }
            }

            else {
                JOptionPane.showMessageDialog(null, "Пустая строка!");
            }
        }
    }

    public class openDataBaseButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            int ret = fileOpen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                String name = file.getAbsolutePath();

                if (name.endsWith(".s3db")) {
                    try {
                        DataBase.openDataBase(name);
                        DataBase.createTable();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    fileOpen.setVisible(false);
                    startFrame.setVisible(false);
                    mainFrame.setVisible(true);
                }

                else {
                    JOptionPane.showMessageDialog(null, "Неправильный файл");
                }
            }
        }
    }

    public class createTaskButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.setVisible(false);
            createFrame.setVisible(true);
        }
    }

    public class createButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String task = taskField.getText();
            String project = projectField.getText();
            String theme = themeField.getText();
            String type = typeField.getText();
            String priority = priorityField.getText();
            String user = userField.getText();
            String description = descriptionField.getText();

            if (task.trim().length() != 0 && project.trim().length() != 0 && theme.trim().length() != 0 && type.trim().length() != 0 && priority.trim().length() != 0 && user.trim().length() != 0 && description.trim().length() != 0 ) {

                try {
                    DataBase.writeDataBase(task, project, theme, type, priority, user, description);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Добавлено!");

                createFrame.setVisible(false);
                mainFrame.setVisible(true);
            }

            else {
                JOptionPane.showMessageDialog(null, "Заполните все строки!");
            }
        }
    }

    public class cancelButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            createFrame.setVisible(false);
            mainFrame.setVisible(true);
        }
    }

    public class getDataButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            viewFrame.setVisible(true);
            textArea.setText(null);

            ArrayList<String[]> list = null;

            try {
                list = DataBase.showDataBase();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            if (!list.isEmpty()) {

                for (String[] str : list) {
                    for (int i = 0; i < str.length; i++) {
                        textArea.append(str[i] + "\n");
                    }
                }
            }

            else {
                textArea.append("Данных нет");
            }
        }
    }

    public class getAllUsersButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            viewFrame.setVisible(true);
            textArea.setText(null);

            ArrayList<String> list = null;

            try {
                list = DataBase.showAllUsers();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            if (!list.isEmpty()) {

                for (String str : list) {
                    textArea.append(str + "\n");
                }
            }

            else {
                textArea.append("Пользователей нет");
            }
        }
    }

    public class getAllProjectsButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            viewFrame.setVisible(true);
            textArea.setText(null);

            ArrayList<String> list = null;

            try {
                list = DataBase.showAllProjects();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            if (!list.isEmpty()) {

                for (String str : list) {
                    textArea.append(str + "\n");
                }
            }

            else {
                textArea.append("Проектов нет");
            }
        }
    }

    public class getAllTasksInProjectButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<String> list = new ArrayList<>();
            boolean check = false;

            String project = JOptionPane.showInputDialog(null,"Введите название проекта");

            if (project.trim().length() != 0) {

                try {
                    check = DataBase.checkProject(project);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if (check == true) {
                    viewFrame.setVisible(true);
                    textArea.setText(null);

                    try {
                        list = DataBase.showAllTasksInProject(project);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    for (String str : list) {
                        textArea.append(str + "\n");
                    }
                }

                else {
                    JOptionPane.showMessageDialog(null, "Проекта не существует");
                }
            }

            else {
                JOptionPane.showMessageDialog(null, "Пустая строка!");
            }
        }
    }

    public class getUserInTaskButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<String> list = new ArrayList<>();
            boolean check = false;

            String user = JOptionPane.showInputDialog(null, "Назовите пользователя");

            if (user.trim().length() != 0) {

                try {
                    check = DataBase.checkUser(user);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if (check == true) {
                    textArea.setText(null);
                    viewFrame.setVisible(true);

                    try {
                        list = DataBase.showUserTasks(user);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    for (String str : list) {
                        textArea.append(str + "\n");
                    }
                }

                else {
                    JOptionPane.showMessageDialog(null, "Пользователя не существует");
                }
            }

            else {
                JOptionPane.showMessageDialog(null, "Пустая строка!");
            }
        }
    }

    public class deleteButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean check = false;

            String task = JOptionPane.showInputDialog(null,"Введите название задача");

            if (task.trim().length() != 0) {

                try {
                    check = DataBase.cheakTask(task);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if (check == true) {
                    try {
                        DataBase.delete(task);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "Удалено!");
                }

                else {
                    JOptionPane.showMessageDialog(null, "Задачи не существует");
                }
            }

            else {
                JOptionPane.showMessageDialog(null, "Пустая строка!");
            }
        }
    }

    public class backButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.setVisible(false);
            startFrame.setVisible(true);
        }
    }
}