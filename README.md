# Ocean View Resort - Room Reservation System

A full-stack hotel room reservation management system built for **Ocean View Resort, Galle, Sri Lanka**.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | React + Vite |
| Backend | Spring Boot (Java 21) |
| Database | MongoDB |
| Auth | JWT + Spring Security |
| Testing | JUnit 5 + Mockito |
| CI/CD | GitHub Actions |

## Features

- User Authentication (JWT-based login)
- Add New Reservations with availability check
- View & manage all reservations
- Auto bill calculation
- Guest search by name
- Booking history reports
- Check-in / Check-out management
- Help section for staff

## Project Structure

```
OceanViewResort/
├── backend/          # Spring Boot REST API (port 8080)
└── frontend/         # React + Vite UI (port 5173)
```

## How to Run

**Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173` in your browser.

**Default credentials:**
- Admin: `admin` / `admin123`
- Staff: `staff` / `staff123`

## Module
CIS6003 - Advanced Programming | Cardiff Metropolitan University | 2025
