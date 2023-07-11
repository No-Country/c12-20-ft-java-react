import { ButtonSign } from "../ButtonSign/ButtonSign";
import { HeaderForm } from "../HeaderForm/HeaderForm";
import { InputText } from "../InputText/InputText";
export const PasswordResetEmailInput = () => {
  return (
    <>
      <div className="flex justify-center h-screen items-center md:bg-[#fafafa]">
        <div className="bg-white flex flex-col gap-5 p-16 max-md:p-8 rounded-xl md:shadow-md max-w-2xl">
          <HeaderForm
            title="Reset password"
            parraf="Enter the email associated with your account and we'll send an email
            with instructions to reset your password."
          />
          <InputText />
          <ButtonSign>Send instructions</ButtonSign>
        </div>
      </div>
    </>
  );
};
