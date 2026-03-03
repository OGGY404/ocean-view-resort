import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { addReservation, getAvailableRooms } from '../services/api'

function AddReservation() {
  const navigate = useNavigate()
  const [rooms, setRooms]   = useState([])
  const [error, setError]   = useState('')
  const [success, setSuccess] = useState('')
  const [loading, setLoading] = useState(false)

  const [form, setForm] = useState({
    guestName: '', address: '', contactNumber: '',
    email: '', roomNumber: '', checkInDate: '', checkOutDate: ''
  })

  useEffect(() => {
    getAvailableRooms().then(r => setRooms(r.data)).catch(() => {})
  }, [])

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value })

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const res = await addReservation(form)
      setSuccess(`Reservation ${res.data.reservationNumber} created successfully!`)
      setTimeout(() => navigate(`/bill/${res.data.reservationNumber}`), 1500)
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create reservation. Check your inputs.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="card">
      <h2 style={{ marginBottom: '20px' }}>New Reservation</h2>

      {error   && <div className="alert alert-error">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      <form onSubmit={handleSubmit}>
        <div className="grid-2">
          <div className="form-group">
            <label>Guest Full Name *</label>
            <input name="guestName" value={form.guestName} onChange={handleChange} required placeholder="e.g. Kasun Perera" />
          </div>
          <div className="form-group">
            <label>Contact Number * (10 digits)</label>
            <input name="contactNumber" value={form.contactNumber} onChange={handleChange} required placeholder="0771234567" />
          </div>
        </div>

        <div className="form-group">
          <label>Address *</label>
          <input name="address" value={form.address} onChange={handleChange} required placeholder="No. 12, Main Street, Colombo" />
        </div>

        <div className="form-group">
          <label>Email</label>
          <input type="email" name="email" value={form.email} onChange={handleChange} placeholder="guest@email.com" />
        </div>

        <div className="form-group">
          <label>Select Room *</label>
          <select name="roomNumber" value={form.roomNumber} onChange={handleChange} required>
            <option value="">-- Select an available room --</option>
            {rooms.map(r => (
              <option key={r.id} value={r.roomNumber}>
                Room {r.roomNumber} — {r.roomType} — LKR {r.pricePerNight.toLocaleString()}/night
              </option>
            ))}
          </select>
          {rooms.length === 0 && <small style={{ color: 'red' }}>No rooms available</small>}
        </div>

        <div className="grid-2">
          <div className="form-group">
            <label>Check-In Date *</label>
            <input type="date" name="checkInDate" value={form.checkInDate} onChange={handleChange} required min={new Date().toISOString().split('T')[0]} />
          </div>
          <div className="form-group">
            <label>Check-Out Date *</label>
            <input type="date" name="checkOutDate" value={form.checkOutDate} onChange={handleChange} required min={form.checkInDate || new Date().toISOString().split('T')[0]} />
          </div>
        </div>

        <div style={{ display: 'flex', gap: '12px' }}>
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Creating...' : 'Create Reservation'}
          </button>
          <button type="button" className="btn btn-danger" onClick={() => navigate(-1)}>Cancel</button>
        </div>
      </form>
    </div>
  )
}

export default AddReservation
