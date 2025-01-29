import java.util.HashMap;
import java.util.Map;

public class ConcreteMediator implements Mediator {
    private Map<String, Utente> utentiRegistrati;

    public ConcreteMediator() {
        utentiRegistrati = new HashMap<>();
    }

    @Override
    public void inviaMessaggio(String messaggio, Utente mittente, Utente destinatario) {
        if (destinatario != null) {
            System.out
                    .println("Messaggio da " + mittente.getNome() + " a " + destinatario.getNome() + ": " + messaggio);
        } else {
            System.out.println("Destinatario non trovato!");
        }
    }

    @Override
    public void registraUtente(Utente utente) {
        utentiRegistrati.put(utente.getCognome(), utente); // Uso il cognome come chiave
    }

    @Override
    public Utente trovaUtente(String cognome) {
        return utentiRegistrati.get(cognome);
    }
}