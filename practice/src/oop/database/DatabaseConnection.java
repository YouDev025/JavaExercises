package oop.database;

// ─── Abstract base class ──────────────────────────────────────────────────────
public abstract class DatabaseConnection {

    protected String host;
    protected int    port;
    protected String dbName;
    protected boolean connected = false;

    public DatabaseConnection(String host, int port, String dbName) {
        this.host   = host;
        this.port   = port;
        this.dbName = dbName;
    }

    // ── Abstract methods — every driver MUST implement these ─────────────────
    public abstract void connect();
    public abstract void disconnect();

    // ── Concrete shared method ───────────────────────────────────────────────
    public void showStatus() {
        System.out.println("------------------------------");
        System.out.println("Host      : " + host);
        System.out.println("Port      : " + port);
        System.out.println("Database  : " + dbName);
        System.out.println("Status    : " + (connected ? "Connected ✔" : "Disconnected ✘"));
        System.out.println("------------------------------");
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public boolean isConnected() { return connected; }
    public String  getDbName()   { return dbName;    }
}