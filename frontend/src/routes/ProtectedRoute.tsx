import { Navigate, Outlet } from 'react-router-dom';

interface Props {
  allowedRoles?: string[];
}

export default function ProtectedRoute({ allowedRoles }: Props) {
  const token = localStorage.getItem('access_token');
  const role = localStorage.getItem('user_role');

  // Jar login nasel tar Login page var pathav
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // Jar role allowed nasel tar Home page var pathav (Farmer trying to access Admin)
  if (allowedRoles && role && !allowedRoles.includes(role)) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
}