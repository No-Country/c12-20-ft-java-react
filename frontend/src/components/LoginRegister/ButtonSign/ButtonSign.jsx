import React from "react";

export const ButtonSign = ({ children }) => {
  return (
    <button className="w-full bg-[#FF0000] text-white text-sm h-10 rounded-md hover:bg-red-900">
      {children}
    </button>
  );
};
