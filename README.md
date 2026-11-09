# 🏨 Ocean View Resort – Room Reservation System

![Java](https://img.shields.io/badge/Java%2021-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%203.2-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![React](https://img.shields.io/badge/React%2018-20232A?style=flat-square&logo=react&logoColor=61DAFB)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=vite&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=mongodb&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat-square&logo=jsonwebtokens&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/CI-GitHub%20Actions-2088FF?style=flat-square&logo=githubactions&logoColor=white)

A full-stack hotel room reservation system for **Ocean View Resort, Galle, Sri Lanka**. Staff log in, add reservations, check room availability, calculate bills and view booking reports.

---

## ✨ Features

- 🔐 **Secure login** with JWT tokens and Spring Security
- 📝 **Add reservations**, with a room availability check
- 📋 **View and manage** all reservations
- 🧾 **Automatic bill calculation**
- 🔍 **Guest search** by name
- 📊 **Booking history reports**
- 🛏️ **Room list** with check-in and check-out management
- ❓ **Help page** for staff

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React 18 + Vite, React Router, Axios |
| Backend | Spring Boot 3.2 (Java 21), REST API |
| Database | MongoDB |
| Security | Spring Security + JWT |
| Testing | JUnit 5 + Mockito |
| CI | GitHub Actions: builds the backend, runs the tests, builds the frontend |

---

## 🏗️ Architecture

```
┌────────────────────┐   REST + JWT   ┌──────────────────────┐        ┌───────────┐
│  React frontend    │ ─────────────► │ Spring Boot backend  │ ─────► │  MongoDB  │
│  (port 5173)       │ ◄───────────── │ (port 8080)          │ ◄───── │           │
└────────────────────┘      JSON      └──────────────────────┘        └───────────┘
```

```
ocean-view-resort/
├── backend/     # Spring Boot REST API: controllers, services, repositories, security
├── frontend/    # React + Vite UI: pages, components, auth context
└── .github/     # GitHub Actions CI workflow
```

---

## ✅ Tests

**6 JUnit tests, all passing.** See [TEST-RESULTS.md](TEST-RESULTS.md).

| Test | What it checks |
|---|---|
| TC01 | A reservation is created successfully |
| TC02 | A booked room can't be reserved again |
| TC03 | Check-out can't be before check-in |
| TC04 | The bill is calculated correctly |
| TC05 | A reservation can be found by its number |
| TC06 | An error is shown when a reservation number doesn't exist |

---

## ▶️ How to Run

**You need:** Java 21, Maven, Node.js 20 and MongoDB running locally (all free).

**1. Start the backend**
```bash
cd backend
mvn spring-boot:run
```

**2. Start the frontend** (in a second terminal)
```bash
cd frontend
npm install
npm run dev
```

**3. Open** `http://localhost:5173` in your browser.

**Demo logins**

| Role | Username | Password |
|---|---|---|
| Admin | `admin` | `admin123` |
| Staff | `staff` | `staff123` |

---

## 📸 Screenshots

<!-- Add 2–3 screenshots here, for example: ![Dashboard](screenshots/dashboard.png) -->

---

## 📝 About

Built for **CIS6003 Advanced Programming** at Cardiff Metropolitan University (ICBT Campus), 2025.
