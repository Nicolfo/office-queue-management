import { Alert, Button, Card, ToggleButton } from "react-bootstrap";
import API from "../API/API";
import { useState, useEffect } from "react";

function GetTicketContent() {

    const [serviceList, setServiceList] = useState([]);
    const [selectedService, setSelectedService] = useState(null);
    const [ticket, setTicket] = useState(null);
    const [error, setError] = useState(null);

    // Get all available services
    const loadServices = async () => {
        try {
            const services = await API.getAllServices();
            setServiceList(services);
            setSelectedService(services[0]);
        } catch (error) {
            setError(error);
        }
    }

    // Generate ticket
    const generateTicket = async (service) => {
        try {
            const ticket = await API.createTicket(service.id);
            setTicket(ticket);
        } catch (error) {
            setError(error);
        }
    }

    // Convert duration from java string to normal text
    const getStringForDuration = (duration) => {
        const str = duration.slice(2);
        let valStr = "";
        let hh = -1, mm = -1, ss = -1;
        for (let i = 0; i < str.length; i++) {
            if (str[i] === "H") {
                hh = Number.parseInt(valStr);
                valStr = "";
            } else if (str[i] === "M") {
                mm = Number.parseInt(valStr);
                valStr = "";
            } else if (str[i] === "S") {
                ss = Number.parseInt(valStr);
                valStr = "";
            } else {
                valStr += str[i];
            }
        }
        
        valStr = "";
        if (ss >= 0) {
            valStr = `${ss} second(s)`;
        }
        if (mm >= 0) {
            valStr = `${mm} minute(s), ` + valStr;
        }
        if (hh >= 0) {
            valStr = `${hh} hour(s), ` + valStr;
        }

        return valStr;
    }

    // Run this when the component is created
    useEffect(() => {
        loadServices();
    }, []);

    return (
        <div>
            { error ? <Alert variant="danger" dismissible onClose={() => setError(null)}>{error.message}</Alert> : <></> }
            <Card className="mb-2">
                <Card.Header><b>Select service</b></Card.Header>
                <Card.Body>
                    {serviceList.map(service => <ServiceButton service={service} selectedService={selectedService} setSelectedService={setSelectedService}/>)}
                </Card.Body>
            </Card>
            <Button variant="primary" style={{"marginLeft": "31.5rem"}} onClick={() => generateTicket(selectedService)}>Get ticket</Button>
            {ticket ?
            <Card className="mb-2">
                <Card.Body>
                    Ticket #{ticket.id}, estimated waiting time: {getStringForDuration(ticket.estimated_time)}
                </Card.Body>
            </Card>
            : <></>}
        </div>
    
    );

}

function ServiceButton({ service, selectedService, setSelectedService }) {

    return (
        <ToggleButton
            key={service.id}
            id={`radio-${service.id}`}
            type="radio"
            variant="primary"
            className="p-3 m-2"
            checked={selectedService.id === service.id}
            onClick={() => { setSelectedService(service); console.log(service); }}
        >{service.name[0].toUpperCase() + service.name.slice(1)}</ToggleButton>
    )

}

export default GetTicketContent;