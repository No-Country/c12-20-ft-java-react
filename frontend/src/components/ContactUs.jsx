import React from 'react'
import { useRef } from 'react'

const ContactUs = () => {

    const scrollContactUs = useRef(null);

    const handleScroll = () => {
        scrollContactUs.current.scrollIntoView({ behavior: 'smooth'});

    };


    return (
        <section ref={scrollContactUs} id="scrollContactUs" className='contContactUs'>
            <div className='contactUsTxt'>
                <h2 className='ttQuestions'>Have questions about our gym?</h2>
                <p className='txtQuestions'>We're here to help! Fill out the contact form below, and our friendly staff will get back to you with all the answers you need. Whether you're curious about our facilities, membership options, or fitness programs, we're happy to provide you with the information you're looking for.</p>
            </div>
            <div className='contactUsForm'>
                <h2 className='ttContactUs'>Contact Us</h2>
                <h3 className='ttFillOut'>Fill out the form below to ask any question!</h3>
                <form>
                    <div className='inputName'>
                        <h4 className='txtlabel'>Enter Your Full Name Here:</h4>
                        <input type="text" placeholder="Your Full Name Here" className="input input-bordered w-full max-w-xs" />
                    </div>
                    <div className='inputEmail'>
                        <h4 className='txtlabel'>Enter Your Email Here:</h4>
                        <input  type="text" placeholder="Your Email Here" className="input input-bordered w-full max-w-xs" />
                    </div>
                    <div className='txtArea'>
                        <h4 className='txtlabel'>Ask Your Question Here:</h4>
                        <textarea className="textarea textarea-bordered textarea-md w-full max-w-xs" placeholder="Type Your Questions Here"></textarea>
                    </div>
                    <div>
                        <button className='btnQuestion'>Send Question</button>
                    </div>
                </form>
            </div>
        </section>
    )
}

export default ContactUs


