# DogVille

DogVille è una piattaforma completa che collega canili e potenziali adottanti. Comprende un **backend robusto** e un **frontend intuitivo** per offrire agli utenti un'esperienza fluida nel processo di ricerca, selezione e adozione di cani.

---

## Repository del Progetto

- **Backend**: [DogVille_Back_End](https://github.com/Zoltan3do/DogVille_Back_End)  
  Gestisce la logica dell'applicazione, le API e l'interazione con il database.

- **Frontend**: [DogVille Front-End](https://github.com/Zoltan3do/DogVille)  
  Fornisce un'interfaccia utente interattiva per gli utenti finali.

---

## Caratteristiche Principali

- **Ricerca Avanzata**: Trova cani disponibili per l'adozione basandosi su criteri come razza, età, taglia, ecc.
- **Quiz di Compatibilità**: Suggerisce il cane ideale in base alle preferenze e allo stile di vita dell'utente.
- **Storie del Canile**: Presenta informazioni sulla missione e la storia del canile.
- **Percorso di Adozione Guidato**: Guida gli utenti in ogni fase dell'adozione.
- **Profilo Utente**: Permette agli utenti di salvare cani preferiti e monitorare le richieste d'adozione.

---

## Tecnologie Utilizzate

### Backend
- **Linguaggio**: Java con Spring
- **Database**: PostGreSQL
- **Autenticazione**: JSON Web Tokens (JWT)
- **Storage**: Cloudinary per la gestione delle immagini
- **Security**: Spring Security
- **Api Description**: SwaggerUI -> http://localhost:3001/swagger-ui/index.html 

### Frontend
- **Libreria**: React.js
- **Gestione Stato**: Redux
- **Styling**: CSS3 e framework di design moderno
- **Interazione con le API**: async fetch

---

## Installazione

### Backend

1. Clona il repository:
   ```bash
   git clone https://github.com/Zoltan3do/DogVille_Back_End.git
   ```
2. Entra nella directory:
   ```bash
   cd DogVille_Back_End
   ```
3. Installa le dipendenze:
   ```bash
   npm install
   ```
4. Configura le variabili d'ambiente:
   Crea un file `.env` con le seguenti variabili:
   ```
   PORT=3000
   MONGODB_URI=your_mongodb_uri
   JWT_SECRET=your_jwt_secret
   CLOUDINARY_CLOUD_NAME=your_cloudinary_cloud_name
   CLOUDINARY_API_KEY=your_cloudinary_api_key
   CLOUDINARY_API_SECRET=your_cloudinary_api_secret
   ```
5. Avvia il server:
   ```bash
   npm start
   ```

### Frontend

1. Clona il repository:
   ```bash
   git clone https://github.com/Zoltan3do/DogVille_Front_End.git
   ```
2. Entra nella directory:
   ```bash
   cd DogVille_Front_End
   ```
3. Installa le dipendenze:
   ```bash
   npm install
   ```
4. Avvia l'applicazione:
   ```bash
   npm start
   ```
5. Accedi al frontend su `http://localhost:3000`.

---

## Struttura delle API

### Backend Endpoints
- **`GET /api/dogs`**: Recupera l'elenco dei cani disponibili.
- **`POST /api/dogs`**: Aggiunge un nuovo cane (richiede autenticazione).
- **`GET /api/dogs/:id`**: Recupera i dettagli di un cane specifico.
- **`PUT /api/dogs/:id`**: Modifica i dati di un cane (richiede autenticazione).
- **`DELETE /api/dogs/:id`**: Rimuove un cane dal database (richiede autenticazione).

- **AND MORE...**
---

## Contribuzione

1. Fai un fork dei repository:
   - [Backend](https://github.com/Zoltan3do/DogVille_Back_End)
   - [Frontend](https://github.com/Zoltan3do/DogVille)

2. Crea una nuova branch per le modifiche:
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. Committa le modifiche:
   ```bash
   git commit -m "Descrizione delle modifiche"
   ```

4. Invia una pull request al branch `main`.

---

DogVille mira a semplificare il processo di adozione e ad aumentare la visibilità dei canili, contribuendo al benessere degli animali. 🐾