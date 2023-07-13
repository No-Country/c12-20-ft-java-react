import React, { useState } from "react";
import { format, isBefore, startOfDay } from "date-fns";

const DateTimePicker = () => {
  const [selectedDateTime, setSelectedDateTime] = useState("");

  const handleDateTimeChange = (event) => {
    const selectedDate = new Date(event.target.value);
    const currentDate = startOfDay(new Date());

    if (isBefore(selectedDate, currentDate)) {
      setSelectedDateTime("");
      return;
    }

    setSelectedDateTime(selectedDate);
  };

  return (
    <div className="flex items-center">
      <input
        type="datetime-local"
        value={selectedDateTime ? format(selectedDateTime, "yyyy-MM-dd'T'HH:mm") : ""}
        onChange={handleDateTimeChange}
        className="appearance-none block w-48 px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
      />
    </div>
  );
};

export default DateTimePicker;
