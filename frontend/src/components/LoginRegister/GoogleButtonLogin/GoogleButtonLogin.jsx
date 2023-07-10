import googleIcon from "../../../assets/google-icon.png";
import jwt_decode from "jwt-decode";
export const GoogleButtonLogin = () => {
  const googleLoginCallback = (res) => {
    // console.log(
    //   `Se inició sesión correctamente. Aquí esta el token: ${res.credential}`
    // );
    // const userObject = jwt_decode(res.credential);
    // console.log(userObject);
    console.log("iniciao con google");
  };

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

  const handleGoogleLogin = () => {
    const googleButtonWrapper = createFakeGoogleWrapper();
    googleButtonWrapper.click();
  };

  initializeGoogleId();

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
