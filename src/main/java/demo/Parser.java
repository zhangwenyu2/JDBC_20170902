package demo;

import com.mysql.jdbc.Driver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Parser {

    public static void main(String[] args) throws SQLException {
        new Driver();
        try (
                BufferedReader reader = new BufferedReader(new FileReader("ip.txt"));
                Connection connection = DriverManager.getConnection("jdbc:mysql:///", "root", "system");
        ) {
            connection.setAutoCommit(false);
            String line;
            PreparedStatement preparedStatement = null;
            int counter = 0;
            String sql = "INSERT INTO db.ip VALUE(NULL, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            while ((line = reader.readLine()) != null) {
                String min = line.split("\\s+")[0];
                String max = line.split("\\s+")[1];
                String loc = line.replaceFirst(min, "").replaceFirst(max, "").trim();

                preparedStatement.setString(1, min);
                preparedStatement.setString(2, max);
                preparedStatement.setString(3, loc);
                preparedStatement.addBatch(); // add batch: 批处理\ [bætʃ]

                if (counter++ == 10000) {
                    System.out.println(counter);
                    preparedStatement.executeBatch();
                    counter = 0;
                }
            }
            if (preparedStatement != null) {
                System.out.println(counter);
                preparedStatement.executeBatch();
            }
            connection.commit();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}