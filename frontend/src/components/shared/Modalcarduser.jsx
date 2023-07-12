import React from "react";
import { RiCloseCircleLine,RiRedPacketFill,RiMailFill } from "react-icons/ri";

const Modalcarduser = ({ cliente, usercard, setusercardopen }) => {
  const { id, name, photo, email, plan, status } = cliente;
  const closeModal = () => {
    setusercardopen(false);
  };

  return (
    <>
      {usercard === id && (
        <div className="fixed inset-0 flex items-center justify-center z-20">
          <div className="fixed inset-0 bg-gray-800 opacity-50" onClick={closeModal}></div>
          {/*Card body*/}
          <div className="rounded-md shadow shadow-md bg-[#2f2f2f] min-w-3xl  grid place-content-center place-items-center relative max-w-3xl p-2  z-10">
            {/*Card Content*/}
            <button
              className="btn btn-sm bg-gray-800 btn-circle absolute right-2 top-2"
              onClick={closeModal}
            >
              <RiCloseCircleLine className="text-3xl" />
            </button>
            <div className="bg-[#2f2f2f] flex flex-col w-64 mx-auto rounded-lg shadow-xl">
              <div className="min-w-3xl">
                <img
                  src={photo}
                  alt="Usuario"
                  className="w-60 rounded-2xl mx-auto my-2 object-cover"
                />
              </div>
              <div className="bg-gray-900 mx-4 rounded-lg flex  justify-between items-center gap-10 -mt-8 z-10 shadow-xl py-2 px-6">
                <h3 className="text-white text-lg font-semibold">{name}</h3>
                <div className="flex gap-2">
                        <h1 className="text-white text-sm">{status== "true" ? "Pagado" : "Debe"}</h1>
                <span 
                          
                          className={`badge badge-sm ring-1 ${
                            status == "true"
                              ? "badge-success"
                              :status == "false"
                              ? "badge-error"
                              : "badge-warning"
                          } `}
                        ></span>
                        </div>
              </div>
              <div className="px-6 py-4 flex flex-col gap-2">
               
                <div className="flex items-center mt-4 ">
                   <RiRedPacketFill className="text-white text-2xl"/>
                  <h1 className="px-2 text-sm text-white">{plan}</h1>
                </div>
                <div className="flex items-center mt-4 ">
                  <RiMailFill className="text-white text-2xl"/>
                  <h1 className="px-2 text-white text-sm">{email}</h1>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Modalcarduser;
