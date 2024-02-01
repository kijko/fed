// AdminAddUser.js

import { clear } from '@testing-library/user-event/dist/clear';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './main.css';
import { addUser } from '../admin.js'
import { logOut } from '../auth.js'

const AdminAddUser = () => {
	const [login, setLogin] = useState('');
	const [password, setPassword] = useState('');
	const [firstname, setFirstname] = useState('');
	const [lastname, setLastname] = useState('');
	const [type, setType] = useState('CLIENT');
	const navigate = useNavigate()

	const clear = () => {
		setLogin('');
		setPassword('');
		setFirstname('');
		setLastname('');
		setType('CLIENT');
	}

	const handleSubmit = () => {
		addUser(login, password, firstname, lastname, type).then(() => clear())
	}

	const handleLogOut = () => logOut().then(() => navigate('/login'));
	return (
		<div className='add-user-form'>
			<div className='user-info'>
				<h2>Administrator</h2>
				<button onClick={() => handleLogOut()}>Wyloguj</button>
			</div>
			<h2>Dodaj użytkownika</h2>
			<form >
				<input type='text' placeholder='Login' id='login' name='login' value={login} onChange={e => setLogin(e.target.value)}/>

				<input
					type='password'
					placeholder='Hasło'
					id='password'
					name='password'
					value={password}
					onChange={e => setPassword(e.target.value)}
				/>

				<input type='text' placeholder='Imię' id='firstName' name='firstName' value={firstname} onChange={e => setFirstname(e.target.value)}/>

				<input
					type='text'
					placeholder='Nazwisko'
					id='lastName'
					name='lastName'
					value={lastname} onChange={e => setLastname(e.target.value)}
				/>

				<select id='userType' name='userType' value={type} onChange={e => setType(e.target.value)}>
					<option value='ADMIN'>Admin</option>
					<option value='CLIENT'>Klient</option>
					<option value='BANK_EMPLOYEE'>Pracownik</option>
				</select>

				<div className='button-container'>
					<button type='button' onClick={() => handleSubmit()}>Dodaj</button>
					<button type='button' onClick={() => navigate('/admin/list')}>Wróć</button>
				</div>
			</form>
		</div>
	);
};

export default AdminAddUser;
