import React from "react"
import { ChangePassword } from "./components/LoginRegister/ChangePassword/ChangePassword";
import { PasswordResetEmailInput } from "./components/LoginRegister/PasswordResetEmailInput/PasswordResetEmailInput";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./components/Context/AuthContext";
import { Form } from "./components/LoginRegister/Form/Form";
import DashboardUsers from "./components/DasboardUsers";
import DashboardAdmin from "./components/DashboardAdmin";
import DashboardClass from "./components/DashboardClass";
import Sidebar from "./components/shared/Sidebar";
import LandingPage from "./components/LandingPage";
import Scheduler from "./components/Scheduler";


export const App = () => {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/schedule" element={<Scheduler/>}/>
          <Route path="/login" element={<Form type="login" />} />
          <Route path="/register" element={<Form type="register" />} />
          <Route
            path="/change-password"element={<PasswordResetEmailInput />}/>

          <Route
            path={`/change-password/:token`}
            element={<ChangePassword />}
          />
          <Route path="/dashboard-users" element={<DashboardUsers />} />
          <Route path="/dashboard-admins" element={<DashboardAdmin />} />
          <Route path="/dashboard-classes" element={<DashboardClass />} />
          <Route path="/dashboard-payments" element={<Sidebar />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}