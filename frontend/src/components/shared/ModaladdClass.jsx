import React, { useState } from "react";
import Success from "./Alert";
import usuariodefault from "../../assets/usuariodefault.jpg";
import TimePicker from "react-time-picker";

const ModalAddClass = () => {
  const [modalOpen, setModalOpen] = useState(false);

  const [selectedFile, setSelectedFile] = useState(usuariodefault);

  const [success, setSuccess] = useState(false);

  const [selectedTime, setSelectedTime] = useState("10:00");

  const handleTimeChange = () => {
    setSelectedTime(SelectedTime);
    setFormData({ ...formData, Schelude: SelectedTime });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (
      formData.activityId != "" &&
      formData.roomId != "" &&
      formData.Name != "" &&
      formData.capacity != "" &&
      formData.timeStart != "" &&
      formData.timeEnd != "" &&
      formData.Day != "" &&
      formData.photo != ""
    ) {
      setAdmins([...Admins, formData]);
      setSuccess(true);
      setTimeout(() => {
        setSuccess(false);
      }, 3000);
    }
    setFormData({
        Name: "",
        activityId: "",
        roomId: "",
        Photo: "",
        capacity: "",
        timeStart: "",
        timeEnd: "",
        Day: "",
    });
    setSelectedFile(usuariodefault);
    setModalOpen(false);
  };

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const [formData, setFormData] = useState({
    Name: "",
    activityId: "",
    roomId: "",
    Photo: "",
    capacity: "",
    timeStart: "",
    timeEnd: "",
    Day: "",
  });

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFileChange = (event) => {
    if (event.target.files[0].type.includes("image/")) {
      const file = event.target.files[0];
      setSelectedFile(URL.createObjectURL(file));
      setFormData({ ...formData, photo: URL.createObjectURL(file) });
    } else {
      alert("El archivo seleccionado no es una imagen");
    }
  };
  return (
    <>
      {success && <Success />}
      <button
        className="btn btn-outline btn-sm text-blue-600"
        onClick={openModal}
      >
        Add Class
      </button>
      {modalOpen && (
        <div className="fixed inset-0 flex items-center justify-center z-20">
          <div className="fixed inset-0 bg-gray-800 opacity-50"></div>
          {/* Modal */}
          <div className="rounded-md shadow shadow-md bg-[#2f2f2f]  ring grid place-items-center relative p-4 z-10">
            <button
              className="btn btn-sm btn-circle  btn-ghost absolute right-2 top-2"
              onClick={closeModal}
            >
              ✕
            </button>
            <span className="text-2xl text-bold">New Admin</span>
            <div className="divider  w-full"></div>
            {/* FormAvatar */}
            <div className="avatar relative">
              <div className="w-24 rounded-xl ring my-4">
                <img src={selectedFile} />
              </div>
            </div>
            {/* Form */}
            <form
              onSubmit={handleSubmit}
              id="Admin"
              method="dialog"
              className="w-full p-4 "
            >
              <div className="flex gap-2">
                <input
                  className="w-full px-3 hover:ring py-2 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500"
                  type="text"
                  name="name"
                  placeholder="Enter name"
                  onChange={handleInputChange}
                  required
                />

                <input
                  className="w-full px-3 py-2 hover:ring border border-white rounded-md focus:outline-none focus:border-blue-500"
                  placeholder="Enter User id"
                  name="id"
                  onChange={handleInputChange}
                  required
                />
              </div>
              <div className="mt-4 flex gap-2">
                <input
                  className="w-full px-3 mt-4 py-2 border hover:ring border-white rounded-md focus:outline-none focus:border-blue-500"
                  placeholder="Enter User Email"
                  type="email"
                  name="email"
                  onChange={handleInputChange}
                  required
                />

                <input
                  className="w-full px-3 mt-4 py-2 border hover:ring border-white rounded-md focus:outline-none focus:border-blue-500"
                  placeholder="Enter User Password"
                  type="password"
                  name="pasword"
                  onChange={handleInputChange}
                  required
                />
              </div>
              <div className="mt-4 flex gap-2">
                <div className="flex items-center">

                  <TimePicker
                    value={selectedTime}
                    onChange={handleTimeChange}
                    clearIcon={null}
                    className={"w-full"}
                  />
                </div>

                <select
                  className="select hover:Ring border border-blue-600 text-white w-full"
                  name="Job"
                  onChange={handleInputChange}
                  required
                >
                  <option selected disabled>
                    Job
                  </option>
                  <option>Personal Trainer</option>
                  <option>Recepcionist</option>
                  <option>Admin</option>
                  <option>Gym Trainer</option>
                </select>
              </div>
              <input
                type="file"
                className="file-input w-full text-blue-600 file-input-smmax-w-xs mt-4"
                onChange={handleFileChange}
              />
              <div className="flex justify-center">
                <button
                  className="bg-blue-500 hover:bg-blue-600 text-white font-bold mt-4 py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                  type="submit"
                  form="Admin"
                >
                  Register
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  );
};

export default ModalAddClass;
