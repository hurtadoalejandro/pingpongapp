package servicios;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageReceiver {
	private static final String RPC_QUEUE_NAME = "rpc_queue";

	private ServiciosAdministradorMensajes objServiciosAdministradorMensajes;

	public MessageReceiver(ServiciosAdministradorMensajes objServiciosAdministradorMensajes) {
		this.objServiciosAdministradorMensajes = objServiciosAdministradorMensajes;
	}

	public void receiveMessage() throws java.io.IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		Connection connection = null;
		try {
			connection = factory.newConnection();
			final Channel channel = connection.createChannel();

			channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

			channel.basicQos(1);

			System.out.println(" [x] Esperando solicitudes RPC en Pong App");

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
							.correlationId(properties.getCorrelationId()).build();

					String response = "";

					try {
						String message = new String(body, "UTF-8");

						System.out.println(" message = (" + message + ")");
						if (message != null && message.equalsIgnoreCase("ping")) {
							response += objServiciosAdministradorMensajes.ejecutarPing();
						} else if (message != null && message.equalsIgnoreCase("estadisticas")) {
							response += objServiciosAdministradorMensajes.getEstadisticas();
						}

					} catch (RuntimeException e) {
						System.out.println(" [.] " + e.toString());
					} finally {
						channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
						channel.basicAck(envelope.getDeliveryTag(), false);
						// RabbitMq consumer worker thread notifies the RPC server owner thread
						synchronized (this) {
							this.notify();
						}
					}
				}
			};

			channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
			// Wait and be prepared to consume the message from RPC client.
			while (true) {
				synchronized (consumer) {
					try {
						consumer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (IOException _ignore) {
				}
		}
	}
	
	public static void main(String[] args) {
		MessageReceiver objMessageReceiver = new MessageReceiver( new ServiciosAdministradorMensajes() );
		try {
			objMessageReceiver.receiveMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
