<<<<<<< HEAD
import { ChangePassword } from "./components/LoginRegister/ChangePassword/ChangePassword";
import { PasswordResetEmailInput } from "./components/LoginRegister/PasswordResetEmailInput/PasswordResetEmailInput";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useState } from "react";
import { AuthProvider } from "./components/Context/AuthContext";
import { Form } from "./components/LoginRegister/Form/Form";

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
        </Routes>
      </AuthProvider>
    </BrowserRouter>
=======
import { Fragment } from "react";
import DasboardAdmin from "./components/DashboardAdmin";

function App() {
  return (
    <>
      <DasboardAdmin />
  </>  
>>>>>>> 2b69a60108f1440e2659c20bdef694f45ae2d726
  );
}
export default App;
