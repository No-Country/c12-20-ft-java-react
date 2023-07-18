import React from "react";
import { Fragment } from "react";
import { useState } from "react";
import {
  RiSearch2Line,
} from "react-icons/ri";
import Sidebar from "./shared/Sidebar";
import CardClass from "./shared/CardClass";
import ModalAddClass from "./shared/ModaladdClass";


const DashboardClass = () => {


    const [Events, setEvents] = useState([
        {
          Name: "Cycling",
          activityId: 2,
          roomId: 2,
          Photo: "https://images.theconversation.com/files/317906/original/file-20200301-166496-1qxkfiq.jpg?ixlib=rb-1.1.0&rect=0%2C0%2C3594%2C2382&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
          capacity: 10,
          timeStart: "09:30",
          timeEnd: "17:50",
          Day: "Monday",
        },    
        {
          Name: "Cycling",
          activityId: 2,
          roomId: 2,
          Photo: "https://images.theconversation.com/files/317906/original/file-20200301-166496-1qxkfiq.jpg?ixlib=rb-1.1.0&rect=0%2C0%2C3594%2C2382&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
          capacity: 10,
          timeStart: "09:30",
          timeEnd: "17:50",
          Day: "Monday",
        },    
        {
          Name: "Cycling",
          activityId: 2,
          roomId: 2,
          Photo: "https://images.theconversation.com/files/317906/original/file-20200301-166496-1qxkfiq.jpg?ixlib=rb-1.1.0&rect=0%2C0%2C3594%2C2382&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
          capacity: 10,
          timeStart: "09:30",
          timeEnd: "17:50",
          Day: "Monday",
        },    
        {
          Name: "Cacling",
          activityId: 2,
          roomId: 2,
          Photo: "https://images.theconversation.com/files/317906/original/file-20200301-166496-1qxkfiq.jpg?ixlib=rb-1.1.0&rect=0%2C0%2C3594%2C2382&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
          capacity: 10,
          timeStart: "09:30",
          timeEnd: "17:50",
          Day: "Monday",
        },  
        {
          Name: "Cacling",
          activityId: 2,
          roomId: 2,
          Photo: "https://images.theconversation.com/files/317906/original/file-20200301-166496-1qxkfiq.jpg?ixlib=rb-1.1.0&rect=0%2C0%2C3594%2C2382&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
          capacity: 10,
          timeStart: "09:30",
          timeEnd: "17:50",
          Day: "Monday",
        },  
        
        {
          Name: "Cacling",
          activityId: 2,
          roomId: 2,
          Photo: "https://images.theconversation.com/files/317906/original/file-20200301-166496-1qxkfiq.jpg?ixlib=rb-1.1.0&rect=0%2C0%2C3594%2C2382&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
          capacity: 10,
          timeStart: "09:30",
          timeEnd: "17:50",
          Day: "Monday",
        },  


        {
          Name: "Cacling",
          activityId: 2,
          roomId: 2,
          Photo: "https://images.theconversation.com/files/317906/original/file-20200301-166496-1qxkfiq.jpg?ixlib=rb-1.1.0&rect=0%2C0%2C3594%2C2382&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
          capacity: 10,
          timeStart: "09:30",
          timeEnd: "17:50",
          Day: "Monday",
        },  
        {
          Name: "Cacling",
          activityId: 2,
          roomId: 2,
          Photo: "https://images.theconversation.com/files/317906/original/file-20200301-166496-1qxkfiq.jpg?ixlib=rb-1.1.0&rect=0%2C0%2C3594%2C2382&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
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

      const Deleteuser = (id) => {
        const updatedAdmins = Admins.filter((Admin) => Admin.id !== id);
        setAdmins(updatedAdmins);
        setAlertdel(true);
      };


      const Filtered = search
      ? Events.filter((Clasess) =>
          Clasess.Name.toLowerCase().startsWith(search.toLowerCase())
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
                <h1 className="text-2xl font-bold text-blue-600">Clasess</h1>
                <div className="flex items-center gap-2">
                  <ModalAddClass addClass={setEvents} />
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
              <div className="flex flex-col   w-full mt-5">  
              <div className="grid grid-cols-1 overflow-y-auto max-h-[501px] sm:grid-cols-2 md:grid-cols-3 gap-4 p-10 place-items-center my-5">

        
              {Filtered.map ((Clasess) => (
                 <React.Fragment key={Clasess.activityId}>
                  <div className="my-2  w-full flex justify-center">
                  <CardClass Clasess={Clasess} />
                  </div>
                 </React.Fragment>
                 ))} 
                 </div>
            </div>
          </div>
          </div>
        </Fragment>
      );
}
 
export default DashboardClass;