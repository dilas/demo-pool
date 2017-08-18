package com.example.demo;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
	@Autowired
	private ObjectPool<Connection> connectionPool;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	@Scope("singleton")
	public ObjectPool<Connection> createConnectionPool() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(1);
		config.setMaxIdle(1);
		config.setMaxTotal(3);
		config.setTestOnBorrow(true);
		
		return PoolUtils.erodingPool(new GenericObjectPool<>(new ConnectionPoolFactory(), config));
	}
	
	@GetMapping("/info")
	public String info() {
		Connection connection = null;
		
		try {
			connection = connectionPool.borrowObject();
		} catch (Exception e) {
			// reconnect?
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connectionPool.returnObject(connection);
				} catch (Exception e) {
					// ignored
				}
			}
		}
		
		return "OK";
	}
}
