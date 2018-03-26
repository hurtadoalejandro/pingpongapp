package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AdministradorMensajes {

	private ArrayList<Mensaje> lstMensaje;

	public int getMensajesRecibidos() {
		int iReceivedPingMessages = 0;

		// Cargamos el archivo donde se guarda la cuenta de mensajes recibidos
		FileReader objFileReader = null;
		BufferedReader objBufferedReader = null;
		PrintWriter objWriter = null;

		try {
			File objFile = new File("/Users/ahurtado/Documents/ReceivedPingMessages.ppf");
			if (!objFile.exists()) {
				objFile.createNewFile();
				objWriter = new PrintWriter(new FileWriter(objFile));

				// Si no existía el archivo y tuvimos que crearlo, entonces guardamos un 0
				objWriter.println(0);
			}

			if (objWriter != null) {
				// Cerramos el flujo como buena práctica
				objWriter.close();
			}

			// Abrimos el flujo
			objFileReader = new FileReader(objFile);
			objBufferedReader = new BufferedReader(objFileReader);

			// Leemos la información guardada en el archivo
			String initial = objBufferedReader.readLine();

			// Hacemos el parse para obtener un valor entero
			iReceivedPingMessages = Integer.parseInt(initial);
		} catch (Exception error) {

			if (objWriter != null) {
				// Cerramos el flujo como buena práctica
				objWriter.close();
			}

			error.printStackTrace();
		}

		if (objBufferedReader != null) {
			try {
				// Cerramos el flujo como buena práctica
				objBufferedReader.close();
			} catch (IOException error) {
				error.printStackTrace();
			}
		}

		return iReceivedPingMessages;
	}

	public int getMensajesRespondidos() {
		int iRepliedPongMessages = 0;

		// Cargamos el archivo donde se guarda la cuenta de mensajes recibidos
		FileReader objFileReader = null;
		BufferedReader objBufferedReader = null;
		PrintWriter objWriter = null;

		try {
			File objFile = new File("/Users/ahurtado/Documents/RepliedPongMessages.ppf");
			if (!objFile.exists()) {
				objFile.createNewFile();
				objWriter = new PrintWriter(new FileWriter(objFile));

				// Si no existía el archivo y tuvimos que crearlo, entonces guardamos un 0
				objWriter.println(0);
			}

			if (objWriter != null) {
				// Cerramos el flujo como buena práctica
				objWriter.close();
			}

			// Abrimos el flujo
			objFileReader = new FileReader(objFile);
			objBufferedReader = new BufferedReader(objFileReader);

			// Leemos la información guardada en el archivo
			String initial = objBufferedReader.readLine();

			// Hacemos el parse para obtener un valor entero
			iRepliedPongMessages = Integer.parseInt(initial);
		} catch (Exception error) {

			if (objWriter != null) {
				// Cerramos el flujo como buena práctica
				objWriter.close();
			}

			error.printStackTrace();
		}

		if (objBufferedReader != null) {
			try {
				// Cerramos el flujo como buena práctica
				objBufferedReader.close();
			} catch (IOException error) {
				error.printStackTrace();
			}
		}

		return iRepliedPongMessages;
	}

	public Mensaje crearMensajePong() {
		int iRepliedPongMessages = this.getMensajesRespondidos();
		int iReceivedPongMessages = this.getMensajesRecibidos();

		System.out.println("1) iReceivedPongMessages = " + iReceivedPongMessages);
		System.out.println("1) iRepliedPongMessages = " + iRepliedPongMessages);

		// Creamos una nueva instancia de Mensaje
		Mensaje objMensaje = null;

		
		PrintWriter objWriter = null, objWriter2 = null;

		try {
			FileOutputStream fileOS = new FileOutputStream("/Users/ahurtado/Documents/ReceivedPingMessages.ppf");
			objWriter = new PrintWriter(fileOS, true);

			// Actualizamos el número de mensajes pong recibidos
			objWriter.println(++iReceivedPongMessages);
			objWriter.flush();

			if (objWriter != null) {
				// Cerramos el flujo como buena práctica
				objWriter.close();
			}

			// Creamos una nueva instancia de Mensaje
			objMensaje = new Mensaje(Estado.RECIBIDO, "pong #" + iReceivedPongMessages);

			FileOutputStream fileOS2 = new FileOutputStream("/Users/ahurtado/Documents/RepliedPongMessages.ppf");

			objWriter2 = new PrintWriter(fileOS2, true);

			// Actualizamos el número de mensajes pong recibidos
			objWriter2.println(++iRepliedPongMessages);
			objWriter2.flush();

			if (objWriter2 != null) {
				// Cerramos el flujo como buena práctica
				objWriter2.close();
			}

			System.out.println("2) iReceivedPongMessages = " + iReceivedPongMessages);
			System.out.println("2) iRepliedPongMessages = " + iRepliedPongMessages);

		} catch (FileNotFoundException error) {
			System.out.println("Error: " + error.getMessage());

			objMensaje = new Mensaje(Estado.RECIBIDO, "Error: " + error.getMessage());
		}

		return objMensaje;
	}

	/*public static void main(String[] args) {
		AdministradorMensajes objAdministradorMensajes = new AdministradorMensajes();
		Mensaje objMensaje = objAdministradorMensajes.crearMensajePong();
	}*/
}
