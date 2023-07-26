import { useEffect } from 'react';
import googleIcon from "../../../assets/google-icon.png";
import { useNavigate } from "react-router-dom";

export const GoogleButtonLogin = () => {
  //Redireccion a otra ruta
  const navigate = useNavigate();

  const googleLoginCallback = (res) => {
    console.log(
      `Se inició sesión correctamente. Aquí esta el token: ${res.credential}`
    );
    console.log(res);
    //Redirije a "/" (Landig, pero deberia ir a panel de usuario)
    navigate("/");
  };
  
  useEffect(() => {
    initializeGoogleId();
  }, []);

  const initializeGoogleId = () => {
    window.google.accounts.id.initialize({
      client_id:
        "974618840112-c3hq9f5chu3s6qbqisuvi5tgq7s646u3.apps.googleusercontent.com",
      ux_mode: "popup",
      callback: googleLoginCallback,
    });
  };

  const createFakeGoogleWrapper = () => {
    const googleLoginWrapper = document.createElement("div");
    googleLoginWrapper.style.display = "none";
    googleLoginWrapper.classList.add("custom-google-button");

    document.body.appendChild(googleLoginWrapper);

    window.google.accounts.id.renderButton(googleLoginWrapper, {
      type: "icon",
      width: "200",
    });

    const googleLoginWrapperButton =
      googleLoginWrapper.querySelector("div[role=button]");

    return {
      click: () => {
        googleLoginWrapperButton.click();
      },
    };
  };

  const handleGoogleLogin = (e) => {
    e.preventDefault();
    const googleButtonWrapper = createFakeGoogleWrapper();
    googleButtonWrapper.click();
  };

  return (
    <button
      className="flex items-center justify-center border w-full h-10 rounded-md gap-2 text-sm font-semibold text-[#111] hover:bg-gray-100"
      onClick={handleGoogleLogin}
    >
      <img className="w-5" src={googleIcon} alt="" />
      Sign in With Google
    </button>
  );
};
