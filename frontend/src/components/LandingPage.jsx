import React from 'react'
import Graphic1 from '../assets/img/graphic1.png'
import Graphic2 from '../assets/img/graphic22.png'
import Graphic3 from '../assets/img/graphic33.png'
import Graphic4 from '../assets/img/graphic4.png'
import Graphic5 from '../assets/img/graphic5.png'
import Graphic6 from '../assets/img/graphic6.png'
import GraphicPricing1 from '../assets/img/1@300x.png'
import GraphicPricing2 from '../assets/img/30@300x.png'
import GraphicPricing3 from '../assets/img/360@300x.png'
import Yoga from '../assets/img/Yoga.png'
import Weight from '../assets/img/Weight.png'
import Spinning from '../assets/img/Spinning.png'
import Crossfit from '../assets/img/Crossfit.png'
import Functional from '../assets/img/Functional.png'
import Stretching from '../assets/img/Stretching.png' 
import NavBar from "../components/NavBar"
import ContactUs from './ContactUs'
import Footer from './Footer'
import { Link } from 'react-router-dom'



const LandingPage = () => {
    return (
        <>
        <NavBar />
            <header className='contHeader'>
                    <div className='contHeaderTxt'>
                        <h1 className='headerTittle'>Transform Your Body, Transform Your Life. Welcome to <span className='headerTittleRed'>PrimeFit</span></h1>
                        <p className='pText'>Unlock your potential, sculpt your body, and live your best life with our expert trainers and state-of-the-art facilities.</p>
                        <Link to="/login" className='btnHeader'>Get started</Link>
                    </div>
            </header>
            <main>
                <section className='contCards'>
                    <div id="scrollAboutUs" className='cardInfo'>
                        <img className='graphic1' src={Graphic1}/>
                        <h3 className='cardTt'>Excellent Equipment</h3>
                        <p className='cardTxt'>Unlock your potential, sculpt your body, and live your best life with our expert trainers and state-of-the-art facilities.</p>
                    </div>
                    <div className='cardInfo'>
                        <img className='graphic1' src={Graphic2}/>
                        <h3 className='cardTt'>Variety of Classes</h3>
                        <p className='cardTxt'>We offer a wide range of fitness classes to cater to the interests and needs of all our clients. From yoga to functional training, we have something for everyone!</p>
                    </div>
                    <div className='cardInfo'>
                        <img className='graphic1' src={Graphic3}/>
                        <h3 className='cardTt'>Certified Trainers</h3>
                        <p className='cardTxt'>Our highly trained and certified trainers are ready to guide you every step of the way towards your fitness goals.</p>
                    </div>
                </section>
                <section className='contCards2'>
                    <div className='cardInfo'>
                        <img className='graphic1' src={Graphic4}/>
                        <h3 className='cardTt'>Community</h3>
                        <p className='cardTxt'>Building strength together, the community at the gym inspires, supports, and pushes us to achieve our fitness goals, creating an atmosphere of motivation, camaraderie, and lasting friendships. </p>
                    </div>
                    <div className='cardInfo'>
                        <img className='graphic1' src={Graphic5}/>
                        <h3 className='cardTt'>Working Hours</h3>
                        <p className='cardTxt'>The gym's flexible working hours ensure that no matter how busy life gets, there's always a convenient time to prioritize our health and well-being, empowering us to stay committed to our fitness journey.</p>
                    </div>
                    <div className='cardInfo'>
                        <img className='graphic1' src={Graphic6}/>
                        <h3 className='cardTt'>Healthy snack bars</h3>
                        <p className='cardTxt'>Fueling our bodies with healthy snack bars, packed with nutritious ingredients, not only satisfies our cravings but also provides the necessary energy and nutrients to power through our workouts, making them a smart choice for supporting our fitness goals.</p>
                    </div>
                </section>
                <section className="contVideoGym">
                    <iframe width="80%" height="600" src="https://www.youtube.com/embed/_21XVgfLyeM" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
                </section>
                <section id="scrollOurClasses" className='contTtClasses'>
                    <div>
                        <h2 className='ttOurClasses'>Our classes</h2>
                        <p className='txtOurClasses'>We are committed to helping our clients live healthier, happier lives through fun, unique and science-backed workouts.</p>
                    </div>
                </section>
                <section className='contClasses'>
                    <div className='classes'>
                        <figure className='contImgClasses'>
                            <img src={Yoga} alt="Yoga" className='imgClasses'/>
                        </figure>
                        <div>
                            <h2 className="cardTitle">Yoga!</h2>
                            <p className='cardDescription'>This class is designed with gentle floor based sequences using bolsters, blankets, and props that support the practitioner to fully relax the muscles, slow down the mental activity of the brain, shift emotional patterns, bring ease to the breath, and tune into the nervous system’s healing capacity.</p>
                        </div>
                    </div>
                    <div className='classes'>
                        <figure className='contImgClasses'>
                            <img src={Weight} alt="Yoga" className='imgClasses'/>
                        </figure>
                        <div>
                            <h2 className="cardTitle">Weightlifting Training!</h2>
                            <p className='cardDescription'>Weightlifting is the sport of choice for the most dedicated and committed. If you decide to step on the platform to compete you will spend hours practicing and refining your technique only to create the smallest increases in performance on competition day.</p>
                        </div>
                    </div>
                    <div className='classes'>
                        <figure className='contImgClasses'>
                            <img src={Spinning} alt="Yoga" className='imgClasses'/>
                        </figure>
                        <div>
                            <h2 className="cardTitle">Spinning Class!</h2>
                            <p className='cardDescription'>Ride from the shoreline to the hills in this journey that is designed to provide you with the optimum in fat burning and strength building. This ride will simulate varied terrain as you tackle rolling hills, sprints and other drills to give you a great interval workout.</p>
                        </div>
                    </div>
                </section>
                <section className='contClasses2'>
                    <div className='classes'>
                        <figure className='contImgClasses'>
                            <img src={Crossfit} alt="Yoga" className='imgClasses'/>
                        </figure>
                        <div>
                            <h2 className="cardTitle">Crossfit!</h2>
                            <p className='cardDescription'>TExperience the intensity and camaraderie of CrossFit classes as you challenge yourself and unlock your full potential. Our expert coaches guide you through dynamic workouts that combine functional movements, strength training, and cardio, fostering a supportive environment that pushes you to new heights of strength, endurance, and overall fitness..</p>
                        </div>
                    </div>
                    <div className='classes'>
                        <figure className='contImgClasses'>
                            <img src={Functional} alt="Yoga" className='imgClasses'/>
                        </figure>
                        <div>
                            <h2 className="cardTitle">Functional!</h2>
                            <p className='cardDescription'>Unleash your functional fitness potential in our invigorating functional classes. Led by experienced trainers, these classes focus on improving your everyday movements, enhancing mobility, and building overall strength. Through a variety of exercises that mimic real-life activities, you'll develop a well-rounded fitness foundation, improve your posture, and boost your performance in daily tasks.</p>
                        </div>
                    </div>
                    <div className='classes'>
                        <figure className='contImgClasses'>
                            <img src={Stretching} alt="Yoga" className='imgClasses'/>
                        </figure>
                        <div>
                            <h2 className="cardTitle">Stretching Class!</h2>
                            <p className='cardDescription'>Indulge in the rejuvenating experience of our stretching classes, designed to enhance flexibility, reduce muscle tension, and promote overall relaxation. Led by knowledgeable instructors, these classes provide a dedicated space to unwind, release stress, and improve your range of motion.</p>
                        </div>
                    </div>
                </section>
                <section>
                    <div className='contBtnClasses'>
                        <Link to="/schedule" className='btnClasses'> Meet the complete schedule here</Link>
                    </div>
                </section>
                <section className='contPlansPricing'>
                    <div>
                        <h2 className='ttOurClasses'>Plans & Prices</h2>
                        <p className='txtOurClasses'>Discover a variety of affordable plans and prices tailored to fit your fitness needs and budget, ensuring that everyone can access the benefits of a gym membership without compromising quality.</p>
                    </div>
                    <section className='contPricing'>
                        <div className='prcingInfo'>
                            <h2 className='cardTt'>One-Day Pass</h2>
                            <img className='graphicPricing' src={GraphicPricing1}/>
                            <ul className='listPricing'>
                                <li>✔ Full access to state-of-the-art gym facilities.</li>
                                <li>✔ Full access to the training equipment</li>
                                <li>✔ Flexible hours of operation to accommodate your schedule</li>
                                <li>✔ Access to expert personal trainers</li>
                                <li>✔ Wi-Fi connectivity throughout the gym</li>
                            </ul>
                            <p className='price'>Only $12,99</p>
                            <Link to="/login" className='btnPricing'>Get started</Link>
                        </div>
                        <div className='pricingInfoMo'>
                            <h2 className='cardTt'>Monthly +</h2>
                            <img className='graphicPricing' src={GraphicPricing2}/>
                            <ul className='listPricing'>
                                <li>✔ All features included in the Daily Plan.</li>
                                <li>✔ Specialized workout programs tailored to your fitness goals.</li>
                                <li>✔ Discounts on additional services, such as massage therapy or nutritional counseling.</li>
                                <li>✔ Guest passes to bring a friend or family member for a workout.</li>
                                <li>✔ Access to fitness tracking tools and apps to monitor your progress.</li>
                                <li>✔ Access to fitness challenges and rewards for achieving milestones.</li>
                            </ul>
                            <p className='price'>Only $355</p>
                            <Link to="/login" className='btnPricing'>Get started</Link>
                        </div>
                        <div className='pricingInfoYear'>
                            <h2 className='cardTt'>Full Year</h2>
                            <img className='graphicPricing' src={GraphicPricing3}/>
                            <ul className='listPricing'>
                                <li>✔ All features included in the Monthly Plan.</li>
                                <li>✔ Unlimited access to the gym and facilities for the entire year.</li>
                                <li>✔ DComplimentary personal training sessions to kick-start your fitness journey.</li>
                                <li>✔ Premium member perks, such as reserved parking or early class registration.</li>
                                <li>✔ Nutrition consultations and personalized meal plans.</li>
                                <li>✔ Quarterly body composition analysis and fitness assessments.</li>
                                <li>✔ Access to specialized workshops and seminars led by fitness experts.</li>
                            </ul>
                            <p className='price'>Only $3199</p>
                            <Link to="/login" className='btnPricing'>Get started</Link>
                        </div>
                    </section>
                </section>
                <ContactUs/>
            </main>
        <Footer/>
        </>
    )
}

export default LandingPage
