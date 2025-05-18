-- Beispieldaten für Tasks (wird nur mit H2 in der Dev-Umgebung geladen)
INSERT INTO tasks (title, description, completed, created_at, due_date, priority)
VALUES ('Spring Boot lernen', 'Die Grundlagen von Spring Boot verstehen', false, CURRENT_TIMESTAMP(), DATEADD('DAY', 7, CURRENT_TIMESTAMP()), 'HIGH');

INSERT INTO tasks (title, description, completed, created_at, due_date, priority)
VALUES ('REST API erstellen', 'Eine REST API für die Aufgabenverwaltung implementieren', false, CURRENT_TIMESTAMP(), DATEADD('DAY', 3, CURRENT_TIMESTAMP()), 'MEDIUM');

INSERT INTO tasks (title, description, completed, created_at, due_date, priority)
VALUES ('Containerisierung', 'Spring Boot Anwendung in Docker containerisieren', false, CURRENT_TIMESTAMP(), DATEADD('DAY', 14, CURRENT_TIMESTAMP()), 'LOW');

INSERT INTO tasks (title, description, completed, created_at, due_date, priority)
VALUES ('Kubernetes Deployment erstellen', 'Die Anwendung in Kubernetes deployen', false, CURRENT_TIMESTAMP(), DATEADD('DAY', 21, CURRENT_TIMESTAMP()), 'LOW');

INSERT INTO tasks (title, description, completed, created_at, due_date, priority)
VALUES ('Dokumentation schreiben', 'API Dokumentation erstellen', false, CURRENT_TIMESTAMP(), DATEADD('DAY', 5, CURRENT_TIMESTAMP()), 'MEDIUM');
