import React from 'react'
import logo from '../assets/img/logo1.png'
import logo_instagram from '../assets/img/logo_instagram.png'
import logo_facebook from '../assets/img/logo_facebook.png'
import logo_twitter from '../assets/img/logo_twitter.png'
import logo_youtube from '../assets/img/logo_youtube.png'
import { Link } from 'react-router-dom'




const NavBar = () => {

    return (
        <div className='navBar'>
            <Link to= {"/"}>
                <img className='logo' src={logo}/>
            </Link>
            <div  className='navBarTxt'>
                <a href="#scrollAboutUs">
                    <h3 className='navBarTittle'>About us</h3>
                </a>
                <a href="#scrollOurClasses">
                    <h3 className='navBarTittle'>Our clasess</h3>
                </a>
                <a href="#scrollContactUs">
                    <h3 className='navBarTittle'>Contact</h3>
                </a>
                
            </div>
            <div className='contIconNavBar'>
                <a href="https://www.instagram.com/" target="_blank">
                    <img className='iconNavBar' src={logo_instagram}/>
                </a>
                <a href="https://www.twitter.com/" target="_blank">
                    <img className='iconNavBar' src={logo_twitter}/>
                </a>
                <a href="https://www.youtube.com/" target="_blank">
                    <img className='iconNavBar' src={logo_youtube}/>
                </a>
                <a href="https://www.facebook.com/" target="_blank">
                    <img className='iconNavBar' src={logo_facebook}/>
                </a>
            </div>
            <div className="dropdown dropdown-bottom">
                <label tabIndex={0} className="btn m-1">MENU</label>
                    <ul tabIndex={0} className="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box w-52">
                        <a href="#scrollAboutUs">
                            <h3 className='navBarTittle'>About us</h3>
                        </a>
                        <a href="#scrollOurClasses">
                            <h3 className='navBarTittle'>Our clasess</h3>
                        </a>
                        <a href="#scrollContactUs">
                            <h3 className='navBarTittle'>Contact</h3>
                        </a>
                    </ul>
            </div>
        </div>
    )
}

export default NavBar