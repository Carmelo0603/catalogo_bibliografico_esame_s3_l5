package it.epicode.entities;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * Entità astratta che rappresenta il catalogo generale.
 * Utilizza la strategia JOINED: creerà una tabella per i campi comuni
 * e tabelle separate (ma collegate tramite Foreign Key) per i campi specifici dei figli.
 */
@Entity
@Table(name = "elementi_catalogo")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ElementoCatalogo {

    @Id
    @GeneratedValue
    private UUID id;

    // L'ISBN è il codice univoco del libro/rivista, come richiesto dalla traccia
    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String titolo;

    @Column(name = "anno_pubblicazione", nullable = false)
    private Integer annoPubblicazione;

    @Column(name = "numero_pagine", nullable = false)
    private Integer numeroPagine;

    public ElementoCatalogo() {
    }

    public ElementoCatalogo(String isbn, String titolo, Integer annoPubblicazione, Integer numeroPagine) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
    }

    // Getter e Setter
    public UUID getId() { return id; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public Integer getAnnoPubblicazione() { return annoPubblicazione; }
    public void setAnnoPubblicazione(Integer annoPubblicazione) { this.annoPubblicazione = annoPubblicazione; }

    public Integer getNumeroPagine() { return numeroPagine; }
    public void setNumeroPagine(Integer numeroPagine) { this.numeroPagine = numeroPagine; }

    @Override
    public String toString() {
        return "ElementoCatalogo{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", numeroPagine=" + numeroPagine +
                '}';
    }
}