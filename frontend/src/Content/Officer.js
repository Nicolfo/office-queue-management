import { useState } from 'react'
import {Button, Card, ToggleButton} from 'react-bootstrap';
import './Officer.css'
import '../API/API-Polling.js'
import API from "../API/API.js";


function CounterButton(props) {
    const { counter, selectedCounter, setSelectedCounter } = props;

    return (
        <ToggleButton
            key={counter.id}
            id={`radio-${counter.id}`}
            type="radio"
            variant="secondary"
            className="p-3 m-2"
            checked={selectedCounter.id === counter.id}
            onClick={() => { setSelectedCounter(counter) } }>
            {counter.name}
        </ToggleButton>
    )
}

function CounterSelection(props) {
    const { setSelectCounter, counters, selectedCounter, setSelectedCounter } = props;

    return (
        <div>
            <Card className="mb-2">
                <Card.Header> <b> Select counter </b> </Card.Header>
                <Card.Body>
                    {counters.map(counter => <CounterButton counter={counter} selectedCounter={selectedCounter} setSelectedCounter={setSelectedCounter} />) }
                </Card.Body>
            </Card>
            <Button variant="primary" onClick={() => setSelectCounter(false)}> Confirm </Button>
        </div>
    )
}

function CounterManagement(props) {
    const { callNextCustomer, customer, selectedCounter, setSelectCounter, setCustomer } = props;

    return(
        <div className="officer-container">
            <div className="header">
                <h1> {selectedCounter.name} </h1>
            </div>
            <Card>
                <Card.Body>
                    <Card.Title>
                        The next customer to be served is: {customer}
                    </Card.Title>
                    <Button variant="primary" style={{"marginTop": "0.5rem"}} onClick={() => callNextCustomer()}>
                        Call next customer
                    </Button>
                </Card.Body>
            </Card>
            <div>
                <Button variant="danger" style={{"marginTop": "0.5rem"}} onClick={() => {setSelectCounter(true); setCustomer("...")} }>
                    Change counter
                </Button>
            </div>
        </div>
    )
}





function Officer(props) {
    const { counters, customer, setCustomer, selectCounter, setSelectCounter, selectedCounter, setSelectedCounter } = props;
    const [error, setError] = useState(null);


    const callNextCustomer = () => {
        API.callNextCustomer(selectedCounter.id)
            .then((ticket) => setCustomer(`Ticket #${ticket.ticket_id} for the service ${ticket.service_name}`))
            .catch((err) => setError(err))
    }

    return (
        selectCounter ?
            <CounterSelection setSelectCounter={setSelectCounter} counters={counters} selectedCounter={selectedCounter} setSelectedCounter={setSelectedCounter} />
                :
            <CounterManagement selectedCounter={selectedCounter} setSelectCounter={setSelectCounter} customer={customer} setCustomer={setCustomer} callNextCustomer={callNextCustomer} />
    )
}

export default Officer;