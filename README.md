# M294-MongoDB-API

1. generische Mongo-DB - basierte REST-API <http://localhost:8080/{your-collection}/documents>
2. Mongodb-Administrations-WebUI <http://localhost:8081/db/demo-store/>

    * Benutzer: root
    * Passwort: example

## Benutzung

`docker compose up -d` sollte ausreichend sein. Die Daten des Mongo-DB Containers werden persistent gespeichert.

Wenn das `greenorca/m294-project-api:latest` nicht geladen werden kann, oder Du etwas am API-*src*-Code verändert hast, erstellst Du das Image lokal mit `docker compose up -d --build`

Die *CORS*-Konfiguration ist für beliebige URLs und Ports frei.

## Api-Documentation

* Basis-URL: <http://localhost:8080/{your-collection}/documents>
* *{your-collection}* ist ein beliebiger, selbstgewählter Name deiner *collection*, beispielsweise:

  * *contacts* für Kontakte
  * *todos* für eine Todo-Liste

  Alle *collections* werden per Default in der demo-store Datenbank abgelegt. Auf Fremdschlüsselbeziehungen zwischen *collections* soll verzichtet werden.

### Allgemeiner Aufbau der *documents*

Jedes *document* in deiner Collection hat eine automatisch generierte *id* und ein *content*-Attribut. Das *content*-Attribut enthält ein JSON-Objekt mit beliebigen Aufbau, beispielsweise

```json
  {
    "content" : {
        "title": "Geschenkli posten",
        "text": "Opa: Olivenöl; Oma: Kräuterlikör",
        "due-to": "2024-12-23"
    }
  }
```

Achte darauf, den Inhalt des *content*-Attributs möglichst immer gleich aufzubauen, indem du immer die gleichen Attribute verwendest.

### API Requests

1. **GET**-Request auf <http://localhost:8080/{your-collection}/documents> ruft alle gespeicherten Dokumente aus der *your-collection* Collection
2. **POST**-Request auf <http://localhost:8080/{your-collection}/documents> speichert ein neues JSON-Dokument in der *your-collection* Collection
3. **PUT**-Request auf <http://localhost:8080/{your-collection}/documents/{id}> updated vorhandenes Dokument mit der übergebenen *id* in der *your-collection* Collection
4. **DELETE**-Request auf <http://localhost:8080/{your-collection}/documents/{id}> entfernt gespeichertes Dokument mit übergebener ID aus der *your-collection* Collection

siehe <http://localhost:8080/v3/api-docs>

