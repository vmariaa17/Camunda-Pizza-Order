package entregaARN2;

import bd.Conexion;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;

public class GuardarPedido implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        try {
            Object idObj = execution.getVariable("id");
            Object idPizzaObj = execution.getVariable("idPizza");
            Object cantidadObj = execution.getVariable("cantidad");
            Object totalObj = execution.getVariable("totalPedido");
            Object descuentoObj = execution.getVariable("descuentoAplicado");
            Object fechaObj = execution.getVariable("fecha");

            if (idObj == null || idPizzaObj == null || cantidadObj == null || totalObj == null) {
                System.out.println(" Faltan variables obligatorias para guardar el pedido.");
                return;
            }

            int idCliente = ((Number) idObj).intValue();
            int idPizza = ((Number) idPizzaObj).intValue();
            int cantidad = ((Number) cantidadObj).intValue();
            double total = ((Number) totalObj).doubleValue();
            double descuento = descuentoObj != null ? ((Number) descuentoObj).doubleValue() : 0;
            Timestamp fecha = (fechaObj != null) ?
                    Timestamp.valueOf(fechaObj.toString() + " 00:00:00") :
                    Timestamp.valueOf(LocalDate.now().atStartOfDay());

            Connection conn = Conexion.abrirConexion("entrega2arn", "root", "root");

            String sql = "INSERT INTO pedidos (id_cliente, id_pizza, cantidad, precio_total, descuento, fecha) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idPizza);
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, total);
            stmt.setDouble(5, descuento);
            stmt.setTimestamp(6, fecha);

            int rows = stmt.executeUpdate();
            System.out.println(" Pedido insertado. Filas afectadas: " + rows);

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(" Error al guardar pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }
}