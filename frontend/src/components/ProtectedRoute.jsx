import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

/**
 * ProtectedRoute — guards all pages that require login.
 *
 * How it works:
 * - If user is logged in → render the page normally (Outlet)
 * - If not logged in → redirect to /login
 *
 * This is the Guard Pattern / Access Control Pattern.
 * The lecturer may ask: "How did you protect your routes?"
 */
function ProtectedRoute() {
  const { user } = useAuth()
  return user ? <Outlet /> : <Navigate to="/login" replace />
}

export default ProtectedRoute
