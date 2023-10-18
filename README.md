# Web Image Gallery Application

Questo repository contiene due versioni di un'applicazione web per la gestione di una galleria d'immagini, sviluppate come progetto per l'esame universitario "Tecnologie informatiche per il web." Una versione è basata su HTML puro e l'altra incorpora JavaScript per un'esperienza utente più interattiva. Le due versioni condividono la stessa funzionalità di base.

## Versione HTML

### Caratteristiche

- Registrazione e login utente con controllo di validità sintattica dell'indirizzo email e dell'uguaglianza tra password e "ripeti password."
- Memorizzazione delle immagini nel file system del server.
- Utilizzo di una base di dati per memorizzare titolo, data, testo descrittivo, percorso dell'immagine e il collegamento all'utente che le ha caricate.
- Creazione di album personali e associazione di immagini ad essi.
- Aggiunta di commenti alle immagini con nome utente.
- Visualizzazione dell'elenco degli album sulla homepage, ordinati per data di creazione.
- Visualizzazione delle immagini in album con navigazione tra blocchi di cinque immagini.
- Visualizzazione dettagli dell'immagine e possibilità di aggiungere commenti.
- Logout utente.

## Versione JavaScript

### Caratteristiche

- Registrazione e login utente con controllo di validità sintattica dell'indirizzo email e dell'uguaglianza tra password e "ripeti password," eseguiti anche lato client.
- Applicazione sviluppata come pagina web singola dopo il login.
- Utilizzo di chiamate asincrone per comunicare con il server e aggiornare il contenuto della pagina senza ricaricare completamente la pagina.
- Gestione client-side della visualizzazione dei blocchi precedenti/successivi d'immagini di un album.
- Finestra modale per visualizzare i dettagli dell'immagine e la possibilità di aggiungere commenti quando si passa con il mouse su una miniatura.
- Controllo client-side per evitare l'invio di commenti vuoti.
- Segnalazione di errori a lato server con messaggi di allerta nella pagina.
- Riordinamento personalizzato degli album trascinando il titolo dell'album e salvando l'ordinamento lato server.

## Autore

- [Vittorio La Ferla](https://github.com/vittoriolaferla)

## Come eseguire l'applicazione

Per eseguire questa applicazione, segui questi passi:

1. **Installazione di Apache Tomcat:**
   - Assicurati di avere Apache Tomcat installato sul tuo sistema. Puoi scaricarlo da [https://tomcat.apache.org/](https://tomcat.apache.org/).

2. **Configurazione di Apache Tomcat:**
   - Dopo l'installazione, assicurati che Tomcat sia configurato correttamente, compresa la configurazione del file `server.xml` per gestire le tue applicazioni web.

3. **Caricamento del progetto:**
   - Carica il progetto sul server Tomcat. Puoi farlo copiando la cartella del progetto nella directory `webapps` di Tomcat.

4. **Avvio di Tomcat:**
   - Avvia il server Tomcat.

5. **Accesso all'applicazione:**
   - Una volta che Tomcat è in esecuzione, apri un browser web e vai all'indirizzo `http://localhost:8080/ImagesGallery`.

6. **Registrazione o login:**
   - Segui le istruzioni per registrarti o effettuare il login nell'applicazione.

