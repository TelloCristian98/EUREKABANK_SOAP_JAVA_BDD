package ec.edu.monster.servicio;

import ec.edu.monster.db.AccesoDB;
import ec.edu.monster.modelo.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EurekaService {
    private static final Logger LOGGER = Logger.getLogger(EurekaService.class.getName());

    public List<Movimiento> leerMovimientos(String cuenta) {
        // Same as before, omitted for brevity
        Connection cn = null;
        List<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT m.chr_cuencodigo cuenta, m.int_movinumero nromov, m.dtt_movifecha fecha, " +
                     "t.vch_tipodescripcion tipo, t.vch_tipoaccion accion, m.dec_moviimporte importe " +
                     "FROM tipomovimiento t INNER JOIN movimiento m ON t.chr_tipocodigo = m.chr_tipocodigo " +
                     "WHERE m.chr_cuencodigo = ? ORDER BY m.dtt_movifecha DESC";
        try {
            cn = AccesoDB.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Movimiento rec = new Movimiento();
                rec.setCuenta(rs.getString("cuenta"));
                rec.setNromov(rs.getInt("nromov"));
                rec.setFecha(rs.getDate("fecha"));
                rec.setTipo(rs.getString("tipo"));
                rec.setAccion(rs.getString("accion"));
                rec.setImporte(rs.getDouble("importe"));
                lista.add(rec);
            }
            rs.close();
            LOGGER.info("Successfully retrieved " + lista.size() + " movements for account " + cuenta + " at 07:22 PM -05, May 19, 2025");
        } catch (SQLException e) {
            LOGGER.severe("Error retrieving movements at 07:22 PM -05, May 19, 2025: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                LOGGER.warning("Error closing connection: " + e.getMessage());
            }
        }
        return lista;
    }

    public void registrarDeposito(String cuenta, double importe, String codEmp) {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);
            String sql = "SELECT dec_cuensaldo, int_cuencontmov FROM cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                LOGGER.severe("Account " + cuenta + " does not exist or is not active at 07:22 PM -05, May 19, 2025");
                throw new SQLException("ERROR, account does not exist or is not active");
            }
            double saldo = rs.getDouble("dec_cuensaldo");
            int cont = rs.getInt("int_cuencontmov");
            LOGGER.info("Account " + cuenta + " found: saldo=" + saldo + ", cont=" + cont + " at 07:22 PM -05, May 19, 2025");
            rs.close();
            pstm.close();

            saldo += importe;
            cont++;
            sql = "UPDATE cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldo);
            pstm.setInt(2, cont);
            pstm.setString(3, cuenta);
            int rowsUpdated = pstm.executeUpdate();
            LOGGER.info("Updated account " + cuenta + ": rowsUpdated=" + rowsUpdated + ", new saldo=" + saldo + ", new cont=" + cont + " at 07:22 PM -05, May 19, 2025");
            pstm.close();

            sql = "INSERT INTO movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) VALUES(?, ?, SYSDATE(), ?, '003', ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            pstm.setInt(2, cont);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            int rowsInserted = pstm.executeUpdate();
            LOGGER.info("Inserted movement for account " + cuenta + ": rowsInserted=" + rowsInserted + " at 07:22 PM -05, May 19, 2025");
            cn.commit();
            LOGGER.info("Deposit of " + importe + " successfully registered for account " + cuenta + " at 07:22 PM -05, May 19, 2025");
        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (Exception el) {
                LOGGER.warning("Rollback failed: " + el.getMessage());
            }
            LOGGER.severe("SQL Error registering deposit at 07:22 PM -05, May 19, 2025: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            try {
                cn.rollback();
            } catch (Exception el) {
                LOGGER.warning("Rollback failed: " + el.getMessage());
            }
            LOGGER.severe("Unexpected error during deposit at 07:22 PM -05, May 19, 2025: " + e.getMessage());
            throw new RuntimeException("ERROR, in the deposit registration process, please try again later.");
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                LOGGER.warning("Error closing connection: " + e.getMessage());
            }
        }
    }
}