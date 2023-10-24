import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
function NextClient(props){

    return (

    <div>
    <h1>Serving</h1>

    {props.counters.map((item, index)=>{
         return <Col><h2>{item}: {props.tickets[index]}</h2></Col>
     })}
    

    
    </div>
    );
}

export default NextClient;