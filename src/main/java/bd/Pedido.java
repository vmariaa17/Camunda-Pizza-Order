package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class Pedido {

    private int idPedido;
    private int idCliente;
    private int idPizza;            // Se usa al guardar el pedido
    private String nombrePizza;     // Se usa al consultar el último pedido
    private int cantidad;
    private double descuento;
    private double total;
    private Timestamp fecha;
    
    public Pedido(int idCliente, int idPizza, int cantidad, double descuento, double total, Timestamp fecha) {
        this.idCliente = idCliente;
        this.idPizza = idPizza;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.total = total;
        this.fecha = fecha;
    }
    
    public Pedido(String nombrePizza, int cantidad, double total, double descuento, Timestamp fecha) {
        this.nombrePizza = nombrePizza;
        this.cantidad = cantidad;
        this.total = total;
        this.descuento = descuento;
        this.fecha = fecha;
    }
    public static Pedido buscarUltimoPedido(int idCliente) {
        Pedido pedido = null;

        try (Connection conn = Conexion.abrirConexion("entrega2arn", "root", "root")) {
        	String sql = 
        		    "SELECT p.cantidad, p.precio_total, p.descuento, p.fecha, pi.nombre " +
        		    "FROM pedidos p " +
        		    "JOIN pizza pi ON p.id_pizza = pi.idPizza " +
        		    "WHERE p.id_cliente = ? " +
        		    "ORDER BY p.fecha DESC " +
        		    "LIMIT 1";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCliente);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombrePizza = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precioTotal = rs.getDouble("precio_total");
                double descuento = rs.getDouble("descuento");
                Timestamp fecha = rs.getTimestamp("fecha");

                pedido = new Pedido(nombrePizza, cantidad, precioTotal, descuento, fecha);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedido;
    }
    // Getters y setters
    public int getIdCliente() {
        return idCliente;
    }

    public int getIdPizza() {
        return idPizza;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }
    public String getNombrePizza() {
        return nombrePizza;
    }
    
    public Timestamp getFecha() {
        return fecha;
    }
}