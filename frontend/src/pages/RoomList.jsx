import { useEffect, useState } from 'react'
import { getRooms, addRoom, updateRoomPrice, deleteRoom } from '../services/api'

function RoomList() {
  const [rooms, setRooms] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  // Add room form state
  const [showAddForm, setShowAddForm] = useState(false)
  const [newRoom, setNewRoom] = useState({ roomNumber: '', roomType: 'SINGLE', pricePerNight: '' })

  // Edit price state — tracks which room is being edited
  const [editingRoom, setEditingRoom] = useState(null)
  const [editPrice, setEditPrice] = useState('')

  const fetchRooms = () => {
    setLoading(true)
    getRooms()
      .then(r => setRooms(r.data))
      .catch(() => setError('Failed to load rooms.'))
      .finally(() => setLoading(false))
  }

  useEffect(() => { fetchRooms() }, [])

  const handleAddRoom = async (e) => {
    e.preventDefault()
    setError('')
    try {
      await addRoom({ ...newRoom, pricePerNight: parseFloat(newRoom.pricePerNight) })
      setSuccess('Room added successfully!')
      setNewRoom({ roomNumber: '', roomType: 'SINGLE', pricePerNight: '' })
      setShowAddForm(false)
      fetchRooms()
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to add room.')
    }
  }

  const handleUpdatePrice = async (roomNumber) => {
    setError('')
    try {
      await updateRoomPrice(roomNumber, parseFloat(editPrice))
      setSuccess(`Price updated for Room ${roomNumber}`)
      setEditingRoom(null)
      fetchRooms()
    } catch {
      setError('Failed to update price.')
    }
  }

  const handleDelete = async (roomNumber) => {
    if (!window.confirm(`Delete Room ${roomNumber}? This cannot be undone.`)) return
    setError('')
    try {
      await deleteRoom(roomNumber)
      setSuccess(`Room ${roomNumber} deleted.`)
      fetchRooms()
    } catch {
      setError('Cannot delete room. It may have active reservations.')
    }
  }

  if (loading) return <div className="card"><p>Loading rooms...</p></div>

  return (
    <div className="card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '16px' }}>
        <h2>Room Management</h2>
        <button className="btn btn-primary" onClick={() => setShowAddForm(!showAddForm)}>
          {showAddForm ? 'Cancel' : '+ Add New Room'}
        </button>
      </div>

      {error   && <div className="alert alert-error">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      {showAddForm && (
        <form onSubmit={handleAddRoom} style={{ background: '#f9f9f9', padding: '16px', borderRadius: '8px', marginBottom: '20px' }}>
          <h3 style={{ marginBottom: '12px' }}>Add New Room</h3>
          <div className="grid-2">
            <div className="form-group">
              <label>Room Number *</label>
              <input
                value={newRoom.roomNumber}
                onChange={e => setNewRoom({ ...newRoom, roomNumber: e.target.value })}
                placeholder="e.g. 303" required
              />
            </div>
            <div className="form-group">
              <label>Room Type *</label>
              <select value={newRoom.roomType} onChange={e => setNewRoom({ ...newRoom, roomType: e.target.value })}>
                <option value="SINGLE">Single</option>
                <option value="DOUBLE">Double</option>
                <option value="SUITE">Suite</option>
              </select>
            </div>
          </div>
          <div className="form-group">
            <label>Price Per Night (LKR) *</label>
            <input
              type="number"
              value={newRoom.pricePerNight}
              onChange={e => setNewRoom({ ...newRoom, pricePerNight: e.target.value })}
              placeholder="e.g. 5000" required min="1"
            />
          </div>
          <button type="submit" className="btn btn-primary">Save Room</button>
        </form>
      )}

      <table>
        <thead>
          <tr>
            <th>Room No.</th>
            <th>Type</th>
            <th>Price/Night (LKR)</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {rooms.map(r => (
            <tr key={r.id}>
              <td><strong>{r.roomNumber}</strong></td>
              <td>{r.roomType}</td>
              <td>
                {editingRoom === r.roomNumber ? (
                  <div style={{ display: 'flex', gap: '8px', alignItems: 'center' }}>
                    <input
                      type="number"
                      value={editPrice}
                      onChange={e => setEditPrice(e.target.value)}
                      style={{ width: '100px' }}
                    />
                    <button className="btn btn-primary" style={{ padding: '4px 10px', fontSize: '12px' }}
                      onClick={() => handleUpdatePrice(r.roomNumber)}>Save</button>
                    <button className="btn btn-danger" style={{ padding: '4px 10px', fontSize: '12px' }}
                      onClick={() => setEditingRoom(null)}>X</button>
                  </div>
                ) : (
                  r.pricePerNight.toLocaleString()
                )}
              </td>
              <td>
                <span className={`badge ${r.available ? 'badge-confirmed' : 'badge-cancelled'}`}>
                  {r.available ? 'Available' : 'Occupied'}
                </span>
              </td>
              <td style={{ display: 'flex', gap: '8px' }}>
                <button
                  className="btn btn-primary"
                  style={{ padding: '4px 12px', fontSize: '12px' }}
                  onClick={() => { setEditingRoom(r.roomNumber); setEditPrice(r.pricePerNight) }}
                >
                  Edit Price
                </button>
                <button
                  className="btn btn-danger"
                  style={{ padding: '4px 12px', fontSize: '12px' }}
                  onClick={() => handleDelete(r.roomNumber)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default RoomList
