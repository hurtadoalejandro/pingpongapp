package servicios;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//import javax.json.JsonArray;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


@Path("/ServiciosMensaje")
public class ServiciosMensaje {

	private static final BlockingQueue<AsyncResponse> suspended = new ArrayBlockingQueue<AsyncResponse>(5);
	private String respuesta;

	@GET
	@Path("/consultar/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String consultarPings(@Suspended AsyncResponse asyncResponse) throws InterruptedException {
		// String respuesta = "";
		new Thread(new Runnable() {
			@Override
			public void run() {

				CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				try {
					// specify the host, protocol, and port
					HttpHost target = new HttpHost("localhost", 8080, "http");

					// specify the get request
					// HttpGet getRequest = new HttpGet("/PingApp/rest/ServiciosMensaje/ping");
					HttpGet getRequest = new HttpGet("/PongApp/rest/ServiciosAdministradorMensaje/estadisticas");

					System.out.println("Ejecutando la consulta en " + target);

					HttpResponse httpResponse = httpClient.execute(target, getRequest);
					HttpEntity entity = httpResponse.getEntity();

					System.out.println("----------------------------------------");
					System.out.println(httpResponse.getStatusLine());
					Header[] headers = httpResponse.getAllHeaders();
					for (int i = 0; i < headers.length; i++) {
						System.out.println(headers[i]);
					}
					System.out.println("----------------------------------------");

					if (entity != null) {
						respuesta = "La respuesta obtenida es: " + EntityUtils.toString(entity);

						asyncResponse.resume(respuesta);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();

		System.out.println(respuesta);

		return respuesta;
	}

	@POST
	@Path("/ejecutar/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String ejecutarPing(@Suspended AsyncResponse asyncResponse) throws InterruptedException {
		// String respuesta = "";

		new Thread(new Runnable() {
			@Override
			public void run() {
				CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				try {
					// specify the host, protocol, and port
					HttpHost target = new HttpHost("localhost", 8080, "http");

					// specify the get request
					// HttpGet getRequest = new HttpGet("/PingApp/rest/ServiciosMensaje/ping");
					HttpPost postRequest = new HttpPost("/PongApp/rest/ServiciosAdministradorMensaje/ejecutar/ping");

					System.out.println("Enviando el comando de ejecución en " + target);

					HttpResponse httpResponse = httpClient.execute(target, postRequest);
					HttpEntity entity = httpResponse.getEntity();

					System.out.println("----------------------------------------");
					System.out.println(httpResponse.getStatusLine());
					Header[] headers = httpResponse.getAllHeaders();
					for (int i = 0; i < headers.length; i++) {
						System.out.println(headers[i]);
					}
					System.out.println("----------------------------------------");

					if (entity != null) {
						respuesta = "La respuesta obtenida es: " + EntityUtils.toString(entity);

						asyncResponse.resume(respuesta);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();

		System.out.println(respuesta);

		return respuesta;
	}

	@GET
	@Path("/broker/consultar/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String consultarPingsMessageBroker(@Suspended AsyncResponse asyncResponse) throws InterruptedException {
		// String respuesta = "";

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					respuesta = MessageSender.sendMessage("estadisticas");
				} catch (Exception error) {
					respuesta = "Error: " + error.getMessage();
				}

				asyncResponse.resume(respuesta);
			}

		}).start();

		return respuesta;
	}

	@POST
	@Path("/broker/ejecutar/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String ejecutarPingBroker(@Suspended AsyncResponse asyncResponse) throws InterruptedException {
		// String respuesta = "";

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					respuesta = MessageSender.sendMessage("ping");
				} catch (Exception error) {
					respuesta = "Error: " + error.getMessage();
				}

				asyncResponse.resume(respuesta);
			}

		}).start();

		return respuesta;
	}

}
