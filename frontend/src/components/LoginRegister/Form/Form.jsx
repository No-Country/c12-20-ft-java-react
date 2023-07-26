
import { InputPassword } from "../InputPassword/InputPassword";
import primefitLogo from "../../../assets/img/logo2.png";
import { Link } from "react-router-dom";
import { ButtonSign } from "../ButtonSign/ButtonSign";
import { HeaderForm } from "../HeaderForm/HeaderForm";
import { InputText } from "../InputText/InputText";
import { AuthContext } from "../../Context/AuthContext";
import { useNavigate } from "react-router-dom";
import { useContext } from "react";
import "../../../index.css"

export const Form = ({ type }) => {
  const navigate = useNavigate();

  const {
    setLastnameValue,
    setNameValue,
    setPasswordValue,
    setEmailValue,
    setChecked,
    checked,
    isLoading,
    setError,
    validateForm,
  } = useContext(AuthContext);
  const handleCheckboxChange = (event) => {
    setChecked(event.target.checked);
  };
  const handleReset = () => {
    setEmailValue("");
    setPasswordValue("");
    setNameValue("");
    setLastnameValue("");
    setError(null);
  };
  
  const handleSubmit = (e) => {
    e.preventDefault();
    validateForm();
  };
  return (
    <section className="contLogin">
      <div className="flex justify-center min-h-screen items-center max-sm:bg-[#fff] text-[#111]">
        <form
          onSubmit={handleSubmit}
          className="bg-white flex flex-col gap-7 p-16 max-md:p-8 rounded-xl md:shadow-md"
        >
          {type === "login" ? (
            <>
              <Link to= {"/"}>
                <img className="w-20" src={primefitLogo} alt="logo" />
              </Link>
              <div>
                <HeaderForm
                  title="Welcome back"
                  parraf="Welcome back! Please enter your details."
                />
              </div>
              <InputText type="Email" placeholder="Email" />
              <InputPassword />
              <div className="flex justify-between text-sm">
                <div className="flex items-center justify-center gap-2">
                  <input
                    type="checkbox"
                    checked={checked}
                    onChange={handleCheckboxChange}
                  />
                  <p className="text-[#111]">Remember me</p>
                </div>
                <Link
                  to="/change-password"
                  onClick={() => {
                    handleReset();
                  }}
                  className="text-[#FF0000] hover:underline"
                >
                  Forgot password?
                </Link>
              </div>
              <ButtonSign>Log In</ButtonSign>

              <p className="text-sm text-center hover:[&>a]:underline text-[#111111a8]">
                Don&apos;t have an account?
                <button
                  onClick={(e) => {
                    e.preventDefault();
                    handleReset();
                    navigate("/register");
                  }}
                  className="text-[#FF0000] ml-1 hover:underline "
                >
                  Sign Up
                </button>
              </p>
            </>
          ) : (
            <>
              {isLoading && <div className="progressBar"></div>}
              <img className="w-20" src={primefitLogo} alt="logo" />
              <div>
                <HeaderForm
                  title="Get started now"
                  parraf="Enter your credentials to create your account."
                />
              </div>
              <InputText type="Name" />
              <InputText type="Lastname" />
              <InputText type="Email" />
              <InputPassword />
              <ButtonSign>Sign up</ButtonSign>
              <p className="text-sm text-center hover:[&>a]:underline text-[#111]">
                Already have an account?
                <button
                  onClick={(e) => {
                    e.preventDefault();
                    handleReset();
                    navigate("/login");
                  }}
                  className="text-[#FF0000] ml-1 hover:underline"
                >
                  Log in
                </button>
              </p>
            </>
          )}
        </form>
      </div>
    </section>
  );
};
