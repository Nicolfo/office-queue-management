import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import {BrowserRouter as Router, useLocation} from "react-router-dom";
import SideBar from "./Sidebar/SideBar";
import NavBar from "./NavBar/NavBar";
import NextClient from "./NextClient/NextClient";
import {useState, useEffect} from "react";
import GetTicketContent from './Content/GetTicketContent';


function Content() {
    const [counters, setCounters] = useState(["Counter 1", "Counter 2", "Counter 3"]);
    const [nextTicket, setNextTicket] = useState([1, 2, 5, 6]);
    const [refreshTicket, setRefreshTicket] = useState(true);
    
    useEffect( ()=>{
        // to run only once
        // api retrive the counters available
        // start the polling to refresh the next ticket id
        const refresh = setInterval(() =>{setRefreshTicket((refreshTicket)=> !refreshTicket)},3000)

    }, []);
    useEffect( ()=>{
        //api get next ticket from db
        //API.getServingTicketsId()
        //.then((list)=> { setNextTicket(list);})
        setNextTicket((nextTicket) => nextTicket.map((ticket)=>{return ticket +1 }));

    }, [refreshTicket]);


    const path = useLocation().pathname.toString();
    switch (path) {                                //add to this switch-case your content (defined in the Content folder)
        case "/get-ticket":

            return <GetTicketContent/>

        case "/Who-is-served":
            return <NextClient counters = {counters} tickets = {nextTicket}></NextClient>

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
