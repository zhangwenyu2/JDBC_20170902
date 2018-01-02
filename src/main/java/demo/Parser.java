package demo;

import com.mysql.jdbc.Driver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


import com.mysql.jdbc.Driver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class Parser {

    private static final String URL = "jdbc:mysql:///";
    private static final String USER = "root";
    private static final String PASSWORD = "system";
    private static final String SQL = "INSERT INTO db.ip VALUE(NULL, ?, ?, ?)";

    private static List<IP> list;

    private static void parse() {
        list = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("ip.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String min = line.split("\\s+")[0];
                String max = line.split("\\s+")[1];
                String loc = line.replaceFirst(min, "").replaceFirst(max, "").trim();
                list.add(new IP(min, max, loc));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void save() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);) {
            new Driver();
            connection.setAutoCommit(false); // 关闭自动提交
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            int counter = 0;

            for (IP ip : list) {
                preparedStatement.setString(1, ip.getMin());
                preparedStatement.setString(2, ip.getMax());
                preparedStatement.setString(3, ip.getLoc());
                preparedStatement.addBatch(); // 加入批处理
                System.out.print(++counter + "\t");
            }
            preparedStatement.executeBatch(); // 执行批处理
            connection.commit(); // 手动提交
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        parse();
        save();
    }
}