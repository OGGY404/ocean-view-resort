/**
 * Help Page — required by the assignment (Task 5 in scenario).
 * Provides guidelines for new staff members.
 */
function Help() {
  return (
    <div className="card">
      <h2 style={{ marginBottom: '20px' }}>Help & User Guide</h2>
      <p style={{ color: '#777', marginBottom: '24px' }}>
        Welcome to the Ocean View Resort Reservation System. This guide explains how to use the system.
      </p>

      <Section title="1. Logging In">
        <li>Go to the login page and enter your username and password provided by the administrator.</li>
        <li>Default credentials: <strong>admin / admin123</strong> (Admin) or <strong>staff / staff123</strong> (Staff).</li>
        <li>After login you will be taken to the Dashboard automatically.</li>
      </Section>

      <Section title="2. Creating a New Reservation">
        <li>Click <strong>New Booking</strong> in the navigation bar.</li>
        <li>Fill in guest details: name, address, contact number, and email.</li>
        <li>Select an available room from the dropdown.</li>
        <li>Choose check-in and check-out dates. Check-out must be after check-in.</li>
        <li>Click <strong>Create Reservation</strong>. You will be redirected to the bill page.</li>
      </Section>

      <Section title="3. Viewing & Managing Reservations">
        <li>Click <strong>All Bookings</strong> to see every reservation.</li>
        <li>Use the <strong>Check In</strong> button when the guest arrives.</li>
        <li>Use the <strong>Check Out</strong> button when the guest leaves — this frees the room.</li>
        <li>Click <strong>Bill</strong> to view and print the invoice for any reservation.</li>
      </Section>

      <Section title="4. Searching for a Guest">
        <li>Click <strong>Search Guest</strong> in the navigation bar.</li>
        <li>Type the guest's name (partial names work, e.g. "Kasu" finds "Kasun Perera").</li>
        <li>All matching reservations will be displayed.</li>
      </Section>

      <Section title="5. Generating Reports">
        <li>Click <strong>Reports</strong> in the navigation bar.</li>
        <li>Select a start and end date, then click <strong>Generate Report</strong>.</li>
        <li>The report shows all bookings in that date range and total revenue earned.</li>
      </Section>

      <Section title="6. Room Types & Pricing">
        <li><strong>Single Room:</strong> LKR 5,000 per night</li>
        <li><strong>Double Room:</strong> LKR 8,500 per night</li>
        <li><strong>Suite:</strong> LKR 15,000 per night</li>
      </Section>

      <div style={{ marginTop: '24px', padding: '14px', background: '#fff3cd', borderRadius: '8px', color: '#856404' }}>
        <strong>Need further help?</strong> Contact the system administrator at the front desk.
      </div>
    </div>
  )
}

function Section({ title, children }) {
  return (
    <div style={{ marginBottom: '24px' }}>
      <h3 style={{ color: '#1a3a5c', marginBottom: '10px', borderBottom: '1px solid #eee', paddingBottom: '6px' }}>{title}</h3>
      <ul style={{ paddingLeft: '20px', lineHeight: '1.9' }}>{children}</ul>
    </div>
  )
}

export default Help
