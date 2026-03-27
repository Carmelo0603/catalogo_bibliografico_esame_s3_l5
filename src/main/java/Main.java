import it.epicode.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        System.out.println("Accensione dei motori di Hibernate...");

        // "CatalogoPU" è il nome magico che abbiamo messo nel persistence.xml.
        // Se non combacia, si ferma tutto.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CatalogoPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Iniziamo la transazione
            em.getTransaction().begin();

            // Creiamo il nostro utente cavia
            Utente cavia = new Utente(
                    "TESS-001",
                    "Mario",
                    "Rossi",
                    LocalDate.of(1990, 5, 15)
            );

            // Salviamo la cavia nel database
            em.persist(cavia);

            // Confermiamo il salvataggio
            em.getTransaction().commit();

            System.out.println("MIRACOLO! L'utente cavia è nel database e le tabelle sono state create!");

        } catch (Exception e) {
            // Se qualcosa va storto, annulliamo tutto
            em.getTransaction().rollback();
            System.err.println("Disastro totale. Guarda l'errore qui sotto:");
            e.printStackTrace();
        } finally {
            // Chiudiamo le connessioni per non intasare la memoria
            em.close();
            emf.close();
        }
    }
}