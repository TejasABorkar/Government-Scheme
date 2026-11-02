import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './components/layout/Layout';
import Home from './pages/public/Home';
import SchemeList from './pages/public/SchemeList';
import SchemeDetail from './pages/public/SchemeDetail';
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';

// Protected imports
import ProtectedRoute from './routes/ProtectedRoute';
import AdminDashboard from './pages/admin/AdminDashboard';
import AddScheme from './pages/admin/AddScheme';
import AdminSchemeList from './pages/admin/AdminSchemeList';
import FarmerDashboard from './pages/farmer/FarmerDashboard';
import ProfileWizard from './pages/farmer/ProfileWizard';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          
          {/* Public Routes */}
          <Route path="/" element={<Home />} />
          <Route path="/schemes" element={<SchemeList />} />
          <Route path="/schemes/:id" element={<SchemeDetail />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* Farmer Protected Routes */}
          <Route element={<ProtectedRoute allowedRoles={['FARMER']} />}>
            <Route path="/farmer-dashboard" element={<FarmerDashboard />} />
            <Route path="/profile" element={<ProfileWizard />} />
          </Route>

          {/* Admin Protected Routes */}
          <Route element={<ProtectedRoute allowedRoles={['ADMIN']} />}>
            <Route path="/admin" element={<AdminDashboard />} />
            <Route path="/admin/schemes" element={<AdminSchemeList />} />
            <Route path="/admin/schemes/new" element={<AddScheme />} />
          </Route>

        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;