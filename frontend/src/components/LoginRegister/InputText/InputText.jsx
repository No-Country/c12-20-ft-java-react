import React from "react"
import { useContext } from "react";
import { AuthContext } from "../../Context/AuthContext";
import { useLocation } from "react-router-dom";

export const InputText = ({ type }) => {
  const {
    handleNameChange,
    handleLastnameChange,
    handleEmailChange,
    emailValue,
    nameValue,
    lastnameValue,
    error,
    emailValid,
  } = useContext(AuthContext);
  const location = useLocation();
  const errorMessageLogin = error?.title;
  const errorMessageRegister = error && error?.[type?.toLocaleLowerCase()];

  return (
    <>
      <div className="flex flex-col text-sm gap-2">
        <label className="font-semibold text-[#111111a8] text-left">
          {type}*
        </label>
        <input
          type="text"
          value={
            type === "Email"
              ? emailValue
              : type === "Name"
              ? nameValue
              : type === "Lastname"
              ? lastnameValue
              : ""
          }
          onChange={
            type === "Email"
              ? handleEmailChange
              : type === "Name"
              ? handleNameChange
              : type === "Lastname"
              ? handleLastnameChange
              : () => {}
          }
          placeholder={`Enter your ${type?.toLowerCase()}`}
          className={`border h-10 border-[#2e2e2e80] outline-[#000] rounded-md p-3 pr-10 relative w-full text-[#111] ${
            errorMessageRegister ? "border-[red] border-2" : ""
          }${
            errorMessageLogin &&
            errorMessageLogin !=
              "The password does not match the account password" &&
            location.pathname == "/login"
              ? "border-[red] border-2"
              : ""
          }`}
        />
        {errorMessageRegister && (
          <p className="text-red-500 text-xs">{errorMessageRegister}</p>
        )}
        {emailValid === false && type === "Email" && (
          <p className="text-red-500 text-xs">Email already taken*</p>
        )}
        {errorMessageLogin && (
          <p className="text-[red] text-xs">{errorMessageLogin}.</p>
        )}
      </div>
    </>
  );
};
