package uk.lazycat.shop.config;

import org.h2.tools.Server;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 測試用H2 server 方便不同DB工具測試
 * 測試用url jdbc:h2:tcp://localhost:9090/mem:testdb;DB_CLOSE_ON_EXIT=FALSE
 */
@Configuration
public class H2ServerConfig {

	@Value("${h2.tcp.server.enabled:false}")
	private boolean h2TcpServerEnabled;

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseaServer() throws SQLException {
		if (h2TcpServerEnabled) {
			return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
		}
		return null;
	}

}
