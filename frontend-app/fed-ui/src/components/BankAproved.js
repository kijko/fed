// BankAproved.js

import React from 'react';
import './main.css';

const BankAproved = () => {
	return (
		<div className='client-history'>
			<div className='user-info'>
				<h2>Pracownik banku</h2>
				<button>Wyloguj</button>
			</div>

			<h2>Przelewy zaksięgowane</h2>

			<button>Oczekujące</button>
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
					<tr>
						<td>1</td>
						<td>Kowalski</td>
						<td>żona</td>
						<td>50zł</td>
						<td>2024-01-23</td>
						<td>Zatwierdzony</td>
						<td>Zaksięgowany</td>
					</tr>
				</tbody>
			</table>
		</div>
	);
};

export default BankAproved;
