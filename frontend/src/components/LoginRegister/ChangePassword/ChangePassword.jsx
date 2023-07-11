import { ButtonSign } from "../ButtonSign/ButtonSign";
import { InputPassword } from "../InputPassword/InputPassword";
import { useParams } from "react-router-dom";
export const ChangePassword = () => {
  const { token } = useParams();
  console.log(token);
  const tokendesc = "askdaskdas";
  //validar ese token si es igual con el de localstorage
  return (
    <>
      {token === tokendesc ? (
        <div className="h-screen w-full flex justify-center items-center md:bg-[#fafafa]">
          <div className="bg-white flex flex-col gap-6 md:p-14 p-5 md:shadow-md rounded-xl">
            <h1 className="text-3xl font-medium">Create new password</h1>
            <p className="font-normal text-[#111111a8]">
              Your new password must be different from previous used passwords.
            </p>
            <InputPassword type="Password" />
            <InputPassword type="Password" text="Confirm" />
            <ButtonSign>Change password</ButtonSign>
          </div>
        </div>
      ) : (
        "ERROR 404"
      )}
    </>
  );
};
