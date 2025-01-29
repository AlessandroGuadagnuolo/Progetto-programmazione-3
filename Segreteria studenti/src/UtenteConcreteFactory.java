public class UtenteConcreteFactory extends UtenteFactory {

    public static Utente creaUtente(String ruolo, String nome, String cognome) {
        switch (ruolo.toLowerCase()) {
            case "studente":
                return new Studente(nome, cognome);
            case "docente":
                return new Docente(nome, cognome);
            case "segreteria":
                return new Segreteria(nome, cognome);
            default:
                throw new IllegalArgumentException("Ruolo non valido: " + ruolo);
        }
    }
}
