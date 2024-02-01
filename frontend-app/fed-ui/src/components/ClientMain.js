// ClientMain.js

import React, { useEffect, useState } from 'react';
import './main.css';
import { getAccountData } from '../client.js'
import { useNavigate } from 'react-router-dom';
import { logOut } from '../auth.js'

const ClientMain = () => {
	const [accountData, setAccountData] = useState({
		accountNumber: '',
		login: '',
		firstName: '',
		lastName: '',
		balance: '',
	});
	const [outboundTransfers, setOutboundTransfers] = useState([])
	const [inboundTransfers, setInboundTransfers] = useState([])
	const navigate = useNavigate()

	useEffect(() => {
		getAccountData().then(data => {
			console.log(data);
			setAccountData(data);
			setOutboundTransfers(data.outboundTransfers);
			setInboundTransfers(data.inboundTransfer);
			// setOutboundTransfers(data.outboundTransfers.sort((t1, t2) => t1.requestedAt.localCompare(t2.requestedAt)));
			// setInboundTransfers(data.inboundTransfer.sort((t1, t2) => t1.requestedAt.localCompare(t2.requestedAt)));
		});

		return () => {};
	}, [])

	const handleLogOut = () => logOut().then(() => navigate('/login'));
	return (
		<div className='client-main'>
			<div className='user-info'>
				<h2>Klient</h2>
				<button onClick={() => handleLogOut()}>Wyloguj</button>
			</div>

			<div className='account-info'>
				<table>
					<thead>
						<tr>
							<th>Numer konta bankowego</th>
							<th>Imię i Nazwisko</th>
							<th>Stan konta</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>{ accountData.accountNumber }</td>
							<td>{ accountData.firstName + ' ' + accountData.lastName }</td>
							<td>{ accountData.balance + 'zł' }</td>
						</tr>
					</tbody>
				</table>

				<button className='new-transfer-button' onClick={() => navigate('/client/transfer')}>Nowy przelew</button>
			</div>

			<br></br>
			<br></br>

			<div className='outgoing-transfers'>
				<h3>Przelewy wychodzące</h3>
				<table>
					<thead>
						<tr>
							<th>Numer</th>
							<th>Kwota</th>
							<th>Odbiorca</th>
							<th>Data przelewu</th>
							<th>Stan przelewu</th>
						</tr>
					</thead>
					<tbody>
					{
						outboundTransfers.map((t, i) =>
								<tr key={i}>
									<td>{ i + 1 }</td>
									<td>{ t.amount }zł</td>
									<td>{ t.toAccountNumber }</td>
									<td>{ t.requestedAt }</td>
									<td>{ t.status }</td>
								</tr>
						)
					}
					</tbody>
				</table>
			</div>

			<br></br>
			<br></br>

			<div className='incoming-transfers'>
				<h3>Przelewy przychodzące</h3>
				<table>
					<thead>
						<tr>
							<th>Numer</th>
							<th>Kwota</th>
							<th>Nadawca</th>
							<th>Data przelewu</th>
							<th>Stan przelewu</th>
						</tr>
					</thead>
					<tbody>
					{
						inboundTransfers.map((t, i) => 
							<tr key={i}>
								<td>{ i + 1 }</td>
								<td>{ t.amount } zł</td>
								<td>{ t.fromAccountNumber }</td>
								<td>{ t.requestedAt }</td>
								<td>{ t.status }</td>
							</tr>
						)
					}
					</tbody>
				</table>
			</div>
		</div>
	);
};

export default ClientMain;
