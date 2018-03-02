package pong;

import java.io.BufferedReader;
import java.io.File;
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
}
