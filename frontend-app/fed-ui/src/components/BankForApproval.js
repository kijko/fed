// BankForApproval.js

import React, { useEffect, useState } from 'react';
import './main.css';
import { logOut } from '../auth.js'
import { useNavigate } from 'react-router-dom';
import { getTransfers, approveTransfer, rejectTransfer } from '../emp.js'

const BankForApproval = () => {
	const [transfersList, setTransfersList] = useState([])
	const [mode, setMode] = useState('TO_APPROVE'); // or HISTORY

	const loadPendingTransfers = () => {
		// return getTransfers('PENDING').then(trs => setTransfersList(trs.sort((t1, t2) => t1.dateTime.localCompare(t2.dateTime))));
		return getTransfers('PENDING').then(trs => setTransfersList(trs));
	}

	const loadHistoryTransfers = () => {
		// return getTransfers('PENDING').then(trs => setTransfersList(trs.sort((t1, t2) => t1.dateTime.localCompare(t2.dateTime))));
		return getTransfers('APPROVED').then(appr => getTransfers('REJECTED').then(rejs => setTransfersList([...appr, ...rejs])));
	}
	useEffect(() => {
		if (mode === 'TO_APPROVE') {
			loadPendingTransfers();
		} else {
			loadHistoryTransfers();
		}

		return () => {};
	}, [mode])

	const handleApprove = (transfer) => {
		approveTransfer(transfer.id).then(() => loadPendingTransfers());
	}

	const handleReject = (transfer) => {
		rejectTransfer(transfer.id).then(() => loadPendingTransfers());
	}

	const handleLogOut = () => logOut().then(() => navigate('/login'));
	const navigate = useNavigate()
	return (
		<div className='client-history'>
			<div className='user-info'>
				<h2>Pracownik banku</h2>
				<button onClick={() => handleLogOut()}>Wyloguj</button>
			</div>

			<h2>Przelewy { mode === 'TO_APPROVE' ? 'oczekujące' : 'historyczne' } </h2>

			<button onClick={() => { setTransfersList([]); setMode(mode === 'TO_APPROVE' ? 'HISTORY' : 'TO_APPROVE');}}>{ mode === 'TO_APPROVE' ? 'Historia' : 'Do akceptacji' }</button>
			<br></br>
			<br></br>
			<table>
				<thead>
					<tr>
						<th>Numer</th>
						<th>Nadawca</th>
						<th>Odbiorca</th>
						<th>Kwota</th>
						<th>Data zlecenia</th>
						<th>Stan</th>
						<th>Akcje</th>
					</tr>
				</thead>
				<tbody>
				{
					transfersList.map((t, i) => 
						<tr key={i}>
							<td>{ i + 1 }</td>
							<td>{ t.sender }</td>
							<td>{ t.recipient }</td>
							<td>{ t.amount } zł</td>
							<td>{ t.dateTime }</td>
							<td>{ t.status }</td>
							<td>
							{ (mode == 'TO_APPROVE') ? 
								<>
								<button onClick={() => handleApprove(t)}>Zaakceptuj</button>
								<button onClick={() => handleReject(t)}>Odrzuć</button>
								</>
								: '-'
							}
							</td>
						</tr>
					)
				}
				</tbody>
			</table>
		</div>
	);
};

export default BankForApproval;
