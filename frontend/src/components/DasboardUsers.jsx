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

const DasboardUsers = () => {
  const [clients, setClient] = useState([
    {
      id: "1",
      photo: usuariodefault,
      email: "email1@gmail.com",
      name: "Luca",
      plan: "Crossfit",
      status: "true",
    },
    {
      id: "2",
      photo: usuariodefault,
      email: "email2@gmail.co,",
      name: "Victorino",
      plan: "Crossfit",
      status: "false",
    },

    {
      id: "3",
      photo: usuariodefault,
      email: "email3@gmail.com",
      name: "lUCHO",
      plan: "Full plan",
      status: "true",
    },
  ]);

  const trueStatusClients = clients.filter(
    (client) => client.status === "true"
  );
  const falseStatusClients = clients.filter(
    (client) => client.status === "false"
  );
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
    const updatedClients = clients.filter((client) => client.id !== id);
    setClient(updatedClients);
    setAlertdel(true);
  };

  {
    /*search user*/
  }
  const filteredClients = search
    ? clients.filter((client) =>
        client.name.toLowerCase().startsWith(search.toLowerCase())
      )
    : clients;

  return (
    <Fragment>
      <div className="flex min-h-screen">
        {/*Sider*/}
        <Sidebar />

        {/*Main*/}
        <div className="p-10 w-full flex flex-col h-full items-center">
          {/*Cards*/}
          <div className="flex  flex-wrap justify-around w-4/5  gap-12 my-8">
            <div className="w-44 h-40 flex flex-col justify-between rounded bg-[#2F2F2F] shadow-xl p-5">
              <div className="flex gap-2 text-xl items-center">
                <RiBarChart2Line className="text-blue-600 ring-1" />
                <span className="text-white">Users</span>
              </div>
              <span className="text-bold text-white text-2xl justify-self-end">
                {clients.length}
              </span>
            </div>
            <div className=" h-40  flex flex-col justify-between rounded bg-[#2F2F2F] shadow-xl p-5">
              <div className="flex gap-2 text-xl items-center">
                <RiBarChart2Line className="text-blue-600 ring-1" />
                <span className="text-white">Inactive Users</span>
              </div>
              <span className="text-bold text-white text-2xl justify-self-end">
                {falseStatusClients.length}
              </span>
            </div>
            <div className=" h-40 flex flex-col justify-between rounded bg-[#2F2F2F] shadow-xl p-5">
              <div className="flex gap-2 text-xl items-center">
                <RiBarChart2Line className="text-blue-600 ring-1" />
                <span className="text-white">Active users</span>
              </div>
              <span className="text-bold text-white text-2xl justify-self-end">
                {trueStatusClients.length}
              </span>
            </div>
          </div>

          {/*Table title*/}
          <div className="flex items-center justify-between w-4/5 mt-10">
            <h1 className="text-2xl font-bold text-blue-600">
              Users Magnament
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
                  <th>Id</th>
                  <th>Full Name</th>
                  <th>Email</th>
                  <th>Training plan</th>
                  <th>Payment status</th>
                  <th></th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {filteredClients.map((client) => (
                  <React.Fragment key={client.id}>
                    {search === "" && clients.length === 0 && (
                      <tr className="text-center">
                        <td colSpan="7">No clients found</td>
                      </tr>
                    )}
                    <tr
                      className={`text-center border-top border-gray-600 ${
                        activeRow === client.id
                          ? "bg-gray-900 transition-colors duration-250 ease-linear"
                          : ""
                      }`}
                      onClick={() => handleRowClick(client.id)}
                      onDoubleClick={() => handlecardopen(client.id)}
                    >
                      <td>{client.id}</td>
                      <td>{client.name}</td>
                      <td>{client.email}</td>
                      <td>{client.plan}</td>
                      <td>
                        <span
                          className={`badge ${
                            client.status == "true"
                              ? "badge-success"
                              : client.status == "false"
                              ? "badge-error"
                              : "badge-warning"
                          } `}
                        ></span>
                      </td>
                      {Alertdel ? (
                        <>
                          <td>
                            <button
                              onClick={() => ConfirmDeleteuser(client.id)}
                            >
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
                              onClick={() => Deleteuser(client.id)}
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

                    <Modalcarduser
                      key={client.id}
                      cliente={client}
                      usercard={Usercardopen}
                      setusercardopen={setusercardopen}
                    />
                  </React.Fragment>
                ))}
              </tbody>
            </table>
            <div className=" flex justify-end mt-5">
              <Modaladduser clientes={clients} addclientes={setClient} />
            </div>
          </div>
        </div>
      </div>
    </Fragment>
  );
};

export default DasboardUsers;
