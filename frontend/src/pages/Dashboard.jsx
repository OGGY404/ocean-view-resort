import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getReservations, getAvailableRooms } from '../services/api'
import { useAuth } from '../context/AuthContext'

function Dashboard() {
  const { user } = useAuth()
  const [reservations, setReservations] = useState([])
  const [availableRooms, setAvailableRooms] = useState([])

  useEffect(() => {
    getReservations().then(r => setReservations(r.data)).catch(() => {})
    getAvailableRooms().then(r => setAvailableRooms(r.data)).catch(() => {})
  }, [])

  const confirmed  = reservations.filter(r => r.status === 'CONFIRMED').length
  const checkedIn  = reservations.filter(r => r.status === 'CHECKED_IN').length
  const checkedOut = reservations.filter(r => r.status === 'CHECKED_OUT').length

  return (
    <div>
      <div className="card">
        <h2>Welcome, {user?.username}!</h2>
        <p style={{ color: '#777', marginTop: '6px' }}>Ocean View Resort — Reservation Management System</p>
      </div>

      {/* Statistics */}
      <div className="stats-grid">
        <div className="stat-card">
          <h3>{reservations.length}</h3>
          <p>Total Reservations</p>
        </div>
        <div className="stat-card">
          <h3 style={{ color: '#27ae60' }}>{confirmed}</h3>
          <p>Confirmed</p>
        </div>
        <div className="stat-card">
          <h3 style={{ color: '#2980b9' }}>{checkedIn}</h3>
          <p>Checked In</p>
        </div>
        <div className="stat-card">
          <h3 style={{ color: '#27ae60' }}>{availableRooms.length}</h3>
          <p>Available Rooms</p>
        </div>
      </div>

      {/* Quick Actions */}
      <div className="card">
        <h3 style={{ marginBottom: '16px' }}>Quick Actions</h3>
        <div style={{ display: 'flex', gap: '12px', flexWrap: 'wrap' }}>
          <Link to="/reservations/new"><button className="btn btn-primary">+ New Booking</button></Link>
          <Link to="/reservations"><button className="btn btn-success">View All Bookings</button></Link>
          <Link to="/search"><button className="btn btn-warning">Search Guest</button></Link>
          <Link to="/reports"><button className="btn btn-primary">View Reports</button></Link>
        </div>
      </div>

      {/* Recent Reservations */}
      <div className="card">
        <h3 style={{ marginBottom: '16px' }}>Recent Reservations</h3>
        <table>
          <thead>
            <tr>
              <th>Reservation #</th>
              <th>Guest Name</th>
              <th>Room</th>
              <th>Check-In</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {reservations.slice(0, 5).map(r => (
              <tr key={r.id}>
                <td>{r.reservationNumber}</td>
                <td>{r.guest?.name}</td>
                <td>{r.room?.roomNumber} ({r.room?.roomType})</td>
                <td>{r.checkInDate}</td>
                <td>
                  <span className={`badge badge-${r.status?.toLowerCase()}`}>
                    {r.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {reservations.length === 0 && (
          <p style={{ textAlign: 'center', color: '#aaa', padding: '20px' }}>
            No reservations yet. <Link to="/reservations/new">Add one!</Link>
          </p>
        )}
      </div>
    </div>
  )
}

export default Dashboard
