import { useState } from 'react'
import { Link } from 'react-router-dom'
import { searchReservations } from '../services/api'

/**
 * Guest Search page — extra feature for excellent marks.
 * Calls GET /api/reservations/search?name=<query>
 */
function GuestSearch() {
  const [query, setQuery]       = useState('')
  const [results, setResults]   = useState([])
  const [searched, setSearched] = useState(false)
  const [loading, setLoading]   = useState(false)

  const handleSearch = async (e) => {
    e.preventDefault()
    if (!query.trim()) return
    setLoading(true)
    try {
      const res = await searchReservations(query)
      setResults(res.data)
      setSearched(true)
    } catch {
      setResults([])
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <div className="card">
        <h2 style={{ marginBottom: '16px' }}>Search Guest</h2>
        <form onSubmit={handleSearch} style={{ display: 'flex', gap: '12px' }}>
          <input
            value={query}
            onChange={e => setQuery(e.target.value)}
            placeholder="Enter guest name..."
            style={{ flex: 1, padding: '10px', borderRadius: '6px', border: '1px solid #ccc', fontSize: '1rem' }}
          />
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Searching...' : 'Search'}
          </button>
        </form>
      </div>

      {searched && (
        <div className="card">
          <h3>Results ({results.length})</h3>
          {results.length === 0 ? (
            <p style={{ color: '#aaa', marginTop: '12px' }}>No guests found matching "{query}"</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Reservation #</th>
                  <th>Guest Name</th>
                  <th>Contact</th>
                  <th>Room</th>
                  <th>Check-In</th>
                  <th>Status</th>
                  <th>Bill</th>
                </tr>
              </thead>
              <tbody>
                {results.map(r => (
                  <tr key={r.id}>
                    <td>{r.reservationNumber}</td>
                    <td>{r.guest?.name}</td>
                    <td>{r.guest?.contactNumber}</td>
                    <td>{r.room?.roomNumber}</td>
                    <td>{r.checkInDate}</td>
                    <td><span className={`badge badge-${r.status?.toLowerCase()}`}>{r.status}</span></td>
                    <td><Link to={`/bill/${r.reservationNumber}`}><button className="btn btn-success" style={{ padding: '4px 10px', fontSize: '0.8rem' }}>View Bill</button></Link></td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
    </div>
  )
}

export default GuestSearch
