package entregaARN2;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bd.Conexion;

public class ValidarPedido implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Object pizzaObj = execution.getVariable("pizza");
        Object cantidadObj = execution.getVariable("cantidad");

        System.out.println("📦 pizza = " + pizzaObj);
        System.out.println("📦 cantidad = " + cantidadObj);

        if (pizzaObj == null || cantidadObj == null) {
            execution.setVariable("pedidoValido", false);
            execution.setVariable("motivoRechazo", "Faltan datos del pedido.");
            return;
        }

        String nombrePizza = pizzaObj.toString();
        int cantidad;

        try {
            cantidad = Integer.parseInt(cantidadObj.toString());
        } catch (NumberFormatException e) {
            execution.setVariable("pedidoValido", false);
            execution.setVariable("motivoRechazo", "Cantidad no válida.");
            return;
        }

        if (cantidad < 1 || cantidad > 4) {
            execution.setVariable("pedidoValido", false);
            execution.setVariable("motivoRechazo", "Cantidad fuera del rango permitido (1-4).");
            return;
        }

        try (Connection conn = Conexion.abrirConexion("entrega2arn", "root", "root");
             PreparedStatement stmt = conn.prepareStatement("SELECT idPizza, precio FROM pizza WHERE nombre = ?")) {

            stmt.setString(1, nombrePizza);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                execution.setVariable("pedidoValido", true);
                execution.setVariable("idPizza", rs.getInt("idPizza"));
                execution.setVariable("precioUnidad", rs.getDouble("precio"));
            } else {
                execution.setVariable("pedidoValido", false);
                execution.setVariable("motivoRechazo", "La pizza '" + nombrePizza + "' no existe.");
            }

        } catch (Exception e) {
            execution.setVariable("pedidoValido", false);
            execution.setVariable("motivoRechazo", "Error en BD: " + e.getMessage());
        }
    }
}