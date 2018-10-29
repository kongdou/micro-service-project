package fun.deepsky.docker.course;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fun.deepsky.docker.thrift.user.UserService;

@Component
public class ServiceProvider {

	@Value("${thrift.user.ip}")
	private String serverIp;

	@Value("${thrift.user.port}")
	private int serverPort;

	private enum ServiceType {
		USER, MESSAGE
	}

	public UserService.Client getUserService() {
		return getService(serverIp, serverPort, ServiceType.USER);
	}

	public <T> T getService(String serverIp, int serverPort, ServiceType serviceType) {
		// socket连接
		TSocket socket = new TSocket(serverIp, serverPort, 30000);
		TFramedTransport transPort = new TFramedTransport(socket);

		try {
			transPort.open();
		} catch (TTransportException e) {
			e.printStackTrace();
			return null;
		}
		TProtocol tbp = new TBinaryProtocol(transPort);

		TServiceClient client = null;
		switch (serviceType) {
		case USER:
			client = new UserService.Client(tbp);
			break;
		}
		return (T) client;
	}
}
