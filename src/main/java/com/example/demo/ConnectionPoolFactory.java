package com.example.demo;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ConnectionPoolFactory extends BasePooledObjectFactory<Connection> {

	@Override
	public Connection create() throws Exception {
		System.out.println("ConnectionPool.create()");
		return new Connection();
	}

	@Override
	public PooledObject<Connection> wrap(Connection con) {
		System.out.println("ConnectionPool.wrap()");
		return new DefaultPooledObject<Connection>(con);
	}

	@Override
	public boolean validateObject(PooledObject<Connection> p) {
		System.out.println("ConnectionPool.validateObject()");
		return p.getObject().isValid();
	}
	
	@Override
	public void destroyObject(PooledObject<Connection> p) throws Exception {
		System.out.println("ConnectionPool.destroyObject()");
		p.getObject().close();
		super.destroyObject(p);
	}
}
