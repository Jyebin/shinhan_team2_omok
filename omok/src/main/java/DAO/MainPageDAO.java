package DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MainPageDAO {
    private Connection con;
    private PreparedStatement pstmt;
    private DataSource dataFactory;

    public MainPageDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/JSP";
            String dbID = "testuser";
            String dbPW = "test1234";
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
