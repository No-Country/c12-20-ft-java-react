import React from "react";
import { Fragment } from "react";
import { useState } from "react";
import usuariodefault from "../assets/usuariodefault.jpg";
import {
  RiBarChart2Line,
  RiSearch2Line,
  RiDeleteBin5Fill,
  RiSettings4Fill,
} from "react-icons/ri";
import Sidebar from "./shared/Sidebar";


const DashboardClass = () => {


    const [Events, setEvents] = useState([
        {
          activityId: 2,
          roomId: 2,
          capacity: 10,
          timeStart: "09:30",
          timeEnd: "17:50",
          Day: "Monday",
        },    
      ]);
      const [search, setSearch] = useState("");

      const handleSearchChange = (event) => {
        setSearch(event.target.value);
      };

      const Filtered = search
      ? Events.filter((Clasess) =>
          Clasess.name.toLowerCase().startsWith(search.toLowerCase())
        )
      : Events;
      

    
      return (
        <Fragment>
          <div className="flex min-h-screen">
            {/*Sider*/}
            <Sidebar />
            {/*Main*/}
            <div className="p-10 w-full flex flex-col h-full items-center">
              <div className="flex w-full justify-between items-center">
                <h1 className="text-blue-600 text-2xl">Clasess</h1>
                <div className="flex items-center gap-2">
                <input
                  type="text"
                  placeholder="Search"
                  className="input input-bordered w-24 md:w-auto hover:ring"
                  value={search}
                  onChange={handleSearchChange}
                />
                <RiSearch2Line className="text-2xl" />
              </div>
                </div>
              {/*Table*/}
              <div className="flex flex-col w-full mt-10">  
            </div>
          </div>
          </div>
        </Fragment>
      );
}
 
export default DashboardClass;