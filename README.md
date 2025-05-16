# KonTask ― Mini-Marketplace Demo (CSC 480 • Project Part II)

A stripped-down Java/SQLite implementation of **KonTask**, an AI-powered local-services marketplace.
The codebase demonstrates the **Version 2 DAO architecture** described in Prof. Howard’s sample projects: a menu-free console driver talks only to Data-Access Objects, which encapsulate all JDBC.

> **Pedigree**
> The schema and business narrative are adapted from my earlier full-stack project ➡︎
> [https://github.com/itsnothuy/Kontask](https://github.com/itsnothuy/Kontask)

---

## What’s inside 📁

| Path                     | Purpose                                                                                            |
| ------------------------ | -------------------------------------------------------------------------------------------------- |
| `util/DBConnection.java` | One static helper that opens the SQLite connection (`kontask.db`).                                 |
| `util/ID.java`           | Generates UUID primary keys.                                                                       |
| `model/`                 | Plain POJOs for `User`, `Service`, `Post`, `Bid`.                                                  |
| `dao/`                   | DAO classes with `createTable()`, `add()`, `get()`, etc.  (`Bootstrap` wires the schema together.) |
| `Main.java`              | Minimal driver that builds the schema, seeds demo rows, and prints them.                           |

Entity relationship (excerpt):

```
User 1 ─── creates ──< Post 1 ─── receives ──< Bid
          ↘                                   ↑
           ↘                              made by
             Service 1 ─── classifies ──<———
```

---

## Prerequisites

* **Java 17+** (any JDK)
* **SQLite JDBC driver** — download `sqlite-jdbc-3.46.0.0.jar`
  or newer and place it next to your clone.

*No other dependencies.*

---

## Build & Run ▶️

```bash
# compile everything under src/ into out/
javac -cp ".:sqlite-jdbc-3.46.0.0.jar" $(find src -name "*.java") -d out

# launch the console demo
java  -cp "out:sqlite-jdbc-3.46.0.0.jar" Main
```

First run creates `kontask.db`, builds the four tables, inserts a sample user, service, post, and bid, then prints:

```
📋 Open posts:
  • [open] Leaky sink (plumbing)

📋 Bids on that post:
  • $75.00 bob => Leaky sink (pending)
```

Delete `kontask.db` any time you need a clean slate.

---

## Extending the demo

* Add more tables (e.g. `Transaction`, `Review`) by following the **same DAO pattern**—copy a DAO, tweak the DDL, map the ResultSet.
* Swap `sqlite-jdbc` for **Derby** by changing the URL to
  `jdbc:derby:kontaskdb;create=true` and loading the Derby driver.
* Wire a **menu interface** (see Prof. Howard’s Version 1 skeleton) or a **Swing GUI** (Version 4) on top of the existing DAO layer.

Have fun—and feel free to compare/contrast with the full REST + React implementation in the original repo!
