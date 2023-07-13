import { ChangePassword } from "./components/LoginRegister/ChangePassword/ChangePassword";
import { PasswordResetEmailInput } from "./components/LoginRegister/PasswordResetEmailInput/PasswordResetEmailInput";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./components/Context/AuthContext";
import { Form } from "./components/LoginRegister/Form/Form";
import DashboardAdmin from "./components/DashboardAdmin";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Form type="login" />} />
          <Route path="/register" element={<Form type="register" />} />
          <Route
            path="/change-password"
            element={<PasswordResetEmailInput />}
          />

          <Route
            path={`/change-password/:token`}
            element={<ChangePassword />}
          />
          <Route path="/admin-dashboard" element={<DashboardAdmin />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}
export default App;
