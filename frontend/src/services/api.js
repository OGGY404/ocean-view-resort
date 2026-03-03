import axios from 'axios'

/**
 * Axios API service — central place for all HTTP calls to the backend.
 *
 * Why centralise API calls here?
 * - Single place to update the base URL
 * - Automatically attaches JWT token to every request
 * - Consistent error handling
 *
 * This follows the Service Layer pattern on the frontend.
 */
const api = axios.create({
  baseURL: '/api',
  headers: { 'Content-Type': 'application/json' }
})

// Interceptor: runs before EVERY request
// Automatically adds "Authorization: Bearer <token>" header
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// Auth
export const login = (credentials) => api.post('/auth/login', credentials)

// Reservations
export const getReservations    = ()       => api.get('/reservations')
export const getReservation     = (number) => api.get(`/reservations/${number}`)
export const addReservation     = (data)   => api.post('/reservations', data)
export const searchReservations = (name)   => api.get(`/reservations/search?name=${name}`)
export const updateStatus       = (number, status) =>
  api.put(`/reservations/${number}/status?status=${status}`)

// Rooms
export const getRooms          = ()    => api.get('/rooms')
export const getAvailableRooms = ()    => api.get('/rooms/available')

// Reports
export const getBookingHistory = (start, end) =>
  api.get(`/reports/booking-history?start=${start}&end=${end}`)

export default api
