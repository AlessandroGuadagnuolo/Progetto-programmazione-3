import java.sql.Date;
import java.util.Random;

public class Tax {
    private int ID;
    private boolean stato =;
    private Date dataDiScadenza;
    private float costo;

    public Tax(int ID, Date dataDiScadenza, float costo) {
        this.ID = ID;
        this.dataDiScadenza = dataDiScadenza;
        this.costo = costo;
    }

    public int getIdTax() { return ID; }
    public Date getExpiryDate() { return dataDiScadenza; }
    public boolean isStatus() { return stato; }
    public float getAmount() { return costo; }

}