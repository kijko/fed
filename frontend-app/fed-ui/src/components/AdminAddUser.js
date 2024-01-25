// AdminAddUser.js

import React from 'react';
import './main.css';

const AdminAddUser = () => {
	return (
		<div className='add-user-form'>
			<h2>Dodaj użytkownika</h2>
			<form>
				<input type='text' placeholder='Login' id='login' name='login' />

				<input
					type='password'
					placeholder='Hasło'
					id='password'
					name='password'
				/>

				<input type='text' placeholder='Imię' id='firstName' name='firstName' />

				<input
					type='text'
					placeholder='Nazwisko'
					id='lastName'
					name='lastName'
				/>

				<select id='userType' name='userType'>
					<option value='admin'>Admin</option>
					<option value='client'>Klient</option>
					<option value='employee'>Pracownik</option>
				</select>

				<div className='button-container'>
					<button type='submit'>Dodaj</button>
					<button type='button'>Wróć</button>
				</div>
			</form>
		</div>
	);
};

export default AdminAddUser;
