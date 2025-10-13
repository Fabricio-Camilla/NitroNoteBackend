INSERT INTO usuario (nombre, email_notifications_enabled, email, password, role)
VALUES ('Fabri',true, 'fachucamilla32@gmail.com', '$2a$10$fxR3DBsv6cCWdyCOQvwMh.KsSYjGxIxO8i2X5eaCSbk8Y2i8DWyAe', 'ROLE_USER'),
       ('Dami', true,'polverigianidamian@gmail.com', '$2a$10$k/Rqy2GaOkmNIGX87sTZAOlC0O//XfKR39kIn2YrapWeGJrPLnOKu','ROLE_USER'),
       ('Walter', true,'walterggomez@gmail.com', '$2a$10$DCWb0.EbHGTZTldr6llVIe8rn6c56juaF2c5qUvt5Pm8pvj5hHXhC','ROLE_USER'),
       ('Lean', true,'leandropeppe@gmail.com', '$2a$10$CJpuZ.H6kJ2wkrGAzTlGTelJqnITWhT4XCCybtF0SYKiUVIjhFtX2','ROLE_USER'),
       ('Lucre', true,'lucrecolon1828@gmail.com', '$2a$10$8.cEKTWpTqx4rsOhK3PlleLubiDxIz0A2K6XqhxzZ.hO0uHh5oY8y','ROLE_USER');

--fa123456, wal123456,dam123456, lean123456, lu123456
INSERT INTO vehiculo (patente, marca, modelo, anio, kilometros, usuarioid)
VALUES ('AD012RD', 'Chevrolet', 'Tracker', 2023, 2000, 1),
       ('AD112RD', 'Volkswagen', 'Golf', 2023, 2000, 2),
       ('AD122RD', 'Volkswagen', 'Up', 2023, 2000, 3),
       ('AD132RD', 'Fiat', 'Palio', 2023, 2000, 4),
       ('AD142RD', 'Audi', 'A3', 2023, 2000, 5);


INSERT INTO mantenimiento (nombre, fechaarealizar, finalizado,kmarealizar, vehiculo_id)
VALUES ('Cambio de aceite',' 2025-10-13', false,2010, 1),
       ('Cambio de aceite',' 2025-10-13', false,2010, 2),
       ('Cambio de aceite',' 2025-10-13', false,2010, 3),
       ('Cambio de aceite',' 2025-10-13', false,2010, 4),
       ('Cambio de aceite',' 2025-10-13', false,2010, 5);