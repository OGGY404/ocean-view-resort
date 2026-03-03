import { createContext, useContext, useState } from 'react'

/**
 * Auth Context — global state management for authentication.
 *
 * What is Context?
 * React Context solves "prop drilling" — instead of passing user data
 * through every component, any component can read auth state directly.
 *
 * This implements the Observer Pattern: components subscribe to auth
 * state changes and re-render automatically when it changes.
 */
const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    // Restore user from localStorage on page refresh
    const token = localStorage.getItem('token')
    const username = localStorage.getItem('username')
    const role = localStorage.getItem('role')
    return token ? { token, username, role } : null
  })

  const loginUser = (userData) => {
    // Save to localStorage so user stays logged in on refresh
    localStorage.setItem('token', userData.token)
    localStorage.setItem('username', userData.username)
    localStorage.setItem('role', userData.role)
    setUser(userData)
  }

  const logoutUser = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    localStorage.removeItem('role')
    setUser(null)
  }

  return (
    <AuthContext.Provider value={{ user, loginUser, logoutUser }}>
      {children}
    </AuthContext.Provider>
  )
}

// Custom hook — clean way to access auth context
export const useAuth = () => useContext(AuthContext)
