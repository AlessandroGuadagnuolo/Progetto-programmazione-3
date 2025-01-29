import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            System.out.println("Connessione al database stabilita!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createTables() {
        String createUtenteTable = "CREATE TABLE IF NOT EXISTS Utente ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "cognome TEXT NOT NULL,"
                + "ruolo TEXT NOT NULL"
                + ");";

        String createStudenteTable = "CREATE TABLE IF NOT EXISTS Studente ("
                + "matricola TEXT PRIMARY KEY,"
                + "data_nascita TEXT,"
                + "residenza TEXT,"
                + "tasse BOOLEAN DEFAULT 0,"
                + "utente_id INTEGER NOT NULL,"
                + "FOREIGN KEY (utente_id) REFERENCES Utente(id)"
                + ");";

        String createDocenteTable = "CREATE TABLE IF NOT EXISTS Docente ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "utente_id INTEGER NOT NULL,"
                + "FOREIGN KEY (utente_id) REFERENCES Utente(id)"
                + ");";

        String createSegreteriaTable = "CREATE TABLE IF NOT EXISTS Segreteria ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "utente_id INTEGER NOT NULL,"
                + "FOREIGN KEY (utente_id) REFERENCES Utente(id)"
                + ");";

        String createEsameTable = "CREATE TABLE IF NOT EXISTS Esame ("
                + "codiceEsame TEXT PRIMARY KEY,"
                + "nome TEXT NOT NULL,"
                + "crediti INTEGER NOT NULL,"
                + "codice_corso TEXT NOT NULL,"
                + "docente_id INTEGER,"
                + "FOREIGN KEY (codice_corso) REFERENCES Corso(codice_corso) ON DELETE CASCADE,"
                + "FOREIGN KEY (docente_id) REFERENCES Docente(id) ON DELETE SET NULL"
                + ");";

        String createAppelloTable = "CREATE TABLE IF NOT EXISTS Appello ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "data DATE NOT NULL,"
                + "codiceEsame TEXT NOT NULL,"
                + "docente_id INTEGER NOT NULL,"
                + "FOREIGN KEY (codiceEsame) REFERENCES Esame(codiceEsame) ON DELETE CASCADE,"
                + "FOREIGN KEY (docente_id) REFERENCES Utente(id) ON DELETE CASCADE"
                + ");";

        String createPrenotazioniTable = "CREATE TABLE IF NOT EXISTS Prenotazioni ("
                + "matricola_studente VARCHAR(50),"
                + "codice_esame VARCHAR(20),"
                + "data_appello DATE,"
                + "PRIMARY KEY (matricola_studente, codice_esame, data_appello),"
                + "FOREIGN KEY (matricola_studente) REFERENCES Studente(matricola) ON DELETE CASCADE,"
                + "FOREIGN KEY (codice_esame, data_appello) REFERENCES Appello(codice_esame, data) ON DELETE CASCADE"
                + ");";



        String createCorsoTable = "CREATE TABLE IF NOT EXISTS Corso ("
                + "codice_corso TEXT PRIMARY KEY,"
                + "nome TEXT NOT NULL"
                + ")";

        String createTaxTable = "CREATE TABLE IF NOT EXISTS Tax ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "dataDiScadenza DATE NOT NULL,"
                + "costo REAL,"
                + "stato BOOLEAN,"
                + "FOREIGN KEY (matricola) REFERENCES Studente(matricola) ON DELETE CASCADE"
                + ");";

        try (Connection conn = connect()) {
            if (conn != null) {
                try (PreparedStatement stmt1 = conn.prepareStatement(createUtenteTable);
                        PreparedStatement stmt2 = conn.prepareStatement(createStudenteTable);
                        PreparedStatement stmt3 = conn.prepareStatement(createDocenteTable);
                        PreparedStatement stmt4 = conn.prepareStatement(createSegreteriaTable);
                        PreparedStatement stmt5 = conn.prepareStatement(createEsameTable);
                        PreparedStatement stmt6 = conn.prepareStatement(createAppelloTable);
                        PreparedStatement stmt7 = conn.prepareStatement(createPrenotazioniTable);
                        PreparedStatement stmt8 = conn.prepareStatement(createCorsoTable)
                        PreparedStatement stmt9 = conn.prepareStatement(createTaxTable)) {

                    stmt1.executeUpdate();
                    System.out.println("Tabella Utente creata o già esistente.");

                    stmt2.executeUpdate();
                    System.out.println("Tabella Studente creata o già esistente.");

                    stmt3.executeUpdate();
                    System.out.println("Tabella Docente creata o già esistente.");

                    stmt4.executeUpdate();
                    System.out.println("Tabella Segreteria creata o già esistente.");

                    stmt5.executeUpdate();
                    System.out.println("Tabella Esame creata o già esistente.");

                    stmt6.executeUpdate();
                    System.out.println("Tabella Appello creata o già esistente.");

                    stmt7.executeUpdate();
                    System.out.println("Tabella Prenotazione creata o già esistente.");

                    stmt8.executeUpdate();
                    System.out.println("Tabella Corso creata o già esistente.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:db.sqlite");
    }
}