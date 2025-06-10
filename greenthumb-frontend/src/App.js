import React from 'react';
import './App.css';
import Navbar from './components/Navbar';
import AppRoutes from './routes';
import UserSyncManager from './components/UserSyncManager';

function App() {
  return (
    <>
      <UserSyncManager />
      <Navbar />
      <main>
        <AppRoutes />
      </main>
    </>
  );
}

export default App;