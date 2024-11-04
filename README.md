# Backend exam fall 2024
## datebase navn: tripplanning

## Task 3.3.2 
### GET {{url}}/trips
Den henter alle trips fra databasen og returnerer dem som en liste.

### GET {{url}}/trips/2
Den henter en specifik trip fra databasen med id 2

### POST {{url}}/trips
Den oprettet en ny trip i databasen, uden at have en guide tilknyttet. 
Man skal give følgende infomationer:
-Name
-startTime
-endTime
-startPosition
-price
-category (BEACH, CITY, FOREST, LAKE, SEA, SNOW)

### POST {{url}}/trips/2/guide/3
Den tilknytter en guide med id 3 til en trip med id 2

### DELETE {{url}}/trips/2
Den sletter en trip med id 2 fra databasen

### PUT {{url}}/trips/2
Den opdaterer en trip med id 2 i databasen
Man skal give følgende infomationer:
-Name
-startTime
-endTime
-startPosition
-price
-category

## Task 3.3.4
Fik ikke det til at virke, fordi jeg fik erros, vaglte at gå videre til næste opgave

## Task 3.3.5
PUT er idempotent, og derfor tænker jeg at det har noget med at hjælpe mod fejl. 
Hvis man trykker flere gange fordi nettet er langsomt, så vil det ikke skade dataen man sender. 
Også siden en guide kan være tilknyttet flere trips, så vil det ikke skade dataen at tilknytte en guide til en trip flere gange.

Hvis vi bruger post, som ikke er idempotent, så vil det give fejl så som at duplikere dataen.


## Generalt
Havde et problem med at få min guide til at connecte med min trip når man skal lave en http request, brugte for lang tid på det
så jeg var bagud med tiden og nåede ikke at lave en stream eller at fetche

jeg fik hellere ikke min populater til at virke via http request, kun manuelt så den kunne oprette i databasen

## Svar på 8.3 
i mine rest assurded test, brugte jeg .header("Authorization", adminToken) til at give mig adgang til at lave en http request.
