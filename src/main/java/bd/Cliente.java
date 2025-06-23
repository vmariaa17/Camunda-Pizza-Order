package bd;


public class Cliente {
	private Integer id;
	private String nombre;
	private String email;
	private long telefono;
	
	public Cliente(String nombre, String email, long telefono) {
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
	}
	
	public Cliente(int id, String nombre, String email, long telefono) {
		this(nombre, email, telefono);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getEmail() {
		return email;
	}
	public long getTelefono() {
		return telefono;
	}
}