package it.epicode.entities.dao;

import it.epicode.entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PrestitoDAO {

    private final EntityManager em;

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    // 1. Salvataggio di un prestito
    public void save(Prestito prestito) {
        try {
            em.getTransaction().begin();
            em.persist(prestito);
            em.getTransaction().commit();
            System.out.println("Prestito registrato con successo!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Errore durante la registrazione del prestito: " + e.getMessage());
        }
    }

    // 2. Ricerca degli elementi attualmente in prestito dato un numero di tessera utente
    public List<Prestito> findPrestitiAttiviByNumeroTessera(String numeroTessera) {
        // La query cerca i prestiti associati a quella tessera in cui la data di restituzione effettiva è ancora NULL (quindi non l'ha ridato)
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class);
        query.setParameter("numeroTessera", numeroTessera);
        return query.getResultList();
    }

    // 3. Ricerca di tutti i prestiti scaduti e non ancora restituiti
    public List<Prestito> findPrestitiScaduti() {
        // La query cerca i prestiti non restituiti in cui la data prevista è superata rispetto alla data odierna
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < CURRENT_DATE",
                Prestito.class);
        return query.getResultList();
    }
}