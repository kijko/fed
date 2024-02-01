import axios from 'axios';
import { getToken } from "./auth";
import { backendUrl } from "./global";

export async function getAccountData() {
    const response = await axios.get(`${backendUrl}/api/account`, { headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
    }});

    return response.data;
}

export async function transfer(amount, toAccountNumber) {
    const response = await axios.post(`${backendUrl}/api/account`, {
        amount, toAccountNumber
    }, { headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
    }});

    return response.data;
}
