import React, { createContext, useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [emailValue, setEmailValue] = useState("");
  const [passwordValue, setPasswordValue] = useState("");
  const [nameValue, setNameValue] = useState("");
  const [lastnameValue, setLastnameValue] = useState("");
  const [nameValid, setNameValid] = useState(null);
  const [lastnameValid, setLastnameValid] = useState(null);
  const [emailValid, setEmailValid] = useState(null);
  const [passwordValid, setPasswordValid] = useState(null);
  const [invalid, setInvalid] = useState(false);
  const [checked, setChecked] = useState(false);
  const [error, setError] = useState({});
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    setEmailValue("");
    setPasswordValue("");
  }, [location.pathname]);

  const handleNameChange = (event) => {
    const newValue = event.target.value;
    setNameValue(newValue);
  };

  const handleLastnameChange = (event) => {
    const newValue = event.target.value;
    setLastnameValue(newValue);
  };

  const handleEmailChange = (event) => {
    const newValue = event.target.value;
    setEmailValue(newValue);
  };

  const handlePasswordChange = (event) => {
    const newValue = event.target.value;
    setPasswordValue(newValue);
  };

  const currentPath = window.location.pathname;

  const validateForm = async () => {
    if (currentPath === "/login") {
      // Validation for login
      const authLogin = async (email, password) => {
        try {
          const response = await fetch(
            "https://c12-20-ft-java-react-production.up.railway.app/api/v1/users/authentication",
            {
              method: "POST",
              body: JSON.stringify({
                email: email,
                password: password,
              }),
              headers: {
                "Content-Type": "application/json",
              },
            }
          );
            
          if (response.status === 400) {
            const errorInfo = await response.json();
            setError(errorInfo);
            console.log(errorInfo);
          } else {
            const responseData = await response.json();
            const responseDataString = JSON.stringify(responseData);
            setError(null);
            if (
              responseData.user.role === "ADMIN" ||
              responseData.user.role === "EMPLOYEE"
            ) {
              localStorage.setItem("user", responseData);
             
              window.location.href = 'http://localhost:4200/dashboard/'+responseData.user.id;
            } else {
              window.location.href = 'http://localhost:4200/user/'+responseData.user.id;
              if (checked === true) {
                localStorage.setItem("user", responseDataString);
              } else {
                sessionStorage.setItem("user", responseDataString);
              }
            }
          }
        } catch (error) {
          console.log("Error en la solicitud:", error);
        }
      };
      authLogin(emailValue, passwordValue);
    }

    if (currentPath === "/register") {
      // Validation for register
      const authRegister = async () => {
        try {
          const res = await fetch(
            "https://c12-20-ft-java-react-production.up.railway.app/api/v1/users/customers",
            {
              method: "POST",
              body: JSON.stringify({
                name: nameValue,
                lastname: lastnameValue,
                email: emailValue,
                password: passwordValue,
              }),
              headers: {
                "Content-Type": "application/json",
              },
            }
          );

          if (res.ok) {
            const data = await res;
            console.log(data);
            toast.success("¡Cuenta creada exitosamente!");
            setError(null);
            setEmailValid(true);
            navigate("/login");
          } else if (res.status === 409) {
            setEmailValid(false);
            console.log("Conflicto: Ya existe un usuario con esa información");
            console.log(await res.json());
            setError(null);
          } else {
            console.log("Error en la solicitud:", res.status);
            const errorInfo = await res.json();
            setError(errorInfo);
            console.log(errorInfo);
          }
        } catch (error) {
          console.log("Error en la solicitud:", error);
          setError({ message: "Error en la solicitud" });
        }
      };

      authRegister();
    }
  };

  const values = {
    nameValue,
    lastnameValue,
    emailValue,
    passwordValue,
    nameValid,
    lastnameValid,
    emailValid,
    passwordValid,
    handleNameChange,
    handleLastnameChange,
    handleEmailChange,
    handlePasswordChange,
    validateForm,
    invalid,
    setLastnameValue,
    setNameValue,
    setEmailValue,
    setPasswordValue,
    setInvalid,
    setNameValid,
    setLastnameValid,
    setEmailValid,
    setPasswordValid,
    setChecked,
    checked,
    error,
    setError,
  };

  return (
    <>
      <AuthContext.Provider value={values}>
        {children}
        <ToastContainer />
      </AuthContext.Provider>
    </>
  );
};
