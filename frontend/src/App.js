import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import {BrowserRouter as Router, useLocation} from "react-router-dom";
import SideBar from "./Sidebar/SideBar";
import NavBar from "./NavBar/NavBar";
<<<<<<< Updated upstream


function Content() {
    const path = useLocation().pathname.toString();
    switch (path) {                                //add to this switch-case your content (defined in the Content folder)
        case "/get-ticket":
            return <>Content goes here</>
=======
import NextClient from "./NextClient/NextClient";
import {useState, useEffect} from "react";
import GetTicketContent from './Content/GetTicketContent';
import Officer from "./Content/Officer";
import { getServingTicketsId, getAvailableCounters } from "./API/API-Polling"


function Content(props) {

    const path = useLocation().pathname.toString();
    switch (path) {                                //add to this switch-case your content (defined in the Content folder)
        case "/get-ticket":
            return <GetTicketContent/>

        case "/Who-is-served":
            return <NextClient counters = {props.counters} tickets = {props.nextTicket}  nextTicket={props.nextTicket} setNextTicket={props.setNextTicket}></NextClient>

        case "/officer":
            return <Officer counters={props.counters} />

>>>>>>> Stashed changes
        default:
            return <h1>Path not found</h1>
    }
}

function App() {
    return (
        <div className="container-fluid" style={{height: '90vh'}}>
            <div className="row align-items-start">
                <Router>
                    <NavBar>
                    </NavBar>
                    <SideBar>
                    </SideBar>
                    <div className="col-9">
                        <Content>
                        </Content>
                    </div>
                </Router>
            </div>
        </div>
    );
}

export default App;
