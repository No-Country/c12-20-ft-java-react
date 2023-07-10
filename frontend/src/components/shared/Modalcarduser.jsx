const Modalcarduser = ({ cliente, usercard, setusercardopen }) => {

  const { id, name, photo, email, plan, status } = cliente;
  const closeModal = () => {
    setusercardopen(false);
  };

  return (
    <>
      {usercard === id && (
        <div className="fixed inset-0 flex items-center justify-center z-20">
          <div className="fixed inset-0 bg-gray-800 opacity-50"></div>
          {/*Card body*/}
          <div className="rounded-md shadow shadow-md bg-[#2f2f2f]  ring grid place-content-center place-items-center relative max-w-2xl p-4 z-10">
            {/*Card Content*/}
            <button
              className="btn btn-sm btn-circle absolute right-2 top-2"
              onClick={closeModal}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-6 w-6 text-white"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M6 18L18 6M6 6l12 12"
                />
              </svg>
            </button>
            <div className="w-60 mask mask-hexagon my-5">
              <img src={photo} alt="la foto" />
            </div>
            <h1 className="text-2xl font-bold text-white self-start">{name}</h1>
            <div className="flex flex-col items-start justify-start">
              <div className="divider"></div>
              <p className="text-sm text-gray-400">{email}</p>
              <p className="text-sm text-gray-400">{plan}</p>
              <p className="text-sm text-gray-400">{status}</p>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Modalcarduser;
