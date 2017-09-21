package com.portfolio.elkeno_jones.mindgamesmaven.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionUtils {

    private final static Logger LOGGER = Logger.getLogger(ConnectionUtils.class.getName());

//	private static MessageResources messages 
//	 = MessageResources.getMessageResources("spring/data-access.properties");
    private static String USER_NAME_DB = "";
    private static String USER_PASSWORD_DB = "";
    private static String DB_NAME = "";
    private static String HOST_NAME = "";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
    }

    public static Connection getConnection() throws Exception {
        return getDBConnection();
    }

    public static Connection getDBConnection() throws Exception {
       // LOGGER.log(Level.INFO, "in getDBConnection");
        if (USER_NAME_DB.equals("")) {
            String propFileName = "data-access";
            ResourceBundle rb = ResourceBundle.getBundle(propFileName);
//            Enumeration<String> keys = rb.getKeys();
//            while (keys.hasMoreElements()) {
//                String key = keys.nextElement();
//                String value = rb.getString(key);
//                //System.out.println(key + ": " + value);
//            }
            USER_NAME_DB = rb.getString("jdbc.username");
            USER_PASSWORD_DB = rb.getString("jdbc.password");
            DB_NAME = rb.getString("jdbc.dbname");
            HOST_NAME = rb.getString("jdbc.host");
        }
//              Properties props = new Properties();
//              
//              InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//              	if (inputStream != null) {
//			props.load(inputStream);
//		} else {
//			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//		}
        Connection conn = null;
        String URL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME;
//        System.out.println("URL=" + URL);
//        System.out.println("User=" + USER_NAME_DB);
//        System.out.println("Pw=" + USER_PASSWORD_DB);
        try {
            conn = DriverManager.getConnection(URL, USER_NAME_DB, USER_PASSWORD_DB);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
