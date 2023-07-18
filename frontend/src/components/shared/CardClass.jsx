import React, { useState } from 'react';

const CardClass = ({ Clasess }) => {
  const { activityId, roomId, capacity, timeStart, timeEnd, Day, Photo, Name } = Clasess;
  const [isClicked, setIsClicked] = useState(false);

  const handleClick = () => {
    setIsClicked(!isClicked);
  };

  return (
    <>
    <div className="card w-5/6 bg-base-100 shadow-2xl  ease-in duration-300 delay-150 hover:ring ">
      <figure className="max-h-56">
    
        <img
          src={Photo}
          alt="image"
        />
      </figure>
      <div className="card-body">
        <h2 className="card-title">
          {Name}
        </h2>
        <p>{Day}</p>
        <p>{capacity}</p>
        <div className="card-actions justify-end">
          <div className="badge badge-outline truncate">{timeEnd}-{timeStart}</div>
        </div>
      </div>
    </div>
  </>
  );
};

export default CardClass;
