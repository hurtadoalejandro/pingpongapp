package servicios;

import modelo.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

@Path("/ServiciosAdministradorMensaje")
public class ServiciosAdministradorMensajes {
	private static final long serialVersionUID = 1L;

	private static final BlockingQueue<AsyncResponse> suspended = new ArrayBlockingQueue<AsyncResponse>(5);

	private AdministradorMensajes objAdministradorMensajes;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServiciosAdministradorMensajes() {
		super();
		// TODO Auto-generated constructor stub

		this.objAdministradorMensajes = new AdministradorMensajes();
	}

	@GET
	@Path("/estadisticas")
	@Produces(MediaType.APPLICATION_JSON)
	public List getEstadisticas(@Suspended AsyncResponse asyncResponse) throws InterruptedException {
		suspended.put(asyncResponse);

		List lstRespuesta = new ArrayList();

		new Thread(new Runnable() {
			@Override
			public void run() {

				lstRespuesta.add("" + objAdministradorMensajes.getMensajesRecibidos());
				lstRespuesta.add("" + objAdministradorMensajes.getMensajesRespondidos());

				asyncResponse.resume(lstRespuesta);
			}

		}).start();

		return lstRespuesta;
	}

	@POST
	@Path("/ejecutar/ping")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML })
	public List ejecutarPing(@Suspended AsyncResponse asyncResponse) throws InterruptedException {
		//final AsyncResponse asyncResponse = suspended.take();
		//asyncResponse.resume(message); // resumes the processing of one GET request
		
		List lstRespuesta = new ArrayList();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {

				Mensaje objMensaje = objAdministradorMensajes.crearMensajePong();
				
				lstRespuesta.add(objMensaje.getMensaje());

				asyncResponse.resume(lstRespuesta);
			}

		}).start();
		
		return lstRespuesta;
	}
	
	public String getEstadisticas()
	{
		return "[recibidos: " + objAdministradorMensajes.getMensajesRecibidos() +
				", respondidos: " + objAdministradorMensajes.getMensajesRespondidos() + "]";
	}
	
	public String ejecutarPing()
	{
		Mensaje objMensaje = objAdministradorMensajes.crearMensajePong();
		
		return "[mensaje: " + objMensaje.getMensaje() + "]";
	}

}
