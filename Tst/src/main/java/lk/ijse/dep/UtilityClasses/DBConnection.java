package lk.ijse.dep.UtilityClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection objref;
    private static Connection con;

    private DBConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/simplepos","root","");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance(){
        if (objref == null){
            objref = new DBConnection();
        }
        return objref;
    }

    public Connection getConnection(){
        return con;

    }

}
