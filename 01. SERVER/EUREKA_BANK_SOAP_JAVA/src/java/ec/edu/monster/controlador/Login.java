package ec.edu.monster.controlador;

import ec.edu.monster.db.AccesoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Login {
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());

    public boolean IniciarSesion(String usuario, String contrasena) {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            String sql = "SELECT COUNT(*) FROM credenciales WHERE usuario = ? AND contrasena = MD5(?)";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, contrasena);
            ResultSet rs = pstm.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
                LOGGER.info("Login query result for user " + usuario + ": count=" + count + " at 07:22 PM -05, May 19, 2025");
            } else {
                LOGGER.warning("No result from login query for user " + usuario + " at 07:22 PM -05, May 19, 2025");
            }
            rs.close();
            pstm.close();
            return count > 0;
        } catch (SQLException e) {
            LOGGER.severe("SQL Error during login at 07:22 PM -05, May 19, 2025: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                LOGGER.warning("Error closing connection: " + e.getMessage());
            }
        }
    }
}