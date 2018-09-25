import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    public static void createDataBase(String name) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + name + ".s3db");
    }

    public static boolean checkDataBase(String name) { //проверка на существование базы данных с таким названием
        File file = new File(new File(".").getAbsolutePath());

        String[] array = file.list();

        int count = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(name + ".s3db")) count++;
        }

        if (count > 0) return false;
        else return true;
    }

    public static void openDataBase(String name) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + name);
    }

    public static void createTable() throws SQLException {

        statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'table' ('task' text, 'project' text, 'theme' text, 'type' text, 'priority' text, 'user' text, 'description' text);");
    }

    public static void writeDataBase(String task, String project, String theme, String type, String priority, String user, String description) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO 'table' (task ,project, theme, type, priority, user, description) VALUES (?, ?, ?, ?, ?, ?, ?)");

        stmt.setString(1, task);
        stmt.setString(2, project);
        stmt.setString(3, theme);
        stmt.setString(4, type);
        stmt.setString(5, priority);
        stmt.setString(6, user);
        stmt.setString(7, description);

        stmt.executeUpdate();
    }

    public static ArrayList<String[]> showDataBase() throws SQLException {

        ArrayList<String[]> list = new ArrayList<>();

        resultSet = statement.executeQuery("SELECT * FROM 'table'");

        while (resultSet.next()) {
            String[] array = {
                    "Задача: " + resultSet.getString("task"),
                    "Проект: " + resultSet.getString("project"),
                    "Тема: " + resultSet.getString("theme"),
                    "Тип: " + resultSet.getString("type"),
                    "Приоритет: " + resultSet.getString("priority"),
                    "Исполнитель: " + resultSet.getString("user"),
                    "Описание: " + resultSet.getString("description"),
                    " "
            };
            list.add(array);
        }
        return list;
    }

    public static ArrayList<String> showAllUsers() throws SQLException {

        ArrayList<String> list = new ArrayList<>();

        resultSet = statement.executeQuery("SELECT user FROM 'table'");

        while(resultSet.next()) {
            String user = resultSet.getString("user");
            list.add(user);
        }
        return list;
    }

    public static ArrayList<String> showAllProjects() throws SQLException {

        ArrayList<String> list = new ArrayList<>();

        resultSet = statement.executeQuery("SELECT project FROM 'table'");

        while (resultSet.next()) {
            String project = resultSet.getString("project");
            list.add(project);
        }
        return list;
    }

    public static boolean checkProject(String project) throws SQLException { //проверка на существование проекта
        int count = 0;

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT project FROM 'table' WHERE project LIKE ?");
        preparedStatement.setString(1, project);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            count++;
        }

        if (count > 0) return true;
        else return false;
    }

    public static ArrayList<String> showAllTasksInProject(String project) throws SQLException {
        ArrayList<String> list = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT task FROM 'table' WHERE project LIKE ?");
        ps.setString(1, project);

        resultSet = ps.executeQuery();

        while (resultSet.next()) {
            String task = resultSet.getString("task");
            list.add(task);
        }
        return list;
    }

    public static boolean checkUser(String user) throws SQLException { //проверка на существование пользователя
        int count = 0;

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT user FROM 'table' WHERE user LIKE ?");
        preparedStatement.setString(1, user);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            count++;
        }

        if (count > 0) return true;
        else return false;
    }

    public static ArrayList<String> showUserTasks(String user) throws SQLException {
        ArrayList<String> list = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT task FROM 'table' WHERE user LIKE ?");
        ps.setString(1, user);

        resultSet = ps.executeQuery();

        while (resultSet.next()) {
            String task = resultSet.getString("task");
            list.add(task);
        }
        return list;
    }

    public static boolean cheakTask(String task) throws SQLException { //проверка на существование задачи
        int count = 0;

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT task FROM 'table' WHERE task LIKE ?");
        preparedStatement.setString(1, task);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            count++;
        }

        if (count > 0) return true;
        else return false;
    }

    public static void delete (String task) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM 'table' WHERE task LIKE ?");
        ps.setString(1, task);
        ps.executeUpdate();
    }
}
