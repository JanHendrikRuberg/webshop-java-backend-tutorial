- Pfad
- Query Parameter
- Http Verb: GET, POST, PUT, DELETE (HEAD, OPTIONS, PATCH, TRACE)
- Request Body

REST: Ressourcen
Produkte
Kunden
Bestellungen


Lade alle Produkte vom Server:
GET /api/products

Lade Produkte mit bestimmten Tag
GET /api/products?tag={tag}

Erzeuge neues Produkt
POST /api/products

Lösche Produkt
DELETE /api/products/{id}

Lade Produkt mit bestimmter ID
GET /api/products/{id}

Update Produkt
PUT /api/products/{id}

Füge Tags zu Produkt hinzu
PUT /api/products/{id}/tags

Bestelle Produkt --> Erzeuge neue Bestellung
POST /api/orders

Füge Produkt zu Bestellung hinzu
POST /api/orders/{id}/products


