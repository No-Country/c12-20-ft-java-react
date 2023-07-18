import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import NavBar from './components/NavBar'
import LandingPage from './components/LandingPage'
import ContactUs from './components/ContactUs'
import Footer from './components/Footer'




const App = () => {
  return (
    
    <BrowserRouter>
    <NavBar/>
    <LandingPage/>
    <ContactUs/>
    <Footer/>
    </BrowserRouter>
    
  )
}
export default App;
