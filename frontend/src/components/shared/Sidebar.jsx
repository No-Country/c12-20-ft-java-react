import logo from "../../assets/Logo.png";
import {
  RiAdminFill,
  RiWalkFill,
  RiRidingFill,
  RiMoneyDollarCircleFill,
} from "react-icons/ri";

const Sidebar = () => {
  return (
    <>
      <aside className="hidden flex-col items-center static gap-8 min-h-full w-80 p-4 bg-[#2F2F2F] md:flex">
        <section>
          {/*Logo*/}
          <div className="flex items-center gap-4 mb-12">
            <img src={logo} alt="Logo" className=" w-10 h-10" />
            <div>
              <h1 className="text-3xl font-bold text-white">Prime Fit</h1>
            </div>
          </div>

          {/*user*/}
          <div className="flex  items-center gap-4 mb-8">
            <div>
              <h1 className="text-xl font-bold text-white">Luca victorino</h1>
              <button className="btn  btn-xs btn-outline">log out</button>
            </div>

            <div className="avatar  hover:cursor-pointer online">
              <div className="w-12  hover:ring rounded-full">
                <img
                  src="https://i.pinimg.com/564x/e7/c1/f6/e7c1f68fdabfa03c2414735a6a4bedc5.jpg"
                  className="object-contain"
                />
              </div>
            </div>
          </div>
          {/*nav*/}
          <div className="">
            <ul className="flex flex-col gap-2">
              <li>
                <a
                  href="/c12-20-ft-java-react/DasboardUsers"
                  className="flex items-center gap-4 p-2 hover:bg-gray-600 focus:bg-gray-600 transition-colors rounded-lg"
                >
                  <RiWalkFill className="w-5 " />
                  <span className="text-white">Users</span>
                </a>
              </li>
              <li>
                <a
                  href="/c12-20-ft-java-react/DashboardAdmins"
                  className="flex items-center gap-4 p-2 hover:bg-gray-600 focus:bg-gray-600 transition-colors rounded-lg"
                >
                  <RiAdminFill className="w-5 " />
                  <span className="text-white">Administrators</span>
                </a>
              </li>
              <li>
                <a
                  href="/c12-20-ft-java-react/Classes"
                  className="flex items-center gap-4 p-2 hover:bg-gray-600 focus:bg-gray-600 transition-colors rounded-lg"
                >
                  <RiRidingFill className="w-5 " />
                  <span className="text-white">Classes</span>
                </a>
              </li>
            </ul>
          </div>
        </section>
      </aside>
    </>
  );
};

export default Sidebar;
