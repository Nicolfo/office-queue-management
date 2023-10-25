import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';


function NextClient(props){



    return (

    <div>
    <h1>Serving</h1>

    {props.counters.map((item, index)=>{
        let ticket_served =props.tickets.find((elem)=>elem.counter_id==item.id);
        if(ticket_served!==undefined)
            return  <Col key = {index}><h2>{item.name}: {ticket_served.ticket_id}</h2></Col>
        else
            return  <Col key = {index}><h2>{item.name}: None</h2></Col>

     })}
    

    
    </div>
    );
}

export default NextClient;