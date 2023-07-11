import React, { useEffect, useState } from "react";
import eye from "../../../assets/eye.svg";
import eye_slash from "../../../assets/eye-slash.svg";
import { useContext } from "react";
import { AuthContext } from "../../Context/AuthContext";

export const InputPassword = () => {
  const { passwordValue, handlePasswordChange, requirements, invalid } =
    useContext(AuthContext);
  const [showPassword, setShowPassword] = useState(false);
  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
  };
  const currentPath = window.location.pathname;
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
              requirements.length > 0 && currentPath === "/register"
                ? "border-[red] border-2"
                : ""
            } ${
              currentPath === "/login" && invalid === true
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
        {currentPath === "/register" && requirements.length !== 0
          ? requirements.map((requirement) => {
              return (
                <p key={requirement} className="text-xs text-[red]">
                  {requirement}
                </p>
              );
            })
          : ""}
        {currentPath === "/login" && invalid === true ? (
          <p className="text-xs text-[red]">The credentials are invalid*</p>
        ) : (
          ""
        )}
      </div>
    </>
  );
};
