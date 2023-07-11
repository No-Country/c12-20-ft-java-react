import { useContext } from "react";
import { AuthContext } from "../../Context/AuthContext";

export const InputText = ({ type }) => {
  const {
    handleNameChange,
    handleSurnameChange,
    handleEmailChange,
    emailValue,
    nameValue,
    surnameValue,
    nameValid,
    surnameValid,
    emailValid,
    invalid,
  } = useContext(AuthContext);
  const currentPath = window.location.pathname;

  return (
    <>
      <div className="flex flex-col text-sm gap-2">
        <label className="font-semibold text-[#111111a8]">{type}*</label>
        <input
          type="text"
          value={
            type === "Email"
              ? emailValue
              : type === "Name"
              ? nameValue
              : type === "Surname"
              ? surnameValue
              : ""
          }
          onChange={
            type === "Email"
              ? handleEmailChange
              : type === "Name"
              ? handleNameChange
              : type === "Surname"
              ? handleSurnameChange
              : ""
          }
          placeholder={`Enter your ${type.toLocaleLowerCase()}`}
          className={`border h-10 border-[#2e2e2e80] outline-[#000] rounded-md p-3 pr-10 relative w-full ${
            type === "Name" && nameValid === true && currentPath === "/register"
              ? "border-[red] border-2"
              : ""
          }${
            type === "Surname" &&
            surnameValid === true &&
            currentPath === "/register"
              ? "border-[red] border-2"
              : ""
          }${
            type === "Email" &&
            emailValid === true &&
            currentPath === "/register"
              ? "border-[red] border-2"
              : ""
          }${
            currentPath === "/login" && invalid === true
              ? "border-[red] border-2"
              : ""
          }`}
        />
        {currentPath === "/register" &&
          ((type === "Name" && nameValid) ||
            (type === "Surname" && surnameValid) ||
            (type === "Email" && emailValid)) && (
            <p className="text-xs text-[red]">{`Invalid ${type.toLowerCase()}*`}</p>
          )}
      </div>
    </>
  );
};
