package br.com.senior.techicaltest.vendas.architecture;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public abstract class DatabaseTest {

    private static Connection connection;
    static Statement statement;

    @BeforeClass
    public static void init() {
        try {
            connection = DatabaseTest.getConnection();
            statement = connection.createStatement();
        } catch (SQLException se) {
            log.log(Level.ERROR, "Error connecting to database");
            throw new RuntimeException(se);
        }
    }

    @AfterClass
    public static void finish() {
        try {
            statement.close();
        } catch (SQLException e) {
            log.log(Level.TRACE, "It's not possible to close statement");
        }

        try {
            connection.close();
        } catch (SQLException e) {
            log.log(Level.TRACE, "It's not possible to close connection");
        }
    }

    private static Connection getConnection() {
       try {
           return DriverManager.getConnection(DatabaseTest.getUrl(), DatabaseTest.getUsername(), DatabaseTest.getPassword());
       } catch (SQLException se) {
           log.log(Level.ERROR, "It's not possible to connect to the database");
           throw new RuntimeException(se);
       }
    }

    private static String getUrl() {
        var databaseIp = "localhost:8080";
        return String.format("jdbc:h2:~/ecommerce", databaseIp);
    }

    private static String getUsername() {
        return "sa";
    }

    private static String getPassword() {
        return "password";
    }

    protected List<String> getParametersSql(String sql) {
        List<String> parameterList = new LinkedList<>();
        Matcher matcher = Pattern.compile("\\:([^\\s\\'.?!:=]+)").matcher(sql);
        while (matcher.find()) {
            parameterList.add(matcher.group().substring(1).replaceAll("[^_a-zA-Z0-9]+",""));
        }
        return parameterList;
    }

}
