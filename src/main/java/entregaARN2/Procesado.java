package entregaARN2;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class Procesado implements ExecutionListener {
	
	@Override
	public void notify(DelegateExecution execution) throws Exception{
			System.out.println("Pedido Procesado");
		
	}
}

