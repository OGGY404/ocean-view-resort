import { useState } from 'react'
import { getBookingHistory } from '../services/api'

/**
 * Reports Page — booking history report filtered by date range.
 * Satisfies Task B: "proposed reports to facilitate decision-making"
 */
function Reports() {
  const [startDate, setStartDate] = useState('')
  const [endDate, setEndDate]     = useState('')
  const [results, setResults]     = useState([])
  const [searched, setSearched]   = useState(false)

  const totalRevenue = results.reduce((sum, r) => sum + (r.bill?.totalAmount || 0), 0)

  const handleReport = async (e) => {
    e.preventDefault()
    const res = await getBookingHistory(startDate, endDate)
    setResults(res.data)
    setSearched(true)
  }

  return (
    <div>
      <div className="card">
        <h2 style={{ marginBottom: '16px' }}>Booking History Report</h2>
        <form onSubmit={handleReport}>
          <div className="grid-2">
            <div className="form-group">
              <label>From Date</label>
              <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} required />
            </div>
            <div className="form-group">
              <label>To Date</label>
              <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} required />
            </div>
          </div>
          <button type="submit" className="btn btn-primary">Generate Report</button>
        </form>
      </div>

      {searched && (
        <div className="card">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '16px' }}>
            <h3>Results: {results.length} reservations</h3>
            <div style={{ background: '#d4edda', padding: '8px 16px', borderRadius: '8px', color: '#155724', fontWeight: 'bold' }}>
              Total Revenue: LKR {totalRevenue.toLocaleString()}
            </div>
          </div>

          <table>
            <thead>
              <tr>
                <th>Res. Number</th>
                <th>Guest</th>
                <th>Room</th>
                <th>Check-In</th>
                <th>Check-Out</th>
                <th>Nights</th>
                <th>Amount (LKR)</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {results.map(r => (
                <tr key={r.id}>
                  <td>{r.reservationNumber}</td>
                  <td>{r.guest?.name}</td>
                  <td>{r.room?.roomNumber}</td>
                  <td>{r.checkInDate}</td>
                  <td>{r.checkOutDate}</td>
                  <td>{r.bill?.numberOfNights}</td>
                  <td>{r.bill?.totalAmount?.toLocaleString()}</td>
                  <td><span className={`badge badge-${r.status?.toLowerCase()}`}>{r.status}</span></td>
                </tr>
              ))}
            </tbody>
          </table>
          {results.length === 0 && <p style={{ textAlign: 'center', color: '#aaa', padding: '20px' }}>No bookings in this date range.</p>}
        </div>
      )}
    </div>
  )
}

export default Reports
