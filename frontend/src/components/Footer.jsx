import React from 'react'
import logo from '../assets/img/logo1.png'
import logo_instagram from '../assets/img/logo_instagram.png'
import logo_facebook from '../assets/img/logo_facebook.png'
import logo_twitter from '../assets/img/logo_twitter.png'
import logo_youtube from '../assets/img/logo_youtube.png'
import { Link } from 'react-router-dom'

const Footer = () => {
    return (
        <div className='contFooter'>
            <Link to= {"/"}>
                <img className='logo' src={logo}/>
            </Link>
            <div  className='footerTxt'>
                <a>
                    <h3 className='footerTittle'>Copyright © 2023 - All right reserved</h3>
                </a>
            </div>
            <div className='contIconFooter'>
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
        </div>
    )
}

export default Footer