package manager;

import db.DBConnectionProvider;
import model.Task;
import model.TaskStatus;
import model.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
    private UserManager userManager = new UserManager();
    private Connection connection = DBConnectionProvider.getInstance().getConnection();


    public void addTask(Task task) {

        System.out.println("before saving user");
        System.out.println(task);
        String sql = "insert into task(name,description,deadline,status,user_id)VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, sdf.format(task.getDeadline()));
            ps.setString(4, task.getTaskStatus().name());
            ps.setInt(5, task.getUserId());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                task.setId(id);
            }
            System.out.println("task was added successfully");
            System.out.println(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        String sql = "select * from task";
        List<Task> tasks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt(1));
                task.setName(resultSet.getString(2));
                task.setDescription(resultSet.getString("description"));
                task.setDeadline(sdf.parse(resultSet.getString("deadline")));
                task.setTaskStatus(TaskStatus.valueOf(resultSet.getString("status")));
                task.setUserId(resultSet.getInt("user_id"));
                task.setUser(userManager.getUserById(task.getUserId()));
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void updateTaskStatus(int taskId,String newStatus ){
        String sql = "update task set status = ? where id  = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,newStatus);
            preparedStatement.setInt(2,taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTaskUser(int userId,String newUser ){
        String sql = "update task set user = ? where id  = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,newUser);
            preparedStatement.setInt(2,userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Task> getAllTasksUserId(int userId) {
        String sql = "select * from task where user_id = ?";
        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt(1));
                task.setName(resultSet.getString(2));
                task.setDescription(resultSet.getString("description"));
                task.setDeadline(sdf.parse(resultSet.getString("deadline")));
                task.setTaskStatus(TaskStatus.valueOf(resultSet.getString("status")));
                task.setUserId(resultSet.getInt("user_id"));
                task.setUser(userManager.getUserById(task.getUserId()));
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    public void deleteTaskById(int id) {
        String sql = "delete from task where id = " + id;
        List<Task> tasks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
