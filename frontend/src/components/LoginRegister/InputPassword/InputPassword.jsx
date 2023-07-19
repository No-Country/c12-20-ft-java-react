import React, { useEffect, useState } from "react";
import eye from "../../../assets/eye.svg";
import eye_slash from "../../../assets/eye-slash.svg";
import { useContext } from "react";
import { AuthContext } from "../../Context/AuthContext";
import { useLocation } from "react-router-dom";

export const InputPassword = () => {
  const { passwordValue, handlePasswordChange, error } =
    useContext(AuthContext);
  const [showPassword, setShowPassword] = useState(false);
  const location = useLocation();
  const errorMessageLogin = error?.message;
  const errorMessage = error && error.password;

  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
  };

  return (
    <>
      <div className="flex flex-col text-sm gap-2">
        <label className="font-semibold text-[#111111a8]">Password*</label>
        <div className="relative">
          <input
            type={showPassword ? "text" : "password"}
            value={passwordValue}
            onChange={handlePasswordChange}
            placeholder="• • • • • • • • • •"
            className={`border h-10 border-[#2e2e2e80] outline-[#000] rounded-md p-3 pr-10 relative w-full ${
              errorMessage ? "border-[red] border-2" : ""
            }${
              errorMessageLogin &&
              errorMessageLogin != "El email no se encuentra registrado" &&
              location.pathname == "/login"
                ? "border-[red] border-2"
                : ""
            }`}
          />
          {passwordValue.length <= 0 ? (
            ""
          ) : (
            <span
              className="absolute right-3 top-1/2 transform -translate-y-1/2 cursor-pointer"
              onClick={handleTogglePassword}
            >
              {showPassword ? (
                <img src={eye} alt="" />
              ) : (
                <img src={eye_slash} alt="" />
              )}
            </span>
          )}
        </div>
        {errorMessage && <p className="text-red-500 text-xs">{errorMessage}</p>}
        {errorMessageLogin &&
        errorMessageLogin != "El email no se encuentra registrado" &&
        location.pathname == "/login" ? (
          <p className="text-red-500 text-xs">invalid password*</p>
        ) : (
          ""
        )}
      </div>
    </>
  );
};
