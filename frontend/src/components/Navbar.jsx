import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

function Navbar() {
  const { user, logoutUser } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logoutUser()
    navigate('/login')
  }

  return (
    <nav className="navbar">
      <h1>🏨 Ocean View Resort</h1>
      <div>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/reservations/new">New Booking</Link>
        <Link to="/reservations">All Bookings</Link>
        <Link to="/search">Search Guest</Link>
        <Link to="/reports">Reports</Link>
        <Link to="/help">Help</Link>
        <span style={{ marginLeft: '20px', opacity: 0.8 }}>
          {user?.username} ({user?.role === 'ROLE_ADMIN' ? 'Admin' : 'Staff'})
        </span>
        <button onClick={handleLogout} style={{ marginLeft: '12px' }}>Logout</button>
      </div>
    </nav>
  )
}

export default Navbar
