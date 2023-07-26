import React from "react"
import { Link } from "react-router-dom";
import leftArrow from "../../../assets/left-arrow.svg";
import check from "../../../assets/check.png";
import error from "../../../assets/error.png";
import { ButtonSign } from "../ButtonSign/ButtonSign";
import { HeaderForm } from "../HeaderForm/HeaderForm";
import { InputText } from "../InputText/InputText";
import { useState, useContext, useEffect } from "react";
import { AuthContext } from "../../Context/AuthContext";
import { GoogleButtonLogin } from "../GoogleButtonLogin/GoogleButtonLogin";
import primefitLogo from "../../../assets/primefit-logo.png";
import { Loader } from "../Loader";
export const PasswordResetEmailInput = () => {
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState();
  const { emailValue } = useContext(AuthContext);
  const validateChangePassword = async (e) => {
    e.preventDefault();

    try {
      setLoading(true);

      const response = await fetch(
        `https://c12-20-ft-java-react-production.up.railway.app/api/v1/passwords?email=${emailValue}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        console.log("email enviado.");
        console.log(response);
        setStatus(await response); // Actualizar el estado con un mensaje personalizado cuando la respuesta sea correcta
      } else {
        const errorResponse = await response.json();
        console.log("Error en la solicitud:", errorResponse);
        setStatus(errorResponse); // Actualizar el estado con el mensaje de error de la respuesta de la API
      }
    } catch (error) {
      console.log("Error en la solicitud:", error);
      setStatus("Error en la solicitud"); // Actualizar el estado con un mensaje personalizado cuando ocurra un error
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    console.log(status);
  }, [status]);
  return (
    <>
      <form
        onSubmit={validateChangePassword}
        className="flex justify-center h-screen items-center md:bg-[#fafafa] bg-white text-[#111]"
      >
        {loading ? (
          <Loader />
        ) : (
          <>
            {status ? (
              <>
                {status.status !== 400 ? (
                  <div className="flex flex-col justify-center items-center min-h-[507px] min-w-[475px] bg-white rounded-xl md:shadow-md gap-7">
                    <img className="w-20" src={check} alt="" />
                    <p>Email sent successfully.</p>
                    <Link
                      className="text-sm text-[#ff0000] hover:underline"
                      to="/"
                    >
                      Go home
                    </Link>
                  </div>
                ) : (
                  <div className="flex flex-col justify-center items-center min-h-[507px] min-w-[475px] bg-white rounded-xl md:shadow-md gap-7">
                    <img className="w-20" src={error} alt="" />
                    <p>{status.title}</p>
                    <Link
                      className="text-sm text-[#ff0000] hover:underline"
                      onClick={() => {
                        reload();
                      }}
                    >
                      Please, try again.
                    </Link>
                  </div>
                )}
              </>
            ) : (
              <div className="bg-white max-w-lg flex flex-col p-16 max-md:p-8 rounded-xl md:shadow-md gap-7 min-h-[500px] justify-center">
                <img className="w-20" src={primefitLogo} alt="logo" />
                <HeaderForm
                  title="Forgot password?"
                  parraf="No worries, we'll send you reset instructions."
                />
                <InputText type="Email" />
                <ButtonSign>Reset password</ButtonSign>
                <div className="relative">
                  <div className="bg-[#111] w-full h-[1px]"></div>
                  <p className="absolute top-[-10px] bg-white w-fit left-0 right-0 mx-auto px-2 text-sm">
                    or
                  </p>
                </div>
                <GoogleButtonLogin />
              </div>
            )}
          </>
        )}
      </form>
    </>
  );
};
