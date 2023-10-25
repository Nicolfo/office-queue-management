import {useNavigate,useLocation} from 'react-router-dom';
function SideBar(props){
    const path = useLocation().pathname;
    const navigate = useNavigate();

    return (
        <div className="d-flex flex-column flex-shrink-0 bg-light col-3 p-2">

            <aside>
                <ul className="nav nav-pills flex-column mb-auto nav-fill ">
                    <li className="nav-item">
                        <button className={path==='/get-ticket' ? "nav-link active link-light text-start":"nav-link link-dark text-start"} onClick={()=>{navigate('/get-ticket')}} >
                            Get Ticket
                        </button>
                    </li>
<<<<<<< Updated upstream
=======
                    <li className="nav-item">
                        <button className={path==='/Who-is-served' ? "nav-link active link-light text-start":"nav-link link-dark text-start"} onClick={()=>{navigate('/Who-is-served')}} >
                            Who is served
                        </button>
                    </li>
                    <li className="nav-item">
                        <button className={path==='/officer' ? "nav-link active link-light text-start":"nav-link link-dark text-start"} onClick={()=>{navigate('/officer')}} >
                            Officer
                        </button>
                    </li>
>>>>>>> Stashed changes
                    {/*props.loggedIn && props.user.role==="Manager" ?
                        <li className="nav-item">
                            <button className={path==='/stats' ? "nav-link active link-light text-start":"nav-link link-dark text-start"}  onClick={()=>{navigate('/stats')}}>
                                Show Statistics
                            </button>
                        </li>
                        :
                        <></>*/              // this is to be used for manager stats
                    }

                </ul>
            </aside>

        </div>

    )

}


export default SideBar;