
import java.util.ArrayList;
import java.util.List;

public class Esame {
    private String codiceEsame; // univoco per ogni esame
    private String nome;
    private int crediti;
    private Docente docente; // aggiunto riferimento al docente responsabile dell'esame
    private List<Appello> appelli;

    public Esame(String codiceEsame, String nome, int crediti, Docente docente) {
        this.codiceEsame = codiceEsame;
        this.nome = nome;
        this.crediti = crediti;
        this.docente = docente;
        this.appelli = new ArrayList<>();
    }

    // Getter e setter
    public String getCodiceEsame() {
        return codiceEsame;
    }

    public String getNome() {
        return nome;
    }

    public int getCrediti() {
        return crediti;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public List<Appello> getAppelli() {
        return appelli;
    }
}
