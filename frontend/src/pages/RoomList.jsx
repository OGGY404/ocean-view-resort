import { useEffect, useState } from 'react'
import { getRooms } from '../services/api'

/**
 * Room List page — Version 3 feature.
 * Shows all rooms with their current availability status.
 */
function RoomList() {
  const [rooms, setRooms] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    getRooms().then(r => setRooms(r.data)).finally(() => setLoading(false))
  }, [])

  if (loading) return <div className="card"><p>Loading rooms...</p></div>

  return (
    <div className="card">
      <h2 style={{ marginBottom: '16px' }}>Room Management</h2>
      <table>
        <thead>
          <tr>
            <th>Room No.</th>
            <th>Type</th>
            <th>Price/Night (LKR)</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {rooms.map(r => (
            <tr key={r.id}>
              <td><strong>{r.roomNumber}</strong></td>
              <td>{r.roomType}</td>
              <td>{r.pricePerNight.toLocaleString()}</td>
              <td>
                <span className={`badge ${r.available ? 'badge-confirmed' : 'badge-cancelled'}`}>
                  {r.available ? 'Available' : 'Occupied'}
                </span>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default RoomList
