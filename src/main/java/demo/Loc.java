package demo;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;


public class Loc {
    public static void main(String[] args) throws SQLException {
        new Driver();
        Connection connection = DriverManager.getConnection("jdbc:mysql:///?user=root&password=system");
        String sql = "SELECT loc FROM db.ip WHERE inet_aton(?) BETWEEN inet_aton(min) AND inet_aton(max)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        Scanner scanner = new Scanner(System.in);
        System.out.println("input ip address: ");
        String ip = scanner.nextLine();
        preparedStatement.setString(1, ip);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.println(resultSet.getString("loc"));

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}