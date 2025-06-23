package entregaARN2;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CalcularPrecio implements JavaDelegate {
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String pizza = (String) execution.getVariable("pizza");
		long cantidad = ((Number) execution.getVariable("cantidad")).intValue();
		
		double precioUnidad = obtenerPrecioUnitario(pizza);
		double descuento = calcularDescuento(pizza, cantidad);
		
		double total = (precioUnidad * cantidad)*(1 - descuento);

		// Guardar variables clave para el resto del proceso
		execution.setVariable("precioUnidad", precioUnidad);
		execution.setVariable("totalPedido", total);
		execution.setVariable("descuento", (int) (descuento * 100));  // Guardado como int
		execution.setVariable("descuentoAplicado", (int) (descuento * 100) + "%");

		// Reasignar variables base para que estén disponibles en GuardarPedido
		execution.setVariable("pizza", pizza);
		execution.setVariable("cantidad", cantidad);
	}
	
	private double obtenerPrecioUnitario(String pizza) {
		switch(pizza) {
			case "Margarita": return 10;
			case "4 quesos":
			case "Barbacoa":
			case "Carbonara":
			case "Hawaiana": return 12;
			default: return 0;
		}
	}
	
	private double calcularDescuento(String pizza, long cantidad) {
		if (pizza.equals("Margarita")) {
			return 0.05;
		} else if (pizza.equals("4 quesos") || pizza.equals("Barbacoa") || pizza.equals("Carbonara")) {
			switch ((int) cantidad) {
				case 2: return 0.10;
				case 3: return 0.20;
				case 4: return 0.30;
			}
		}
		return 0;
	}
}