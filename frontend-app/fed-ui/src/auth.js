import axios from 'axios';
import { backendUrl } from './global';

export function getToken() {
    return localStorage.getItem('token')
}

export async function auth(login, password) {
		try {
			const response = await axios.post(`${backendUrl}/api/sign-in`, {
				login,
				password,
			});

            localStorage.setItem('token', response.data.jwt);
            localStorage.setItem('userType', response.data.type);
            localStorage.setItem('expireAt', response.data.expireAt);

            return response.data.type;
		} catch (error) {
			console.error('Login error', error);
		}
}

export function getLoggedUserId() {
    const token = getToken();
    const payload = atob(token.split('.')[1]);

    return JSON.parse(payload).sub;
}

export async function logOut() {
    localStorage.clear()

    return;
}
