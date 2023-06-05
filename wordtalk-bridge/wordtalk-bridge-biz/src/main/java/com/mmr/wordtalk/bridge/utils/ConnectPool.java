package com.mmr.wordtalk.bridge.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectPool {
    private static final int MAX_POOL_SIZE = 10;
    private List<Connection> connectionPool;
    private static ConnectPool instance;
    
    private ConnectPool() {
        connectionPool = Collections.synchronizedList(new ArrayList<>());
        initializePool();
    }
    
    public static ConnectPool getInstance() {
        if (instance == null) {
            synchronized (ConnectPool.class) {
                if (instance == null) {
                    instance = new ConnectPool();
                }
            }
        }
        return instance;
    }
    
    private void initializePool() {
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }
    
    public Connection getConnection() {
        if (connectionPool.isEmpty()) {
            System.out.println("No available connections. Creating a new one.");
            return createConnection();
        }
        
        Connection connection = connectionPool.remove(0);
        System.out.println("Using an existing connection from the pool.");
        return connection;
    }
    
    public void releaseConnection(Connection connection) {
        if (connectionPool.size() < MAX_POOL_SIZE) {
            connectionPool.add(connection);
            System.out.println("Connection released and added back to the pool.");
        } else {
            System.out.println("Connection pool is full. Connection not added back to the pool.");
        }
    }
    
    private Connection createConnection() {
        // Code to create a new connection object
        return new Connection();
    }
    
    public static void main(String[] args) {
        ConnectPool pool = ConnectPool.getInstance();
        
        // Get connections from the pool
        Connection connection1 = pool.getConnection();
        Connection connection2 = pool.getConnection();
        
        // Release connections back to the pool
        pool.releaseConnection(connection1);
        pool.releaseConnection(connection2);
    }
}

class Connection {
    // Connection class implementation
    // ...
}
