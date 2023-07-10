export const HeaderForm = ({ title, parraf }) => {
  return (
    <>
      <h1 className="text-3xl font-medium">{title}</h1>
      <p className="font-normal text-[#111111a8]">{parraf}</p>
    </>
  );
};