package it.epicode.entities;

import it.epicode.entities.dao.ElementiCatalogoDAO;
import it.epicode.entities.dao.PrestitoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Avvio del sistema... speriamo di non far danni.");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CatalogoPU");
        EntityManager em = emf.createEntityManager();

        // Inizializziamo i nostri fidi operai (i DAO)
        ElementiCatalogoDAO catalogoDAO = new ElementiCatalogoDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        try {
            System.out.println("\n--- 1. CREAZIONE DATI DI TEST ---");

            // Salviamo un nuovo utente "a mano" per fargli prendere in prestito le cose
            em.getTransaction().begin();
            Utente utenteTest = new Utente("TESS-002", "Luigi", "Verdi", LocalDate.of(1995, 8, 20));
            em.persist(utenteTest);
            em.getTransaction().commit();
            System.out.println("Utente test creato: " + utenteTest.getNome());

            // Creiamo un po' di roba da leggere
            Libro libro1 = new Libro("ISBN-111", "Il Signore degli Anelli", 1954, 1200, "J.R.R. Tolkien", "Fantasy");
            Libro libro2 = new Libro("ISBN-222", "1984", 1949, 328, "George Orwell", "Distopico");
            Rivista rivista1 = new Rivista("ISBN-333", "Focus", 2026, 80, Periodicita.MENSILE);

            // Usiamo il DAO per salvarli
            catalogoDAO.save(libro1);
            catalogoDAO.save(libro2);
            catalogoDAO.save(rivista1);

            System.out.println("\n--- 2. REGISTRAZIONE PRESTITI ---");

            // Prestito 1: Regolare, fatto 10 giorni fa (ancora attivo)
            Prestito prestitoAttivo = new Prestito(utenteTest, libro1, LocalDate.now().minusDays(10));

            // Prestito 2: Scaduto. Iniziato 40 giorni fa. La restituzione prevista era calcolata a 30 giorni,
            // quindi è scaduto da 10 giorni e non è ancora stato restituito.
            Prestito prestitoScaduto = new Prestito(utenteTest, libro2, LocalDate.now().minusDays(40));

            prestitoDAO.save(prestitoAttivo);
            prestitoDAO.save(prestitoScaduto);

            System.out.println("\n--- 3. TEST DELLE RICERCHE (Quello che vuole vedere il prof) ---");

            System.out.println("\n> Cerco elemento tramite ISBN 'ISBN-111':");
            ElementoCatalogo trovato = catalogoDAO.findByIsbn("ISBN-111");
            System.out.println(trovato != null ? "Trovato: " + trovato.getTitolo() : "Nessun risultato.");

            System.out.println("\n> Cerco tutti i libri scritti da 'George Orwell':");
            List<ElementoCatalogo> libriOrwell = catalogoDAO.findByAutore("George Orwell");
            libriOrwell.forEach(l -> System.out.println("- " + l.getTitolo()));

            System.out.println("\n> Cerco prestiti attualmente in mano all'utente TESS-002:");
            List<Prestito> prestitiTessera = prestitoDAO.findPrestitiAttiviByNumeroTessera("TESS-002");
            prestitiTessera.forEach(p -> System.out.println("- " + p.getElementoPrestato().getTitolo()));

            System.out.println("\n> ALLARME: Cerco tutti i prestiti SCADUTI e non restituiti:");
            List<Prestito> prestitiScaduti = prestitoDAO.findPrestitiScaduti();
            prestitiScaduti.forEach(p -> System.out.println("- Multa per " + p.getUtente().getNome() + " " + p.getUtente().getCognome() +
                    " | Non ha restituito: " + p.getElementoPrestato().getTitolo()));

            System.out.println("\nTest concluso. Se sei arrivato fin qui, hai un progetto funzionante.");

        } catch (Exception e) {
            System.err.println("Hai rotto qualcosa all'ultimo metro:");
            e.printStackTrace();
        } finally {
            // Chiude sempre la porta
            em.close();
            emf.close();
        }
    }
}