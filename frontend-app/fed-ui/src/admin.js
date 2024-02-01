import axios from 'axios';
import { backendUrl } from './global';
import { getToken } from './auth'

export async function getUsers() {
    const response = await axios.get(`${backendUrl}/api/users`, { headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
    }});

    return response.data;
}

export async function blockUser(id) {
    const response = await axios.patch(`${backendUrl}/api/users/${id}`, { blocked: true }, { headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
    }});

    return;
}

export async function unblockUser(id) {
    const response = await axios.patch(`${backendUrl}/api/users/${id}`, { blocked: false }, { headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
    }});

    return;
}

export async function addUser(login, password, firstname, lastname, type) {
    const response = await axios.post(`${backendUrl}/api/users`, {
        login, password, firstname, lastname, type
    }, { headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
    }});

    return response.data;
}
