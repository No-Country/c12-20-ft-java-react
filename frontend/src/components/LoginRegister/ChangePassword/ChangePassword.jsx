import { useEffect, useState } from "react";
import { ButtonSign } from "../ButtonSign/ButtonSign";
import { InputPassword } from "../InputPassword/InputPassword";
export const ChangePassword = () => {
  const [showForm, setShowForm] = useState(false);
  useEffect(() => {
    validateToken();
  });
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
    }
  };
  return (
    <>
      {showForm ? (
        <form className="h-screen w-full flex justify-center items-center md:bg-[#fafafa]">
          <div className="bg-white flex flex-col gap-6 md:p-14 p-5 md:shadow-md rounded-xl">
            <h1 className="text-3xl font-medium">Create new password</h1>
            <p className="font-normal text-[#111111a8]">
              Your new password must be different from previous used passwords.
            </p>
            <InputPassword type="Password" />
            <InputPassword type="Password" text="Confirm" />
            <ButtonSign>Change password</ButtonSign>
          </div>
        </form>
      ) : (
        ""
      )}
    </>
  );
};
