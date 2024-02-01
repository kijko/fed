import axios from 'axios';
import { getToken } from "./auth";
import { backendUrl } from "./global";

export async function getTransfers(status) {
    const response = await axios.get(`${backendUrl}/api/transfers`, { headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
    }, params: { status } });

    return response.data;
}

export async function approveTransfer(transferId) {
    const response = await axios.post(`${backendUrl}/api/transfers/${transferId}`, {
        status: 'APPROVED'
    }, { headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
    }});

    return response.data;
}

export async function rejectTransfer(transferId) {
    const response = await axios.post(`${backendUrl}/api/transfers/${transferId}`, {
        status: 'REJECTED'
    }, { headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
    }});

    return response.data;
}
