import React, { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [emailValue, setEmailValue] = useState("");
  const [passwordValue, setPasswordValue] = useState("");
  const [nameValue, setNameValue] = useState("");
  const [surnameValue, setSurnameValue] = useState("");
  const [nameValid, setNameValid] = useState(null);
  const [surnameValid, setSurnameValid] = useState(null);
  const [emailValid, setEmailValid] = useState(null);
  const [passwordValid, setPasswordValid] = useState(null);
  const [invalid, setInvalid] = useState(false);
  const [logged, setLogged] = useState(false);
  const [requirements, setRequirements] = useState([]);
  const [checked, setChecked] = useState(false);
  const navigate = useNavigate();
  const handleNameChange = (event) => {
    const newValue = event.target.value;
    setNameValue(newValue);
  };

  const handleSurnameChange = (event) => {
    const newValue = event.target.value;
    setSurnameValue(newValue);
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

  useEffect(() => {
    if (
      nameValid === false &&
      surnameValid === false &&
      emailValid === false &&
      passwordValid === true &&
      currentPath === "/register"
    ) {
      const createdUser = {
        name: nameValue,
        surname: surnameValue,
        email: emailValue,
        password: passwordValue,
      };
      console.log(createdUser);
      // navigate("/");
    }
  }, [
    nameValid,
    surnameValid,
    emailValid,
    passwordValid,
    currentPath,
    navigate,
  ]);

  const validatePassword = (password) => {
    const missingRequirements = [];

    // Check minimum length of 8 characters
    if (password.length < 8) {
      missingRequirements.push("Minimum length of 8 characters.");
    }

    // Check presence of at least one uppercase letter
    if (!/[A-Z]/.test(password)) {
      missingRequirements.push("At least one uppercase letter.");
    }

    // Check presence of at least one number
    if (!/[0-9]/.test(password)) {
      missingRequirements.push("At least one number.");
    }

    // Check presence of at least one special character
    if (!/[^A-Za-z0-9]/.test(password)) {
      missingRequirements.push("At least one special character.");
    }

    // Return the missing requirements
    return missingRequirements;
  };

  const validateForm = async () => {
    if (currentPath === "/login") {
      // Validation for login
      return new Promise((res, rej) => {
        fetch("../../emails.json")
          .then((response) => {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error("Fatal error...");
            }
          })
          .then((data) => {
            const emailFounded = data.find((user) => user.email === emailValue);
            if (emailFounded === undefined) {
              setInvalid(false);
            }
            if (emailFounded.password !== passwordValue) {
              setInvalid(true);
            } else {
              setLogged(true);
              setInvalid(false);
              const userLogged = {
                email: emailValue,
                password: passwordValue,
                remember: checked,
              };
              console.log(userLogged);
              if (emailFounded.role === "admin") {
                console.log("redirecting to admin panel");
              } else {
                console.log("redirecting to user panel");
              }
            }
          })
          .catch((error) => {
            setLogged(false);
            setInvalid(true);
            console.error(error);
          });
      });
    }

    if (currentPath === "/register") {
      // Validation for registration
      return new Promise((res, rej) => {
        fetch("../../emails.json")
          .then((response) => {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error("Fatal error...");
            }
          })
          .then((data) => {
            const emailRegex =
              /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            const nameRegex = /^[a-zA-Z]{3,}(?:\s[a-zA-Z]+)*$/;
            const emailFounded = data.find((user) => user.email === emailValue);

            setEmailValid(
              emailFounded === undefined && emailRegex.test(emailValue)
            );
            setNameValid(!nameRegex.test(nameValue));
            setSurnameValid(!nameRegex.test(surnameValue));
            setEmailValid(!emailRegex.test(emailValue));

            const missingRequirements = validatePassword(passwordValue);
            setRequirements(missingRequirements);
            setPasswordValid(missingRequirements.length === 0);
          })
          .catch((error) => {
            console.error(error);
          });
      });
    }
  };

  const values = {
    emailValue,
    passwordValue,
    nameValue,
    surnameValue,
    handleNameChange,
    handleSurnameChange,
    handleEmailChange,
    handlePasswordChange,
    validateForm,
    invalid,
    setSurnameValue,
    setNameValue,
    setEmailValue,
    setPasswordValue,
    setInvalid,
    setNameValid,
    setSurnameValid,
    setEmailValid,
    setPasswordValid,
    nameValid,
    surnameValid,
    emailValid,
    passwordValid,
    requirements,
    setChecked,
    checked,
  };

  return <AuthContext.Provider value={values}>{children}</AuthContext.Provider>;
};
