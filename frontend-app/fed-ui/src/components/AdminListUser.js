// AdminListUser.js

import React from 'react';
import './main.css';

const AdminListUser = () => {
	return (
		<div className='admin-list-user'>
			<h2>Lista użytkowników</h2>
			<button className='add-user-button'>Dodaj użytkownika</button>
			<table className='user-table'>
				<thead>
					<tr>
						<th>Numer</th>
						<th>Imię i Nazwisko</th>
						<th>Typ</th>
						<th>Stan</th>
						<th>Akcje</th>
					</tr>
				</thead>
				<tbody>
					{/* Dodane na sztywno */}
					<tr>
						<td>1</td>
						<td>Mirek Kowalski</td>
						<td>Client</td>
						<td>Aktywny</td>
						<td>
							<button>Zablokuj</button>
							<button>Odblokuj</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	);
};

export default AdminListUser;
