import java.util.ArrayList;
import java.util.List;

public class Corso {
    private String codiceCorso;
    private String nome;
    private List<Esame> esami;

    public Corso(String codiceCorso, String nome) {
        this.codiceCorso = codiceCorso;
        this.nome = nome;
        this.esami = new ArrayList<>();
    }

    public List<Esame> getEsami() {
        return esami;
    }
}