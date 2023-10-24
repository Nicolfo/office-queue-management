"use strict";

const SERVER_URL = "http://localhost:8080";

// Service

const getAllServices = async () => {
    const response = await fetch(`${SERVER_URL}/API/services/getAll`);
    const json = await response.json();
    if (response.ok)
        return json;
    throw json;
};

// Ticket

const createTicket = async (serviceId) => {
    const response = await fetch(`${SERVER_URL}/API/tickets/createTicket/${serviceId}`, {
        method: "POST"
    });
    const json = await response.json();
    if (response.ok)
        return json;
    throw json;
}

const API = { 
    getAllServices,
    createTicket
};

export default API;