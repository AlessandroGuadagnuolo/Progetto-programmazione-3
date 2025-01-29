import java.sql.Connection;
import java.sql.SQLException;

public class MainFrame {

    public static void main(String[] args) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();

    }
}