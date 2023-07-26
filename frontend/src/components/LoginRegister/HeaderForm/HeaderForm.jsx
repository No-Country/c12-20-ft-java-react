import React from "react"
export const HeaderForm = ({ title, parraf }) => {
  return (
    <div>
      <h1 className="text-3xl font-medium text-black">{title}</h1>
      <p className="font-normal text-[#111111a8]">{parraf}</p>
    </div>
  );
};
