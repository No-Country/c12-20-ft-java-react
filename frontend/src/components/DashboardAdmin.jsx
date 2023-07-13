import React from "react";
import { Fragment } from "react";
import { useState } from "react";
import logo from "../assets/Logo.png";
import {
  RiAdminFill,
  RiWalkFill,
  RiRidingFill,
  RiMoneyDollarCircleFill,
  RiBarChart2Line,
  RiSearch2Line,
} from "react-icons/ri";
import Sidebar from "./shared/Sidebar";
const DasboardAdmin = () => {
  const [activeRow, setActiveRow] = useState(null);

  const handleRowClick = (rowId) => {
    setActiveRow(rowId);
  };

  const [clients, setClient] = useState([
    {
      id: 1,
      name: "Luca Victorino",
      plan: "mensual",
      date: "12/12/12",
      status: "false",
    },
    {
      id: 2,
      name: "Luca Victorino",
      plan: "Developer",
      date: "123/12/12",
      status: "true",
    },
    {
      id: 3,
      name: "Luca Victorino",
      plan: "Developer",
      date: "123/12/12",
      status: "near",
    },
  ]);
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
                167
              </span>
            </div>
            <div className=" h-40  flex flex-col justify-between rounded bg-[#2F2F2F] shadow-xl p-5">
              <div className="flex gap-2 text-xl items-center">
                <RiBarChart2Line className="text-blue-600 ring-1" />
                <span className="text-white">Transactions</span>
              </div>
              <span className="text-bold text-white text-2xl justify-self-end">
                167
              </span>
            </div>
            <div className=" h-40 flex flex-col justify-between rounded bg-[#2F2F2F] shadow-xl p-5">
              <div className="flex gap-2 text-xl items-center">
                <RiBarChart2Line className="text-blue-600 ring-1" />
                <span className="text-white">Active users</span>
              </div>
              <span className="text-bold text-white text-2xl justify-self-end">
                167
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
                />
                <RiSearch2Line className="text-2xl" />
              </div>
            </div>
          </div>

          {/*Table*/}
          <div className="w-4/5 h-64 overflow-y-auto mt-10">
            <table className="table table-xs table-pin-rows table-pin-cols">
              {/* head */}
              <thead>
                <tr className="text-white text-center border-bottom border-gray-600">
                  <th></th>
                  <th> Full Name</th>
                  <th>Training plan</th>
                  <th>Last payment</th>
                  <th>Payment status</th>
                </tr>
              </thead>
              <tbody>
                {clients.map((client) => (
                  <tr
                    key={client.id}
                    className={`text-center border-top border-gray-600 ${
                      activeRow === client.id
                        ? "bg-gray-900 transition-colors duration-250 ease-linear"
                        : ""
                    }`}
                    onClick={() => handleRowClick(client.id)}
                  >
                    <td>{client.id}</td>
                    <td>{client.name}</td>
                    <td>{client.plan}</td>
                    <td>{client.date}</td>
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
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </Fragment>
  );
};

export default DasboardAdmin;
