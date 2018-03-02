package pong.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pong.*;

/**
 * Servlet implementation class ServiciosAdministradorMensajes
 */
@WebServlet("/ServiciosAdministradorMensajes")
public class ServiciosAdministradorMensajes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdministradorMensajes objAdministradorMensajes;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServiciosAdministradorMensajes() {
		super();
		// TODO Auto-generated constructor stub
		
		this.objAdministradorMensajes = new AdministradorMensajes();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String strMensajesRecibidos = "<br />Mensajes recibidos: " + this.objAdministradorMensajes.getMensajesRecibidos();
		String strMensajesRespondidos = "<br />Mensajes respondidos: " + this.objAdministradorMensajes.getMensajesRespondidos();
		
		response.getWriter().append( strMensajesRecibidos );
		response.getWriter().append( strMensajesRespondidos );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
