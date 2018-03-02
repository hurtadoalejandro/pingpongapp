package pong;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Mensaje {
	
	private String mensaje;
	private Estado estado;
	
	public Mensaje(String strMensaje)
	{
		this.mensaje = strMensaje;
	}
}
