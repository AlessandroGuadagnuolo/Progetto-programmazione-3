import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Tax {
    private LocalDate startDate = LocalDate.of(2024, 12, 1);
    private LocalDate endDate = LocalDate.of(2025, 12, 1);
    private long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
    private LocalDate dataDiScadenza = startDate.plusDays(ThreadLocalRandom.current().nextLong(daysBetween + 1));

    private Studente studente;
    private float costo;
    Random random = new Random();
    private short id = (short) random.nextInt();
    private boolean stato = random.nextBoolean();

    public Tax(float costo) {
        this.costo = costo;
    }

    public int getIdTax() {
        return ID;
    }

    public LocalDate getExpiryDate() {
        return dataDiScadenza;
    }

    public boolean isStatus() {
        return stato;
    }

    public float getAmount() {
        return costo;
    }
}