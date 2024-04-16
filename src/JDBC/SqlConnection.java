package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

    /** The connection */
    private Connection conn;

    private String dbUrl;
    private String dbName;
    private String dbPassword;

    
    public SqlConnection(String dbUrl, String dbName, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbName = dbName;
        this.dbPassword = dbPassword;
    }

    /**
     * Connect to the database.
     *
     * @return Database connection
     */
    public Connection connectToDB() {
        try {
            // Load the JDBC driver
            try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
            System.out.println("Driver definition succeed");

            // Build the database connection string with the schema path
            conn = DriverManager.getConnection(dbUrl, dbName, dbPassword); 
            if (conn.isValid(5)) { // Check if the connection is valid within 5 seconds
		        System.out.println("Connection is valid. Connected to the database.");
		        return conn ;
		    } else {
		        System.out.println("Connection is not valid. Not connected to the database.");
		    }
		System.out.println("SQL connection succeed");
        } catch (SQLException ex) {// handle any errors
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
	 	}
        return null;
    }
    
}

