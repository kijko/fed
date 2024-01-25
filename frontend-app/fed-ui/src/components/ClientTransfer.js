// ClientTransfer.js

import React from 'react';
import './main.css';

const ClientTransfer = () => {
	return (
		<div className='client-transfer'>
			<div className='user-info'>
				<h2>Nazwa użytkownika</h2>
				<button>Wyloguj</button>
			</div>
			<br></br>
			<br></br>
			<h2>Przelej Środki</h2>

			<form>
				<label htmlFor='accountNumber'>Numer konta bankowego:</label>
				<input
					type='text'
					id='accountNumber'
					name='accountNumber'
					placeholder='Wpisz numer konta bankowego'
				/>

				<label htmlFor='amount'>Kwota:</label>
				<input
					type='text'
					id='amount'
					name='amount'
					placeholder='Wpisz kwotę'
				/>

				<div className='button-container'>
					<button type='submit'>Zleć przelew</button>
					<button type='button'>Wróć</button>
				</div>
			</form>
		</div>
	);
};

export default ClientTransfer;
