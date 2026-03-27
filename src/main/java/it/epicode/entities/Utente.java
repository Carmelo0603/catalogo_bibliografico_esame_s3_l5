package it.epicode.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Rappresenta l'entità Utente all'interno del database.
 * Mappata sulla tabella relazionale "utenti".
 */
@Entity
@Table(name = "utenti")
public class Utente {

    // Chiave primaria della tabella. Generata automaticamente come UUID per garantire l'univocità globale.
    @Id
    @GeneratedValue
    private UUID id;

    // Mappatura della colonna "numero_tessera".
    // nullable = false: applica il vincolo NOT NULL.
    // unique = true: applica un vincolo di unicità a livello di database.
    @Column(name = "numero_tessera", nullable = false, unique = true)
    private String numeroTessera;

    // Colonne standard obbligatorie per l'anagrafica.
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    // Customizzazione del nome colonna per rispettare le convenzioni di naming del DB (snake_case).
    @Column(name = "data_di_nascita", nullable = false)
    private LocalDate dataNascita;

    /**
     * Costruttore vuoto di default.
     * Obbligatorio per le specifiche JPA/Hibernate per l'istanziazione dell'entità tramite reflection.
     */
    public Utente() {
    }

    /**
     * Costruttore parametrizzato per la creazione di nuovi record.
     * L'ID è omesso in quanto la sua generazione è delegata integralmente al database.
     */
    public Utente(String numeroTessera, String nome, String cognome, LocalDate dataNascita) {
        this.numeroTessera = numeroTessera;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    // Metodi Getter e Setter per l'incapsulamento e l'accesso ai dati dell'entità
    public UUID getId() { return id; }

    public String getNumeroTessera() { return numeroTessera; }
    public void setNumeroTessera(String numeroTessera) { this.numeroTessera = numeroTessera; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public LocalDate getDataNascita() { return dataNascita; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }

    // Override del metodo toString per facilitare il logging e il debug in console
    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", numeroTessera='" + numeroTessera + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }
}