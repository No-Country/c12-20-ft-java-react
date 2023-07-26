import React from "react";
import { Fragment } from "react";
import { useState } from "react";
import Modaladduser from "./shared/Modaladduser";
import Modalcarduser from "./shared/Modalcarduser";
import usuariodefault from "../assets/usuariodefault.jpg";
import {
  RiBarChart2Line,
  RiSearch2Line,
  RiDeleteBin5Fill,
  RiSettings4Fill,
} from "react-icons/ri";
import Sidebar from "./shared/Sidebar";
import DateTimePicker from "./shared/Timepicker";

const DashboardAdmins = () => {
  const [Admins, setAdmins] = useState([
    {
      id: "1",
      photo: usuariodefault,
      email: "email1@gmail.com",
      name: "Luca",
      pasword: "123456",
      Schelude: "8:00-12:00",
      Job: "Crossfit",
      status: "true",
    },
  ]);

  const [activeRow, setActiveRow] = useState(null);
  const [Alertdel, setAlertdel] = useState(true);
  const [Usercardopen, setusercardopen] = useState(null);
  const [search, setSearch] = useState("");
  {
    /*open and close events*/
  }

  const handlecardopen = (rowId) => {
    if (activeRow === rowId) {
      setusercardopen(rowId);
    } else {
      setusercardopen(null);
    }
  };

  const handleSearchChange = (event) => {
    setSearch(event.target.value);
  };

  const handleRowClick = (rowId) => {
    setActiveRow(rowId);
  };

  const ConfirmDeleteuser = () => {
    setAlertdel(false);
  };

  const DenyDeleteuser = () => {
    setAlertdel(true);
  };

  {
    /*delete user*/
  }
  const Deleteuser = (id) => {
    const updatedAdmins = Admins.filter((Admin) => Admin.id !== id);
    setAdmins(updatedClients);
    setAlertdel(true);
  };

  {
    /*search user*/
  }
  const filteredAdmins = search
    ? Admins.filter((client) =>
        client.name.toLowerCase().startsWith(search.toLowerCase())
      )
    : Admins;

  return (
    <Fragment>
      <div className="flex min-h-screen">
        {/*Sider*/}
        <Sidebar />

        {/*Main*/}
        <div className="p-10 w-full flex flex-col h-full items-center">
          {/*Cards*/}

          {/*Table title*/}
          <div className="flex items-center justify-between w-4/5 mt-10">
            <h1 className="text-2xl font-bold text-blue-600">
              Admin Magnament
            </h1>
            <div className="form-control">
              <div className="flex items-center gap-2">
                <input
                  type="text"
                  placeholder="Search"
                  className="input input-bordered w-24 md:w-auto"
                  value={search}
                  onChange={handleSearchChange}
                />
                <RiSearch2Line className="text-2xl" />
              </div>
            </div>
          </div>

          {/*Table*/}

          <div className="w-4/5 h-64 overflow-y-auto mt-10">
            <table className="table table-xs ">
              {/* head */}
              <thead>
                <tr className="text-white text-center border-bottom border-gray-600">
         
                  <th>Full Name</th>
                  <th>Email</th>
                  <th>Schedule</th>
                  <th>Active</th>
                  <th></th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {filteredAdmins.map((Admin) => (
                  <React.Fragment key={Admin.id}>
                    {search === "" && Admin.length === 0 && (
                      <tr className="text-center">
                        <td colSpan="7">No Users found</td>
                      </tr>
                    )}
                    <tr
                      className={`text-center border-top border-gray-600 ${
                        activeRow === Admin.id
                          ? "bg-gray-900 transition-colors duration-250 ease-linear"
                          : ""
                      }`}
                      onClick={() => handleRowClick(Admin.id)}
                      onDoubleClick={() => handlecardopen(Admin.id)}
                    >
                      <td>
                        <div className="flex items-center space-x-3 justify-center text-start">
                          <div className="avatar">
                            <div className="mask mask-squircle w-12 h-12">
                              <img
                                src={Admin.photo}
                                alt="Avatar Tailwind CSS Component"
                              />
                            </div>
                          </div>
                          <div>
                            <div className="font-bold">{Admin.name}</div>
                            <div className="text-sm opacity-50">
                              {Admin.Job}
                            </div>
                          </div>
                        </div>
                      </td>
                      <td>{Admin.email}</td>
                      <td>{Admin.Schelude}</td>
                      <td>
                        <span
                          className={`badge ${
                            Admin.status == "true"
                              ? "badge-success"
                              : Admin.status == "false"
                              ? "badge-error"
                              : "badge-warning"
                          } `}
                        ></span>
                      </td>
                      {Alertdel ? (
                        <>
                          <td>
                            <button onClick={() => ConfirmDeleteuser(Admin.id)}>
                              <RiDeleteBin5Fill className="text-lg hover:text-red-600" />
                            </button>
                          </td>
                          <td>
                            <button>
                              <RiSettings4Fill className="text-lg hover:text-blue-600" />
                            </button>
                          </td>
                        </>
                      ) : (
                        <>
                          <td>
                            <button
                              className="btn btn-primary btn-sm"
                              onClick={() => Deleteuser(Admin.id)}
                            >
                              Confirm
                            </button>
                          </td>
                          <td>
                            <button
                              className="btn btn-sm text-blue-600"
                              onClick={DenyDeleteuser}
                            >
                              Deny
                            </button>
                          </td>
                        </>
                      )}
                    </tr>
                  </React.Fragment>
                ))}
              </tbody>
            </table>
            <div className=" flex justify-end mt-5">
              {/*<Modaladduser clientes={clients} addclientes={setClient} />*/}
            </div>
          </div>
        </div>
      </div>
    </Fragment>
  );
};

export default DashboardAdmins;
