import ChameleonCard from './components/ChameleonCard.jsx'
function App() {
  // Replace '12345' with a real ID from your backend
  const currentCustomerId = "CUST001";
  return (
      <div style={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        minHeight: '100vh',
        backgroundColor: '#f0f2f5'
      }}>
        <ChameleonCard customerId={currentCustomerId} />
      </div>
  );
}

export default App;
