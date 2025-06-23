package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicioPedido {

    private String bd = "entrega2arn";
    private String usuario = "root";
    private String pass = "root";

    public int insertarPedido(Pedido pedido) {

        Connection conn = Conexion.abrirConexion(bd, usuario, pass);
        if (conn != null) {
            String SQL = "INSERT INTO pedido (idCliente, fecha, idPizza, cantidad, descuento, total) VALUES (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement statement = conn.prepareStatement(SQL);
                statement.setInt(1, pedido.getIdCliente());
                statement.setTimestamp(2, pedido.getFecha());
                statement.setInt(3, pedido.getIdPizza());
                statement.setInt(4, pedido.getCantidad());
                statement.setDouble(5, pedido.getDescuento());
                statement.setDouble(6, pedido.getTotal());
                return statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Conexion.cerrarConexion();
            }
        }
        return 0;
    }
    public Pedido obtenerUltimoPedido(int idCliente) {
        Pedido pedido = null;
        Connection conn = Conexion.abrirConexion(bd, usuario, pass);
        if (conn != null) {
            try {
                String sql = "SELECT p.fecha, p.cantidad, p.total, p.descuento, x.nombre FROM pedido p JOIN pizza x ON p.idPizza = x.idPizza WHERE p.idCliente = ? ORDER BY p.fecha DESC LIMIT 1";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idCliente);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    pedido = new Pedido(
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getDouble("total"),
                        rs.getDouble("descuento"),
                        rs.getTimestamp("fecha")
                    );
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Conexion.cerrarConexion();
            }
        }
        return pedido;
    }
}