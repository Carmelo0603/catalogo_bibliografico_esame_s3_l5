# Catalogo Bibliografico - Modello Dati e Implementazione

Questo progetto implementa il backend per la gestione di un catalogo bibliografico e dei relativi prestiti. Di seguito è illustrata la logica del diagramma Entità-Relazione (ER) di progetto e la sua traduzione in codice.

## 1. Logica del Diagramma ER
Il modello concettuale si basa su tre entità fondamentali:

* **Utente:** Rappresenta l'anagrafica dei tesserati della biblioteca.
* **ElementoCatalogo:** Rappresenta una generalizzazione dei materiali disponibili. Contiene gli attributi comuni (ISBN, titolo, anno di pubblicazione, numero di pagine). Da questa entità derivano le due specializzazioni **Libro** (caratterizzato da autore e genere) e **Rivista** (caratterizzata dalla periodicità).
* **Prestito:** Funge da entità associativa (ponte) per gestire la relazione "Molti-a-Molti" tra `Utente` ed `ElementoCatalogo`. È stata modellata come entità autonoma (con relazioni 1-a-N verso Utente ed ElementoCatalogo) per poter tracciare informazioni transazionali specifiche, ovvero la data di inizio prestito, la data di restituzione prevista e quella effettiva.

## 2. Implementazione nel Codice (JPA & Hibernate)
Il diagramma concettuale è stato tradotto in un database relazionale (PostgreSQL) utilizzando Java e il framework ORM JPA/Hibernate.

* **Ereditarietà (Pattern JOINED):** La gerarchia tra `ElementoCatalogo`, `Libro` e `Rivista` è stata implementata tramite la strategia `@Inheritance(strategy = InheritanceType.JOINED)`. Questa scelta permette di mantenere il database normalizzato, creando tabelle separate per i campi specifici, collegate alla tabella padre tramite Foreign Key.
* **Mappatura delle Relazioni:** Le interazioni tra le entità sono gestite tramite annotazioni `@ManyToOne` all'interno della classe `Prestito`, delegando a Hibernate la generazione automatica delle chiavi esterne (JoinColumn).
* **Pattern DAO (Data Access Object):** L'interazione con il database è gestita tramite classi DAO dedicate. Questo garantisce una netta separazione tra il modello a oggetti e la logica di persistenza (salvataggio, eliminazione, ricerche per attributi e monitoraggio dei prestiti in corso o scaduti).