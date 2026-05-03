package oop.database;

// ─── MySQL implementation ─────────────────────────────────────────────────────
public class MySQLConnection extends DatabaseConnection {

    private String username;

    public MySQLConnection(String host, int port, String dbName, String username) {
        super(host, port, dbName);
        this.username = username;
    }

    @Override
    public void connect() {
        System.out.println("[MySQL] Connecting to " + dbName + " at " + host + ":" + port);
        System.out.println("[MySQL] Authenticating user: " + username);
        System.out.println("[MySQL] Using InnoDB engine — connection established!");
        connected = true;
    }

    @Override
    public void disconnect() {
        if (!connected) {
            System.out.println("[MySQL] No active connection to close.");
            return;
        }
        System.out.println("[MySQL] Committing pending transactions...");
        System.out.println("[MySQL] Closing connection to " + dbName + ".");
        connected = false;
    }
}