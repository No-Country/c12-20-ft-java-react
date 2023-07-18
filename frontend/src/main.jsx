import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import DasboardUsers from './components/DasboardUsers.jsx'
import DashboardClass from './components/DashboardClass.jsx'
import DashboardAdmins from './components/DashboardAdmins.jsx'

const router = createBrowserRouter([
  {
    path:'/c12-20-ft-java-react/',
    element: <App/>,
    errorElement: <h1>error</h1>,
  },
  {
    path:'/c12-20-ft-java-react/DashboardAdmins',
    element: <DashboardAdmins  />,
    errorElement: <h1>error</h1>,
  },

  {
    path: '/c12-20-ft-java-react/DasboardUsers',
    element: <DasboardUsers />,
  },
  {
    path: '/c12-20-ft-java-react/Classes',
    element: <DashboardClass />,
  },
])
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
     <RouterProvider router={router}/>
  </React.StrictMode>,
)
