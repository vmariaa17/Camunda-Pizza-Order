package entregaARN2;

import bd.ServicioClientes;
import bd.Cliente;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class BuscarCliente implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String email = (String) execution.getVariable("email");

        if (email == null || email.isEmpty()) {
            execution.setVariable("clienteExiste", false);
            execution.setVariable("motivoRechazo", "El email no fue proporcionado.");
            return;
        }

        ServicioClientes servicio = new ServicioClientes();
        Cliente cliente = servicio.buscarCliente(email);

        if (cliente != null) {
        	System.out.println("Cliente Encontrado");
            execution.setVariable("clienteExiste", true);
            execution.setVariable("id", cliente.getId()); 
            execution.setVariable("nombre", cliente.getNombre());
            execution.setVariable("email", cliente.getEmail());
            execution.setVariable("telefono", cliente.getTelefono());
        } else {
        	System.out.println("Cliente con email '" + email + "' no encontrado en la base de datos.");
            execution.setVariable("clienteExiste", false);
        }
    }
}