import React from "react"
import { useEffect, useState } from "react";
import { ButtonSign } from "../ButtonSign/ButtonSign";
import eye from "../../../assets/eye.svg";
import eye_slash from "../../../assets/eye-slash.svg";
import { Link } from "react-router-dom";
import { Loader } from "../Loader";

export const ChangePassword = () => {
  const [showForm, setShowForm] = useState(false);
  const [inputValue1, setInputValue1] = useState("");
  const [inputValue2, setInputValue2] = useState("");
  const [loading, setLoading] = useState(true); // Nuevo estado para indicar si la validación está en progreso o no

  const handleInputChange1 = (event) => {
    setInputValue1(event.target.value);
  };

  const handleInputChange2 = (event) => {
    setInputValue2(event.target.value);
  };

  useEffect(() => {
    validateToken();
  }, []); // Usar un array de dependencias vacío para que la validación se realice solo una vez al cargar el componente

  const validateToken = async () => {
    try {
      const currentURL = window.location.href;
      const parts = currentURL.split("/change-password/");
      const afterChangePassword = parts[1];
      const extractedPart = afterChangePassword;
      const response = await fetch(
        `https://c12-20-ft-java-react-production.up.railway.app/api/v1/passwords/${extractedPart}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        console.log("mostrar form para cambiar contraseña");
        console.log(response);
        setShowForm(true);
      } else {
        const errorResponse = await response.json();
        console.log("Error en la solicitud:", errorResponse);
        setShowForm(false);
      }
    } catch (error) {
      console.log("Error en la solicitud:", error);
      setShowForm(false);
    } finally {
      setLoading(false); // Finalizar el estado de carga después de obtener la respuesta del servidor
    }
  };

  return (
    <>
      {loading ? (
        // Mostrar un mensaje de carga mientras se realiza la validación
        <div className="grid h-screen place-items-center">
          <Loader />
        </div>
      ) : showForm ? (
        <form
          onSubmit={() => {
            navigate("/login");
          }}
          className="h-screen w-full flex justify-center items-center md:bg-[#fafafa] text-[#111]"
        >
          <div className="bg-white flex flex-col gap-6 md:p-14 p-5 md:shadow-md rounded-xl max-w-md">
            <div className="flex flex-col gap-1">
              <h1 className="text-3xl font-semibold">Create new password</h1>
              <p className="font-normal text-[#111111a8]">
                Your new password must be different from previously used
                passwords.
              </p>
            </div>
            <div className="relative flex flex-col gap-3">
              <label className="font-semibold text-[#111111a8] text-sm">
                Password
              </label>
              <input
                type="password"
                placeholder="• • • • • • • • • •"
                value={inputValue1}
                onChange={handleInputChange1}
                className={`border h-10 border-[#2e2e2e80] outline-[#000] rounded-md p-3 pr-10 relative w-full`}
              />
            </div>
            <div className="relative flex flex-col gap-3">
              <label className="font-semibold text-[#111111a8] text-sm">
                Confirm Password
              </label>
              <input
                type="password"
                placeholder="• • • • • • • • • •"
                value={inputValue2}
                onChange={handleInputChange2}
                className={`border h-10 border-[#2e2e2e80] outline-[#000] rounded-md p-3 pr-10 relative w-full`}
              />
            </div>
            <ButtonSign>Reset password</ButtonSign>
          </div>
        </form>
      ) : (
        <div className="grid h-screen px-4 place-content-center">
          <div className="text-center">
            <h1 className="font-black text-gray-200 text-9xl">404</h1>

            <p className="text-2xl font-bold tracking-tight  sm:text-4xl">
              Uh-oh!
            </p>

            <p className="mt-4 text-gray-500">We can't find that page.</p>

            <Link
              to="/change-password"
              className="inline-block px-5 py-3 mt-6 text-sm font-medium text-white bg-[#ff0000] rounded hover:bg-red-900 focus:outline-none focus:ring"
            >
              Go home
            </Link>
          </div>
        </div>
      )}
    </>
  );
};
