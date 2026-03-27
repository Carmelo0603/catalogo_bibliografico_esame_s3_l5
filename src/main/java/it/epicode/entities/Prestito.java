package it.epicode.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Entità che rappresenta la transazione di prestito.
 * Fa da ponte tra l'Utente e l'ElementoCatalogo.
 */
@Entity
@Table(name = "prestiti")
public class Prestito {

    @Id
    @GeneratedValue
    private UUID id;

    // Relazione Molti-a-Uno con l'Utente.
    // @JoinColumn indica a Hibernate il nome esatto della Foreign Key sul database.
    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    // Relazione Molti-a-Uno con l'Elemento del Catalogo.
    @ManyToOne
    @JoinColumn(name = "elemento_prestato_id", nullable = false)
    private ElementoCatalogo elementoPrestato;

    @Column(name = "data_inizio_prestito", nullable = false)
    private LocalDate dataInizioPrestito;

    @Column(name = "data_restituzione_prevista", nullable = false)
    private LocalDate dataRestituzionePrevista;

    // Può essere null se l'utente non ha ancora restituito l'elemento
    @Column(name = "data_restituzione_effettiva")
    private LocalDate dataRestituzioneEffettiva;

    public Prestito() {
    }

    public Prestito(Utente utente, ElementoCatalogo elementoPrestato, LocalDate dataInizioPrestito) {
        this.utente = utente;
        this.elementoPrestato = elementoPrestato;
        this.dataInizioPrestito = dataInizioPrestito;
        // La traccia di solito dice che la restituzione prevista è 30 giorni dopo l'inizio
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30);
    }

    // Getter e Setter
    public UUID getId() { return id; }

    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }

    public ElementoCatalogo getElementoPrestato() { return elementoPrestato; }
    public void setElementoPrestato(ElementoCatalogo elementoPrestato) { this.elementoPrestato = elementoPrestato; }

    public LocalDate getDataInizioPrestito() { return dataInizioPrestito; }
    public void setDataInizioPrestito(LocalDate dataInizioPrestito) {
        this.dataInizioPrestito = dataInizioPrestito;
        // Se cambi la data di inizio, si ricalcola in automatico la fine prevista
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30);
    }

    public LocalDate getDataRestituzionePrevista() { return dataRestituzionePrevista; }
    // Non c'è il setter per la prevista, perché è calcolata in automatico!

    public LocalDate getDataRestituzioneEffettiva() { return dataRestituzioneEffettiva; }
    public void setDataRestituzioneEffettiva(LocalDate dataRestituzioneEffettiva) { this.dataRestituzioneEffettiva = dataRestituzioneEffettiva; }

    @Override
    public String toString() {
        return "Prestito{" +
                "id=" + id +
                ", utente=" + (utente != null ? utente.getNumeroTessera() : "null") +
                ", elementoPrestato=" + (elementoPrestato != null ? elementoPrestato.getIsbn() : "null") +
                ", dataInizioPrestito=" + dataInizioPrestito +
                ", dataRestituzionePrevista=" + dataRestituzionePrevista +
                ", dataRestituzioneEffettiva=" + dataRestituzioneEffettiva +
                '}';
    }
}