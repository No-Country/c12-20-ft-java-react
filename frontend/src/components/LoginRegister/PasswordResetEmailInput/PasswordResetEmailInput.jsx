import { ButtonSign } from "../ButtonSign/ButtonSign";
import { HeaderForm } from "../HeaderForm/HeaderForm";
import { InputText } from "../InputText/InputText";
import { useContext } from "react";
import { AuthContext } from "../../Context/AuthContext";

export const PasswordResetEmailInput = () => {
  const { emailValue } = useContext(AuthContext);
  const validateChangePassword = async (e) => {
    e.preventDefault();

    try {
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
        console.log("email enviao.");
        console.log(response);
      } else {
        const errorResponse = await response.json();
        console.log("Error en la solicitud:", errorResponse);
      }
    } catch (error) {
      console.log("Error en la solicitud:", error);
    }
  };

  return (
    <>
      <form
        onSubmit={validateChangePassword}
        className="flex justify-center h-screen items-center md:bg-[#fafafa]"
      >
        <div className="bg-white flex flex-col gap-5 p-16 max-md:p-8 rounded-xl md:shadow-md max-w-2xl">
          <HeaderForm
            title="Reset password"
            parraf="Enter the email associated with your account and we'll send an email
            with instructions to reset your password."
          />
          {/* Paso 4: Vincular el estado userEmail con el componente InputText */}
          <InputText type="Email" />
          <ButtonSign>Send instructions</ButtonSign>
        </div>
      </form>
    </>
  );
};
