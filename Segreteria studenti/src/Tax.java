import java.sql.Date;
import java.util.Random;

public class Tax {
    private Date dataDiScadenza;
    private float costo;
    Random random = new Random();
    private short ID = (short) random.nextInt();
    private boolean stato = random.nextBoolean();

    public Tax(Date dataDiScadenza, float costo) {
        this.dataDiScadenza = dataDiScadenza;
        this.costo = costo;
    }

    public int getIdTax() {
        return ID;
    }

    public Date getExpiryDate() {
        return dataDiScadenza;
    }

    public boolean isStatus() {
        return stato;
    }

    public float getAmount() {
        return costo;
    }

}