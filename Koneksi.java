import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    // URL, username, dan password untuk koneksi ke database
    private static final String URL = "jdbc:mysql://localhost:3306/dbkuispbo"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    // Method untuk mendapatkan koneksi ke database
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Register JDBC driver (opsional)
            Class.forName("com.mysql.cj.jdbc.Driver");
    
            // Membuka koneksi ke database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi ke database berhasil!"); // Indikator keberhasilan
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal: " + e.getMessage());
        }
        return connection;
    }

    // Method untuk menutup koneksi
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Koneksi ke database ditutup.");
            } catch (SQLException e) {
                System.out.println("Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }
}
