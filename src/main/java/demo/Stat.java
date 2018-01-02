package demo;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;


public class Stat {

    private static final String[] CITIES = {"北京", "上海", "广州", "深圳"};


    public static void main(String[] args) throws SQLException {
        new Driver();
        Connection connection = DriverManager.getConnection("jdbc:mysql:///?user=root&password=system");
        String sql = "SELECT loc, inet_aton(max) - inet_aton(min) + 1 AS number FROM db.ip WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = null;

        Map<String, Integer> map = new LinkedHashMap<>();
        for (String city : CITIES) {
            map.put(city, 0);
        }

        for (int i = 1; i < 447298; i++) {
            preparedStatement.setInt(1, i);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String loc = resultSet.getString("loc");
            int number = resultSet.getInt("number");
            for (String city : CITIES) {
                if (loc.contains(city)) {
                    map.put(city, map.get(city) + number);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t-\t" + entry.getValue());
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}

/*
    北京	-	8613
    上海	-	7540
    广州	-	5891
    深圳	-	4461
 */