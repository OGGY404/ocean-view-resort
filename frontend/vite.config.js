import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

/**
 * Vite configuration.
 * The proxy setting forwards /api calls to Spring Boot.
 * This means React can call /api/reservations and Vite
 * automatically forwards it to http://localhost:8080/api/reservations
 */
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
})
