import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getReservations, updateStatus } from '../services/api'

function ViewReservations() {
  const [reservations, setReservations] = useState([])
  const [loading, setLoading] = useState(true)

  const load = () => {
    getReservations()
      .then(r => setReservations(r.data))
      .finally(() => setLoading(false))
  }

  useEffect(() => { load() }, [])

  const handleStatusChange = async (number, status) => {
    await updateStatus(number, status)
    load()
  }

  if (loading) return <div className="card"><p>Loading...</p></div>

  return (
    <div className="card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '16px' }}>
        <h2>All Reservations ({reservations.length})</h2>
        <Link to="/reservations/new"><button className="btn btn-primary">+ New Booking</button></Link>
      </div>

      <table>
        <thead>
          <tr>
            <th>Res. Number</th>
            <th>Guest</th>
            <th>Contact</th>
            <th>Room</th>
            <th>Check-In</th>
            <th>Check-Out</th>
            <th>Total (LKR)</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {reservations.map(r => (
            <tr key={r.id}>
              <td><strong>{r.reservationNumber}</strong></td>
              <td>{r.guest?.name}</td>
              <td>{r.guest?.contactNumber}</td>
              <td>{r.room?.roomNumber} ({r.room?.roomType})</td>
              <td>{r.checkInDate}</td>
              <td>{r.checkOutDate}</td>
              <td>{r.bill?.totalAmount?.toLocaleString()}</td>
              <td>
                <span className={`badge badge-${r.status?.toLowerCase()}`}>{r.status}</span>
              </td>
              <td>
                <Link to={`/bill/${r.reservationNumber}`}>
                  <button className="btn btn-success" style={{ padding: '4px 10px', fontSize: '0.8rem' }}>Bill</button>
                </Link>
                {r.status === 'CONFIRMED' && (
                  <button className="btn btn-primary" style={{ padding: '4px 10px', fontSize: '0.8rem', marginLeft: '4px' }}
                    onClick={() => handleStatusChange(r.reservationNumber, 'CHECKED_IN')}>
                    Check In
                  </button>
                )}
                {r.status === 'CHECKED_IN' && (
                  <button className="btn btn-warning" style={{ padding: '4px 10px', fontSize: '0.8rem', marginLeft: '4px' }}
                    onClick={() => handleStatusChange(r.reservationNumber, 'CHECKED_OUT')}>
                    Check Out
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {reservations.length === 0 && (
        <p style={{ textAlign: 'center', color: '#aaa', padding: '30px' }}>No reservations found.</p>
      )}
    </div>
  )
}

export default ViewReservations
