package cl.muruna.ejercicio.dto;

public class ErrorDTO {

	public ErrorDTO(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	private String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
