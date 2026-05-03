package oop.database;

// ─── PostgreSQL implementation ────────────────────────────────────────────────
public class PostgreSQLConnection extends DatabaseConnection {

    private String schema;

    public PostgreSQLConnection(String host, int port, String dbName, String schema) {
        super(host, port, dbName);
        this.schema = schema;
    }

    @Override
    public void connect() {
        System.out.println("[PostgreSQL] Connecting to " + dbName + " at " + host + ":" + port);
        System.out.println("[PostgreSQL] Loading schema: " + schema);
        System.out.println("[PostgreSQL] SSL handshake complete — connection established!");
        connected = true;
    }

    @Override
    public void disconnect() {
        if (!connected) {
            System.out.println("[PostgreSQL] No active connection to close.");
            return;
        }
        System.out.println("[PostgreSQL] Releasing connection pool...");
        System.out.println("[PostgreSQL] Closing connection to " + dbName + ".");
        connected = false;
    }
}