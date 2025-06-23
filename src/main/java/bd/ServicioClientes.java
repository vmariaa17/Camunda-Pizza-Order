package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServicioClientes {
	
	private String bd="entrega2arn";
	private String usuario="root";
	private String pass="root";
	
	public Cliente buscarCliente(String email) {
		Cliente cliente = null;
		Connection conn = Conexion.abrirConexion(bd, usuario, pass);
		if(conn !=null) {
			try {
				String SQL = "SELECT * FROM cliente where email = ?";
				PreparedStatement statement = conn.prepareStatement(SQL);
				statement.setString(1, email);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					cliente = new Cliente(
					result.getInt("id"),
					result.getString("nombre"),
				    result.getString("email"),
				    result.getLong("telefono")
				    );   
				}
				result.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				Conexion.cerrarConexion();
			}
		}
		return cliente;
	}
	
	public int insertarCliente(String nombre, String email, long telefono ){ 
		int idGenerado = -1;
		Connection conn = Conexion.abrirConexion(bd, usuario, pass);
		if(conn !=null) {
			try {
				String SQL = "INSERT INTO cliente (nombre, email, telefono) VALUES (?,?,?)";
				PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1,nombre);
				statement.setString(2,email);
				statement.setLong(3,telefono);
				statement.executeUpdate();
				ResultSet keys = statement.getGeneratedKeys();
				if (keys.next()) {
					idGenerado = keys.getInt(1);
				}
				keys.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally { 
				Conexion.cerrarConexion();
			}
		}
		return idGenerado;
	}
}
