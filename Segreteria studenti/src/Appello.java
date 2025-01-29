import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Appello {
    private String id;
    private Date data;
    private Esame esame;
    private Docente docente;
    private List<Studente> studentiPrenotati;

    public Appello(Date data, Esame e, Docente d) {
        this.data = data;
        this.esame = e;
        this.docente = d;
        this.studentiPrenotati = new ArrayList<>();
    }

    public void aggiungiStudente(Studente studente) {
        if (!studentiPrenotati.contains(studente)) {
            studentiPrenotati.add(studente);
        }
    }

    public Esame getEsame() {
        return esame;
    }

    public Date getData() {
        return data;
    }

    public Docente getDocente() {
        return docente;
    }

    public List<Studente> getStudentiPrenotati() {
        return studentiPrenotati;
    }
}
