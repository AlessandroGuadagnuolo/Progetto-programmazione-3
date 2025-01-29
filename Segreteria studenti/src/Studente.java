import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.time;
public class Studente extends Utente {
    private String matricola; // chiave primaria
    private Date dataNascita;
    private String residenza;
    private List<Esame> esami; // lista degli esami del corso di appartenenza
    private Corso pianoDiStudi; // indica il mio corso di laurea scelto
    private List<Esame> esamiSuperati;
    private List<Esame> esamiSostenuti; // in fase di valutazione
    private List<Esame> testCompletati;
    private boolean tasse; // visualizzare una notifica o un messaggio che ricordi di pagarle in caso sia
                           // FALSE
    private Mediator mediator;
    Scanner scanner;

    public Studente(String nome, String cognome, String matricola, Date dataNascita, String residenza) {
        super(nome, cognome);
        this.matricola = matricola;
        this.dataNascita = dataNascita;
        this.residenza = residenza;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setPianoDiStudi(Corso pianoDiStudi) {
        this.pianoDiStudi = pianoDiStudi;
    }

    public void setEsami(List<Esame> esami) {
        this.esami = esami;
    }

    public void effettuaPrenotazione(String nomeEsame, Date dataAppello) {
        // Cerco l'esame per nome nel piano di studi
        Esame esameTrovato = null;
        for (Esame esame : pianoDiStudi.getEsami()) {
            if (esame.getNome().equalsIgnoreCase(nomeEsame)) {
                esameTrovato = esame;
                break;
            }
        }

        if (esameTrovato == null) {
            System.out.println("Errore: Esame non trovato nel piano di studi.");
            return;
        }

        // Verifico se il test di valutazione è stato superato
        if (!testCompletati.contains(esameTrovato)) {
            System.out.println(
                    "Errore: Test di valutazione non superato. Devi completarlo prima di richiedere la prenotazione.");
            return;
        }

        // Controllo se l'esame è già stato superato
        if (esamiSuperati.contains(esameTrovato)) {
            System.out.println("Errore: Hai già superato questo esame.");
            return;
        }

        // Controllo se è presente in esamiSostenuti (già prenotato/bocciato)
        if (esamiSostenuti.contains(esameTrovato)) {
            System.out.println("Errore: Non puoi prenotarti di nuovo per questo esame.");
            return;
        }

        // Invio richiesta al docente tramite il Mediator
        if (mediator != null) {
            String messaggio = "Richiesta prenotazione: Matricola " + getMatricola() +
                    ", Codice Esame " + esameTrovato.getCodiceEsame() +
                    ", Data Appello " + dataAppello.toString();
            mediator.inviaMessaggio(messaggio, this, esameTrovato.getDocente());
            System.out.println(
                    "Richiesta di prenotazione inviata al docente per l'esame " + esameTrovato.getNome() + ".");
        } else {
            System.out.println("Errore: Sistema di comunicazione (Mediator) non disponibile.");
        }
    }

    public void gestioneVoto() {

    }

    public void effettuaTest(String nomeEsame) {
        // prima ricerco l'esame nel mio piano di studi
        Esame esameDaTestare = null;
        for (Esame esame : pianoDiStudi.getEsami()) {
            if (esame.getNome().equalsIgnoreCase(nomeEsame)) {
                esameDaTestare = esame;
                break;
            }
        }

        // se non c'è stampo un messaggio di errore
        if (esameDaTestare == null) {
            System.out.println("Errore: L'esame inserito non fa parte del tuo piano di studi.");
            return;
        }

        // controllo se il test è già stato superato
        if (testCompletati.contains(esameDaTestare)) {
            System.out.println("Test già superato per questo esame.");
            return;
        }

        // genero casualmente il risultato del test
        boolean testSuperato = Math.random() < 0.7; // math.random() genera un numero tra 0 e <1, se il valore è minore
                                                    // di 0.8 la funzione ritorna TRUE altrimenti FALSE (probablità 70%
                                                    // che ritorni TRUE)

        if (testSuperato) {
            testCompletati.add(esameDaTestare); // Registra il test come completato
            System.out.println("Test superato! Ora puoi prenotarti per l'esame.");
        } else {
            System.out.println("Test non superato. Riprova.");
        }
    }

    /*
     * public void setMediator(Mediator mediator) {
     * this.mediator = mediator;
     * }
     */

    /*
     * public void inviaMessaggio(String messaggio, String docenteCognome) {
     * Utente destinatario = mediator.trovaUtente(docenteCognome);
     * mediator.inviaMessaggio(messaggio, this, destinatario);
     * }
     */

}
