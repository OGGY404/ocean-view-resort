import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import ProtectedRoute from './components/ProtectedRoute'
import Navbar from './components/Navbar'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import AddReservation from './pages/AddReservation'
import ViewReservations from './pages/ViewReservations'
import GuestSearch from './pages/GuestSearch'
import BillPage from './pages/BillPage'
import Reports from './pages/Reports'
import Help from './pages/Help'

/**
 * App.jsx — Root component. Defines all routes (pages).
 *
 * BrowserRouter: enables URL-based navigation
 * Routes + Route: maps URLs to page components
 * ProtectedRoute: redirects to login if not authenticated
 */
function Layout({ children }) {
  return (
    <>
      <Navbar />
      <div className="container">{children}</div>
    </>
  )
}

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          {/* Public route */}
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Navigate to="/dashboard" replace />} />

          {/* Protected routes — require login */}
          <Route element={<ProtectedRoute />}>
            <Route path="/dashboard"        element={<Layout><Dashboard /></Layout>} />
            <Route path="/reservations/new" element={<Layout><AddReservation /></Layout>} />
            <Route path="/reservations"     element={<Layout><ViewReservations /></Layout>} />
            <Route path="/search"           element={<Layout><GuestSearch /></Layout>} />
            <Route path="/bill/:number"     element={<Layout><BillPage /></Layout>} />
            <Route path="/reports"          element={<Layout><Reports /></Layout>} />
            <Route path="/help"             element={<Layout><Help /></Layout>} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
