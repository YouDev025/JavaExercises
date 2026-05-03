package oop.database;

// ─── Main ─────────────────────────────────────────────────────────────────────
public class Main {

    public static void main(String[] args) {

        // Stored as abstract type → polymorphism
        DatabaseConnection mysql = new MySQLConnection(
                "localhost", 3306, "shop_db", "admin");

        DatabaseConnection postgres = new PostgreSQLConnection(
                "db.server.com", 5432, "analytics_db", "public");

        // ── MySQL demo ───────────────────────────────────────────────────────
        System.out.println("╔══════════════ MySQL ══════════════╗");
        mysql.connect();
        mysql.showStatus();
        mysql.disconnect();
        mysql.showStatus();

        System.out.println();

        // ── PostgreSQL demo ──────────────────────────────────────────────────
        System.out.println("╔══════════════ PostgreSQL ══════════╗");
        postgres.connect();
        postgres.showStatus();
        postgres.disconnect();
        postgres.showStatus();
    }
}