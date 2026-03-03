import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { getReservation } from '../services/api'

/**
 * Bill Page — displays calculated bill for a reservation.
 * URL: /bill/:reservationNumber
 */
function BillPage() {
  const { number } = useParams()
  const navigate   = useNavigate()
  const [res, setRes]     = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    getReservation(number)
      .then(r => setRes(r.data))
      .catch(() => navigate('/reservations'))
      .finally(() => setLoading(false))
  }, [number])

  if (loading) return <div className="card"><p>Loading bill...</p></div>
  if (!res) return null

  return (
    <div className="card" style={{ maxWidth: '600px', margin: '0 auto' }}>
      {/* Bill Header */}
      <div style={{ textAlign: 'center', borderBottom: '2px solid #2e6da4', paddingBottom: '16px', marginBottom: '20px' }}>
        <h2 style={{ color: '#1a3a5c' }}>🏨 Ocean View Resort</h2>
        <p style={{ color: '#777' }}>Galle, Sri Lanka</p>
        <h3 style={{ marginTop: '10px' }}>INVOICE</h3>
      </div>

      {/* Reservation Info */}
      <table style={{ marginBottom: '20px' }}>
        <tbody>
          <tr><td><strong>Reservation No:</strong></td><td>{res.reservationNumber}</td></tr>
          <tr><td><strong>Guest Name:</strong></td>    <td>{res.guest?.name}</td></tr>
          <tr><td><strong>Address:</strong></td>       <td>{res.guest?.address}</td></tr>
          <tr><td><strong>Contact:</strong></td>       <td>{res.guest?.contactNumber}</td></tr>
          <tr><td><strong>Room:</strong></td>          <td>{res.room?.roomNumber} — {res.room?.roomType}</td></tr>
          <tr><td><strong>Check-In:</strong></td>      <td>{res.checkInDate}</td></tr>
          <tr><td><strong>Check-Out:</strong></td>     <td>{res.checkOutDate}</td></tr>
          <tr><td><strong>Status:</strong></td>
            <td><span className={`badge badge-${res.status?.toLowerCase()}`}>{res.status}</span></td>
          </tr>
        </tbody>
      </table>

      {/* Bill Calculation */}
      <div style={{ background: '#f5f9ff', padding: '16px', borderRadius: '8px', border: '1px solid #dce8f5' }}>
        <h4 style={{ marginBottom: '12px', color: '#1a3a5c' }}>Bill Summary</h4>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
          <span>Room rate per night:</span>
          <span>LKR {res.bill?.pricePerNight?.toLocaleString()}</span>
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
          <span>Number of nights:</span>
          <span>{res.bill?.numberOfNights}</span>
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '1.1rem', fontWeight: 'bold',
                      borderTop: '1px solid #ccc', paddingTop: '10px', marginTop: '10px', color: '#1a3a5c' }}>
          <span>TOTAL AMOUNT:</span>
          <span>LKR {res.bill?.totalAmount?.toLocaleString()}</span>
        </div>
        <div style={{ marginTop: '10px', color: res.bill?.paid ? '#27ae60' : '#e74c3c', fontWeight: 'bold' }}>
          {res.bill?.paid ? '✓ PAID' : '⚠ PAYMENT PENDING'}
        </div>
      </div>

      <div style={{ display: 'flex', gap: '12px', marginTop: '20px' }}>
        <button className="btn btn-primary" onClick={() => window.print()}>Print Bill</button>
        <button className="btn btn-danger"  onClick={() => navigate('/reservations')}>Back</button>
      </div>
    </div>
  )
}

export default BillPage
