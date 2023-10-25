import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import {BrowserRouter as Router, useLocation} from "react-router-dom";
import SideBar from "./Sidebar/SideBar";
import NavBar from "./NavBar/NavBar";
import NextClient from "./NextClient/NextClient";
import {useState, useEffect} from "react";
import { getServingTicketsId, getAvailableCounters } from "./API/API-Polling"

function Content(props) {







    const path = useLocation().pathname.toString();
    switch (path) {                                //add to this switch-case your content (defined in the Content folder)
        case "/get-ticket":
            return <>Content goes here</>

        case "/Who-is-served":
            return <NextClient counters = {props.counters} tickets = {props.nextTicket}  nextTicket={props.nextTicket} setNextTicket={props.setNextTicket}></NextClient>

        default:
            return <h1>Path not found</h1>
    }
}

function App() {

    const [counters, setCounters] = useState([]);
    const [nextTicket, setNextTicket] = useState([]);

    const [refreshTicket, setRefreshTicket] = useState(0);

    const fnRefreshTicket = () => {
        setRefreshTicket((refreshTicket) => refreshTicket +1);
    }
    

    useEffect( ()=>{
        // to run only once
        // api retrive the counters available
        getAvailableCounters()
        .then((counters) => setCounters(counters))
        console.log(counters);
        // start the polling to refresh the next ticket id
        
         setInterval(() => fnRefreshTicket(), 2000);

    },[]);

    useEffect( ()=>{
        //api get next ticket from db
        getServingTicketsId()
        .then((list)=> { setNextTicket(list);})
        console.log(nextTicket)
        //setNextTicket((nextTicket) => nextTicket.map((ticket)=>{return ticket +1 }) );

    }, [refreshTicket]);

    return (
        <div className="container-fluid" style={{height: '90vh'}}>
            <div className="row align-items-start">
                <Router>
                    <NavBar>
                    </NavBar>
                    <SideBar>
                    </SideBar>
                    <div className="col-9">
                        <Content counters= {counters} setCounters={setCounters} nextTicket = {nextTicket} setNextTicket = {setNextTicket} >
                        </Content>
                    </div>
                </Router>
            </div>
        </div>
    );
}

export default App;
