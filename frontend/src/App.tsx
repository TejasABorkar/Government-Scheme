import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './components/layout/Layout';
import Home from './pages/public/Home';
import SchemeList from './pages/public/SchemeList'; // <-- Navin import
import Login from './pages/auth/Login'; // Jar aadhi banavla asel tar

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/schemes" element={<SchemeList />} /> {/* <-- Navin route */}
          <Route path="/login" element={<Login />} /> {/* Jar Login page asel tar */}
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;