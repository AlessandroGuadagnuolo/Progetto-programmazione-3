public interface Mediator { // per la comunicazione indiretta tra Studente e Docente riguardo i voti
    void inviaMessaggio(String messaggio, Utente mittente, Utente destinatario);

    void registraUtente(Utente utente);

    Utente trovaUtente(String cognome);
}
