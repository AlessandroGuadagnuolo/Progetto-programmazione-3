import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Docente extends Utente {
    private List<Esame> esamiInsegnati;
    private Mediator mediator;
    Scanner scanner;

    public Docente(String nome, String cognome,int id) {
        super(id,nome, cognome);
    }

    // visualizzo *solo* gli esami che insegna
    public List<Esame> getEsamiInsegnati(List<Esame> tuttiGliEsami) {
        esamiInsegnati = new ArrayList<>();
        for (Esame esame : tuttiGliEsami) {
            if (esame.getDocente().getCognome().equals(this.getCognome())) {
                esamiInsegnati.add(esame);
            }
        }
        return esamiInsegnati;
    }

    public void inserisciAppello(Connection conn) {
        try (Scanner scanner = new Scanner(System.in)) {
            // 1. Chiedere il nome dell'esame
            System.out.println("Inserisci il nome dell'esame:");
            String nomeEsame = scanner.nextLine();

            // 2. Verificare che l'esame esista e che appartenga al docente
            String queryEsame = "SELECT * FROM Esame WHERE nome = ? AND docente_id = ?";
            Esame esame = null;

            try (PreparedStatement stmt = conn.prepareStatement(queryEsame)) {
                stmt.setString(1, nomeEsame);
                stmt.setInt(2, this.getId()); // ID del docente che sta inserendo l'appello
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    esame = new Esame(
                            rs.getString("codiceEsame"),
                            rs.getString("nome"),
                            rs.getInt("crediti"),
                            this // Docente associato
                    );
                }
            }

            if (esame == null) {
                System.out.println("Errore: Esame non trovato o non insegnato da te.");
                return;
            }

            // 3. Chiedere la data dell'appello
            System.out.println("Inserisci la data dell'appello (formato: yyyy-MM-dd):");
            String dataInput = scanner.nextLine();
            Date dataAppello;

            try {
                dataAppello = Date.valueOf(dataInput); // Converte la stringa in oggetto Date
            } catch (IllegalArgumentException e) {
                System.out.println("Formato data non valido.");
                return;
            }

            // 4. Verificare che non esista già un appello per quella data e quell'esame
            String queryAppello = "SELECT 1 FROM Appello WHERE codiceEsame = ? AND data = ?";
            try (PreparedStatement stmt = conn.prepareStatement(queryAppello)) {
                stmt.setString(1, esame.getCodiceEsame());
                stmt.setDate(2, dataAppello);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Errore: Esiste già un appello per questo esame in questa data.");
                    return;
                }
            }

            // 5. Inserire l'appello nel database
            String inserisciAppello = "INSERT INTO Appello (codiceEsame, data, docente_id) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(inserisciAppello)) {
                stmt.setString(1, esame.getCodiceEsame());
                stmt.setDate(2, dataAppello);
                stmt.setInt(3, this.getId()); // ID del docente
                stmt.executeUpdate();
                System.out.println("Appello inserito con successo.");
            }

        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento dell'appello: " + e.getMessage());
        }
    }

    public void inserisciVoto() {
        // Seleziona un esame tra quelli insegnati
        System.out.println("Inserisci il nome dell'esame:");
        String nomeEsame = scanner.nextLine();

        Esame esameSelezionato = null;
        for (Esame esame : esamiInsegnati) {
            if (esame.getNome().equalsIgnoreCase(nomeEsame)) {
                esameSelezionato = esame;
                break;
            }
        }

        if (esameSelezionato == null) {
            System.out.println("Esame non trovato tra quelli insegnati.");
            return;
        }

        // Seleziona un appello per data
        System.out.println("Inserisci la data dell'appello (formato: yyyy-MM-dd):");
        String dataInput = scanner.nextLine();
        Date dataAppello = Date.valueOf(dataInput); // Conversione stringa -> Date

        Appello appelloSelezionato = null;
        for (Appello appello : esameSelezionato.getAppelli()) {
            if (appello.getData().equals(dataAppello)) {
                appelloSelezionato = appello;
                break;
            }
        }

        if (appelloSelezionato == null) {
            System.out.println("Appello non trovato.");
            return;
        }

        // Visualizza gli studenti prenotati
        List<Studente> studentiPrenotati = appelloSelezionato.getStudentiPrenotati();
        if (studentiPrenotati.isEmpty()) {
            System.out.println("Nessuno studente è prenotato per questo appello.");
            return;
        }

        System.out.println("Studenti prenotati:");
        for (Studente studente : studentiPrenotati) {
            System.out.println("- " + studente.getNome() + " " + studente.getCognome() + " (Matricola: "
                    + studente.getMatricola() + ")");
        }

        // Seleziona uno studente per matricola
        System.out.println("Inserisci la matricola dello studente:");
        String matricola = scanner.nextLine();

        Studente studenteSelezionato = null;
        for (Studente studente : studentiPrenotati) {
            if (studente.getMatricola().equals(matricola)) {
                studenteSelezionato = studente;
                break;
            }
        }

        if (studenteSelezionato == null) {
            System.out.println("Studente non trovato tra i prenotati.");
            return;
        }

        // Genera un voto casuale (probabilità più alta tra 18 e 30)
        int voto = generaVotoCasuale();
        System.out.println("Voto generato: " + voto);

        // Invia il voto allo studente tramite il Mediator
        String messaggio = String.format(
                "Esame: %s, Data: %s, Voto: %d",
                esameSelezionato.getNome(),
                dataAppello,
                voto);
        inviaMessaggio(messaggio, studenteSelezionato.getCognome());
        System.out.println("Voto inviato allo studente.");
    }

    // Metodo per generare un voto casuale
    private int generaVotoCasuale() {
        Random random = new Random();
        int probabilita = random.nextInt(100); // Genera un numero tra 0 e 99
        if (probabilita < 60) {
            // 60% di probabilità tra 18 e 30
            return 18 + random.nextInt(13); // [18, 30]
        } else {
            // 40% di probabilità tra 0 e 17
            return random.nextInt(18); // [0, 17]
        }
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void inviaMessaggio(String messaggio, String studenteCognome) {
        Utente destinatario = mediator.trovaUtente(studenteCognome);
        mediator.inviaMessaggio(messaggio, this, destinatario);
    }

}
