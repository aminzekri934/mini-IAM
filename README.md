# Mini-IAM Visibility Simulator 🔐

Ein Java-basiertes mini-Projekt zur Simulation von Kernfunktionen einer Identity & Access Management (IAM) Plattform.

## 🎯 Über das Projekt
Dieses Projekt implementiert ein relationales **Role-Based Access Control (RBAC)** Modell. Es demonstriert die Verknüpfung von Identitäten (Users), Rollen und Berechtigungen sowie die programmatische Auswertung komplexer Rechte-Strukturen über Java (JDBC) und PostgreSQL.

## 🛠️ Tech Stack
* **Sprache:** Java (Plain Java)
* **Datenbank:** PostgreSQL
* **Schnittstelle:** JDBC (mit PreparedStatements zur SQL-Injection Prävention)

## 🚀 Kernfunktionen
1. **Access Check:** Live-Prüfung (`checkAccess`), ob ein Benutzer über seine zugewiesenen Rollen ein spezifisches Recht besitzt (Aufgelöst über JOINs über 5 Tabellen).
2. **Security Audit - Orphaned Accounts:** Identifiziert deaktivierte Benutzer, die technisch noch aktive Rollen-Zuweisungen besitzen.
3. **Security Audit - Segregation of Duties (SoD):** Erkennt Compliance-Verstöße, wenn ein Benutzer unzulässige Rollenkombinationen (z.B. Admin + Finance) besitzt (Gelöst über SQL Self-Joins).

## 🗄️ Datenbank-Schema
Das System basiert auf einer klassischen Many-to-Many Architektur:
`users` <-> `user_roles` <-> `roles` <-> `role_permissions` <-> `permissions`

*(Die DDL- und DML-Skripte zur Initialisierung der Datenbank liegen im DDL_DML Datei).*