import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IAMService {
    private static final String DB_URL = ConfigReader.getProperty("db.url");
    private static final String DB_USER = ConfigReader.getProperty("db.user");
    private static final String DB_PASSWORD = ConfigReader.getProperty("db.password");

    /**
     * Prüft, ob ein aktiver User ein bestimmtes Recht besitzt.
     */
    public boolean checkAccess(String username, String permissionName) {
        boolean hasAccess = false;

        String sql = "SELECT COUNT(*)\n"+
                "FROM users \n" +
                "JOIN user_roles ON users.user_id = user_roles.user_id \n" +
                "JOIN roles ON user_roles.role_id = roles.role_id  \n" +
                "JOIN role_permissions ON roles.role_id = role_permissions.role_id \n" +
                "JOIN permissions ON role_permissions.permission_id = permissions.permission_id \n" +
                "WHERE users.username = ? AND users.is_active = TRUE AND permissions.permission_name = ?;";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, permissionName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    hasAccess = (count > 0);
                }
            }

        } catch (Exception e) {
            System.err.println("Datenbankfehler beim Access Check: " + e.getMessage());
        }

        return hasAccess;
    }
}