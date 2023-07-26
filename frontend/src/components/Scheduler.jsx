import React from 'react'
import NavBar from "../components/NavBar"
import Footer from "../components/Footer"

const Scheduler = () => {
    return (
        <>
            <NavBar />
                <section className='cont'>
                    <div>
                        <h1 className='schTT'>Meet The Complete Schedule Here</h1>
                    </div>
                    <div className="overflow-x-auto">
                        <table className="table-sm">
                        {/* head */}
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Activity</th>
                                <th>Room Name</th>
                                <th>Capacity</th>
                                <th>Time Start</th>
                                <th>Time End</th>
                                <th>Day Of The Week</th>
                            </tr>
                            </thead>
                            <tbody>
                                {/* row 1 */}
                                <tr>
                                    <th>1</th>
                                    <td>Functional</td>
                                    <td>Outdoor</td>
                                    <td>30</td>
                                    <td>8:00</td>
                                    <td>9:00</td>
                                    <td>Monday</td>
                                </tr>
                                {/* row 2 */}
                                <tr>
                                    <th>2</th>
                                    <td>Yoga</td>
                                    <td>Indoor</td>
                                    <td>20</td>
                                    <td>10:00</td>
                                    <td>11:00</td>
                                    <td>Wednesday</td>
                                </tr>
                                {/* row 3 */}
                                <tr>
                                    <th>3</th>
                                    <td>Weightlifting Training</td>
                                    <td>Indoor</td>
                                    <td>20</td>
                                    <td>14:00</td>
                                    <td>15:00</td>
                                    <td>Friday</td>
                                </tr>
                                {/* row 4 */}
                                <tr>
                                    <th>4</th>
                                    <td>Spinning Class</td>
                                    <td>Indoor</td>
                                    <td>25</td>
                                    <td>17:00</td>
                                    <td>18:00</td>
                                    <td>Friday</td>
                                </tr>
                                {/* row 5 */}
                                <tr>
                                    <th>5</th>
                                    <td>Stretching Class</td>
                                    <td>Indoor</td>
                                    <td>25</td>
                                    <td>11:00</td>
                                    <td>12:00</td>
                                    <td>Saturday</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </section>
            <Footer/>
        </>
    )
}

export default Scheduler