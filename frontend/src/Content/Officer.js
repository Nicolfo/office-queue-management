import { useState } from 'react'
import { Button, Card, ListGroup } from 'react-bootstrap';
import './Officer.css'
import '../API/API-Polling.js'
import API from "../API/API.js";


function Officer(props) {
    const { counters } = props;
    const [customer, setCustomer] = useState(null);
    const [error, setError] = useState(null);


    const callNextCustomer = () => {
        API.callNextCustomer(counters[0].id)
            .then((ticket) => setCustomer(`${ticket.ticket_id}${ticket.service_name}`))
            .catch((err) => setError(err))
    }


    return (
        <div className="officer-container">
            <div className="header">
                <h1> {counters[0].name} </h1>
            </div>
            <Card>
                <Card.Body>
                    <Card.Title>
                        The next customer to be served is: {customer}
                    </Card.Title>
                    <Button variant="primary" style={{"marginTop": "0.5rem"}} onClick={() => callNextCustomer()} >
                        Call next customer
                    </Button>
                    {/* <ListGroup style={{"marginTop": "1.5rem"}}>
                      //  <ListGroup.Item>
                        //    You are currently serving customer...
                       // </ListGroup.Item>
                    </ListGroup> */}
                </Card.Body>
            </Card>
        </div>
    );
}

export default Officer;