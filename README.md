# M294-MongoDB-API

1. generische Mongo-DB - basierte REST-API <http://localhost:8080/{your-collection}/documents>
2. Mongodb-Administrations-WebUI <http://localhost:8081/db/demo-store/>

    * Benutzer: admin
    * Passwort: pass

## Benutzung

`docker compose up -d` sollte ausreichend sein. Die Daten des Mongo-DB Containers werden persistent gespeichert.

Wenn Du etwas am API-*src*-Code verändert hast, erstellst Du das Image lokal mit `docker compose up -d --build`

Die *CORS*-Konfiguration ist für beliebige URLs und Ports frei.

## Api-Documentation

* Basis-URL: <http://localhost:8080/{your-collection}/documents>
* *{your-collection}* ist ein beliebiger, selbstgewählter Name deiner *collection*, beispielsweise:

  * *contacts* für Kontakte
  * *todos* für eine Todo-Liste

  Alle *collections* werden per Default in der *demo-store* Datenbank abgelegt. Auf Fremdschlüsselbeziehungen zwischen *collections* soll verzichtet werden.

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

### Beispiel

Hier werden zwei Objekte in der *demo-store* DB in der *demo* Collection erstellt und danach abgefragt.

```sh
curl -X POST http://localhost:8080/demo/documents --json '{"content": {"name":"Lucky","planet":"Earth"}}'

curl -X POST http://localhost:8080/demo/documents --json '{"content": {"name":"Alf","planet":"Melmak"}}'

curl http://localhost:8080/demo/documents

#returns [{"id":"6931be5534bd84a03f1d2a05","content":{"name":"Alf","planet":"Melmak"}},{"id":"6931be6e34bd84a03f1d2a06","content":{"name":"Lucky","planet":"Earth"}}]
```
