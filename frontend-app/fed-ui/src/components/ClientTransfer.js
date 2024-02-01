// ClientTransfer.js

import React, { useState } from 'react';
import './main.css';
import { logOut } from '../auth.js'
import { useNavigate } from 'react-router-dom';
import { transfer } from '../client.js'

const ClientTransfer = () => {
	const [toAccountNumber, setToAccountNumber] = useState('');
	const [amount, setAmount] = useState('0.0');

	const navigate = useNavigate()

	const clean = () => {
		setAmount('0.0');
		setToAccountNumber('');
	}

	const handleTransfer = () => {
		transfer(amount, toAccountNumber).then(() => clean())
	}

	const handleLogOut = () => logOut().then(() => navigate('/login'));
	return (
		<div className='client-transfer'>
			<div className='user-info'>
				<h2>Klient</h2>
				<button onClick={() => handleLogOut()}>Wyloguj</button>
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
					value={toAccountNumber}
					onChange={(e) => setToAccountNumber(e.target.value)}
				/>

				<label htmlFor='amount'>Kwota:</label>
				<input
					type='number'
					id='amount'
					name='amount'
					placeholder='Wpisz kwotę'
					value={amount}
					onChange={(e) => setAmount(e.target.value)}
				/>

				<div className='button-container'>
					<button type='button' onClick={() => handleTransfer()}>Zleć przelew</button>
					<button type='button' onClick={() => navigate('/client')}>Wróć</button>
				</div>
			</form>
		</div>
	);
};

export default ClientTransfer;
