package modelo;

public class Mensaje {
	private String contenido;

	private Resultado resultado;

	public Mensaje(String contenido) {
		this.contenido = contenido;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
}
