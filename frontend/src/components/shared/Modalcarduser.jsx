const Modalcarduser = ({cliente,usercard,setusercardopen}) => {
    const {id, name,photo,email, plan, status} = cliente;
    const closeModal = () => {
        setusercardopen(false);
        };

  return (
    <>
    {usercard && (
      <div className="fixed inset-0 flex items-center justify-center z-20">
      <div className="fixed inset-0 bg-gray-800 opacity-50"></div>
      {/*Card body*/}
      <div className="rounded-md shadow shadow-md bg-[#2f2f2f]  ring grid place-items-center relative p-4 z-10">
        {/*Card Content*/}
            <button
              className="btn btn-sm btn-circle text-white absolute right-2 top-2"
              onClick={closeModal}
            ></button>

            <img src={photo} alt="la foto"/>
        </div>    
      </div>
    )}
    </>
  );
};

export default Modalcarduser;
