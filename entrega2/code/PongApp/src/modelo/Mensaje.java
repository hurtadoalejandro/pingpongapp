package modelo;

public class Mensaje {
	
	private String mensaje;
	private Estado estado;
	
	public Mensaje(Estado estado, String strMensaje)
	{
		this.estado = estado;
		this.mensaje = strMensaje;
	}
	
	public Estado getEstado() {
		return this.estado;
	}
	
	public String getMensaje()
	{
		return this.mensaje;
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
