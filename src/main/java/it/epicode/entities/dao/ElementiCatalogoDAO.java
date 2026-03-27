package it.epicode.entities.dao;

import it.epicode.entities.ElementoCatalogo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ElementiCatalogoDAO {

    private final EntityManager em;

    public ElementiCatalogoDAO(EntityManager em) {
        this.em = em;
    }

    // 1. Aggiunta di un elemento al catalogo
    public void save(ElementoCatalogo elemento) {
        try {
            em.getTransaction().begin();
            em.persist(elemento);
            em.getTransaction().commit();
            System.out.println("Elemento salvato con successo: " + elemento.getTitolo());
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Errore durante il salvataggio dell'elemento: " + e.getMessage());
        }
    }

    // 2. Rimozione di un elemento dato un codice ISBN
    public void deleteByIsbn(String isbn) {
        try {
            em.getTransaction().begin();
            // Per cancellare, prima devo trovare l'oggetto
            TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.isbn = :isbn", ElementoCatalogo.class);
            query.setParameter("isbn", isbn);

            ElementoCatalogo elemento = query.getResultStream().findFirst().orElse(null);

            if (elemento != null) {
                em.remove(elemento);
                System.out.println("Elemento eliminato: " + isbn);
            } else {
                System.out.println("Elemento con ISBN " + isbn + " non trovato.");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Impossibile eliminare l'elemento.");
        }
    }

    // 3. Ricerca per ISBN
    public ElementoCatalogo findByIsbn(String isbn) {
        TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.isbn = :isbn", ElementoCatalogo.class);
        query.setParameter("isbn", isbn);
        // Uso findFirst per evitare fastidiose eccezioni se non trova nulla
        return query.getResultStream().findFirst().orElse(null);
    }

    // 4. Ricerca per anno di pubblicazione
    public List<ElementoCatalogo> findByAnno(int anno) {
        TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno", ElementoCatalogo.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    // 5. Ricerca per autore
    public List<ElementoCatalogo> findByAutore(String autore) {
        // Nota bene: 'autore' esiste solo nella tabella dei libri.
        // Interrogo direttamente la classe 'Libro' per evitare problemi.
        TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", ElementoCatalogo.class);
        query.setParameter("autore", autore);
        return query.getResultList();
    }

    // 6. Ricerca per titolo o parte di esso
    public List<ElementoCatalogo> findByTitolo(String parteTitolo) {
        TypedQuery<ElementoCatalogo> query = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)", ElementoCatalogo.class);
        query.setParameter("titolo", "%" + parteTitolo + "%");
        return query.getResultList();
    }
}