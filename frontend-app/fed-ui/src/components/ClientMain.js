// ClientMain.js

import React from 'react';
import './main.css';

const ClientMain = () => {
	return (
		<div className='client-main'>
			<div className='user-info'>
				<h2>Mirek Kowalski</h2>
				<button>Wyloguj</button>
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
							<td>123456789</td>
							<td>Mirek Kowalski</td>
							<td>100zł</td>
						</tr>
					</tbody>
				</table>

				<button className='new-transfer-button'>Nowy przelew</button>
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
						<tr>
							<td>1</td>
							<td>50zł</td>
							<td>żona</td>
							<td>2024-01-23</td>
							<td>Zaksięgowany</td>
						</tr>
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
						<tr>
							<td>1</td>
							<td>20000zł</td>
							<td>szef</td>
							<td>2024-01-23</td>
							<td>Zaksięgowany</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	);
};

export default ClientMain;
