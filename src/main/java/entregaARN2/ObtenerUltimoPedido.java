package entregaARN2;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import bd.Pedido;

public class ObtenerUltimoPedido implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        int idCliente = ((Number) execution.getVariable("id")).intValue();

        Pedido pedido = Pedido.buscarUltimoPedido(idCliente);

        if (pedido != null) {
            execution.setVariable("pedidoExiste", true);
            execution.setVariable("pizza", pedido.getNombrePizza());
            execution.setVariable("cantidad", pedido.getCantidad());
            execution.setVariable("precioTotal", pedido.getTotal());
            execution.setVariable("descuentoAplicado", pedido.getDescuento() * 100);
            execution.setVariable("fecha", pedido.getFecha().toString());
        } else {
            execution.setVariable("pedidoExiste", false);
        }
    }
}