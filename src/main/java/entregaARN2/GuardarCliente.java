package entregaARN2;


import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import bd.ServicioClientes;

public class GuardarCliente implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		String nombre = (String)delegateTask.getExecution().getVariable("nombre");
		String email = (String)delegateTask.getExecution().getVariable("email");
		Object telefonoObj = delegateTask.getExecution().getVariable("telefono");
		Long telefono = telefonoObj instanceof Number ? ((Number) telefonoObj).longValue() : Long.parseLong(telefonoObj.toString());
		
		
		ServicioClientes servicio = new ServicioClientes();
		int numClientes = servicio.insertarCliente(nombre, email, telefono);
		
		delegateTask.getExecution().setVariable("numClientes",  numClientes);
	}
}
