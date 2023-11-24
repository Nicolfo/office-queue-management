import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import {BrowserRouter as Router, useLocation} from "react-router-dom";
import SideBar from "./Sidebar/SideBar";
import NavBar from "./NavBar/NavBar";
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
            return <Officer counters={props.counters} customer={props.customer} setCustomer={props.setCustomer} selectCounter={props.selectCounter} setSelectCounter={props.setSelectCounter} selectedCounter={props.selectedCounter} setSelectedCounter={props.setSelectedCounter} />

        default:
            return <h1>Path not found</h1>
    }
}

function App() {

    const [counters, setCounters] = useState([]);
    const [nextTicket, setNextTicket] = useState([]);
    const [customer, setCustomer] = useState("...");
    const [selectCounter, setSelectCounter] = useState(true);
    const [selectedCounter, setSelectedCounter] = useState(null);
    const [refreshTicket, setRefreshTicket] = useState(0);
    const [servingView, setServingView] = useState(false);

    const fnRefreshTicket = () => {
        setRefreshTicket((refreshTicket) => refreshTicket + 1);
    }

    useEffect( ()=>{
        const fetchData = async () => {
            try {
                // Fetch the counters
                const countersData = await getAvailableCounters();
                setCounters(countersData);
                setSelectedCounter(countersData[0]);
            } catch (error) {
                // Handle any errors here
                console.error(error);
            }
        };
        fetchData();

        setInterval(() => fnRefreshTicket(), 2000);

    },[]);

    useEffect( ()=>{
        //api get next ticket from db
        if(servingView) {
        getServingTicketsId()
        .then((list)=> { setNextTicket(list);})
        console.log(nextTicket)
        //setNextTicket((nextTicket) => nextTicket.map((ticket)=>{return ticket +1 }) );
        }
    }, [refreshTicket]);

    return (
        <div className="container-fluid" style={{height: '90vh'}}>
            <div className="row align-items-start">
                <Router>
                    <NavBar>
                    </NavBar>
                    <SideBar setservingView={setServingView}>
                    </SideBar>
                    <div className="col-9">
                        <Content counters= {counters} setCounters={setCounters} nextTicket = {nextTicket} setNextTicket = {setNextTicket}
                                 customer={customer} setCustomer={setCustomer} selectCounter={selectCounter} setSelectCounter={setSelectCounter}
                                 selectedCounter={selectedCounter} setSelectedCounter={setSelectedCounter} >
                        </Content>
                    </div>
                </Router>
            </div>
        </div>
    );
}

export default App;
