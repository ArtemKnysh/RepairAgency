package com.epam.rd.java.basic.repairagency.service.base;

import com.epam.rd.java.basic.repairagency.factory.ServiceFactory;
import com.epam.rd.java.basic.repairagency.util.FactoryUtil;
import com.epam.rd.java.basic.repairagency.util.db.ConnectionManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ServiceTest {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test-repair-agency";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static final String URL_CONNECTION = DB_URL + ";user=" + USER + ";password=" + PASS + ";";
    protected static ServiceFactory serviceFactory;
    private static MockedStatic<ConnectionManager> connectionManagerMockedStatic;

    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_CONNECTION);
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        Class.forName(JDBC_DRIVER);
        try (Connection con = getConnection();
             Statement statement = con.createStatement()) {
            String initSQL = String.join("", Files.readAllLines(Paths.get("h2-scripts/init.sql")));
            statement.executeUpdate(initSQL);
        }
        final String rootPackageName = "com.epam.rd.java.basic.repairagency";
        serviceFactory = FactoryUtil.buildServiceFactory(rootPackageName);
        ConnectionManager connectionManagerMock = Mockito.mock(ConnectionManager.class);
        connectionManagerMockedStatic = Mockito.mockStatic(ConnectionManager.class);
        connectionManagerMockedStatic.when(ConnectionManager::getInstance).thenReturn(connectionManagerMock);
        Mockito.when(connectionManagerMock.getConnection()).thenAnswer(invocationOnMock -> getConnection());
    }

    @AfterClass
    public static void afterClass() throws Exception {
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = con.createStatement()) {
            String sql = "DELETE FROM feedback;" +
                    "DELETE FROM repair_request;" +
                    "DELETE FROM account_transaction;" +
                    "DELETE FROM user;" +
                    "DELETE FROM role;" +
                    "DELETE FROM repair_request_status;";
            statement.executeUpdate(sql);
        }
        connectionManagerMockedStatic.close();
    }

    @After
    public void tierDown() throws Exception {
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = con.createStatement()) {
            String sql = "DELETE FROM feedback;" +
                    "DELETE FROM repair_request;" +
                    "DELETE FROM account_transaction;" +
                    "DELETE FROM user;";
            statement.executeUpdate(sql);
        }
    }

}
