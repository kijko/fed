// AdminListUser.js

import React, { useEffect, useState } from 'react';
import './main.css';
import { getUsers, blockUser, unblockUser } from '../admin.js'
import { getLoggedUserId, logOut } from '../auth';
import { useNavigate } from 'react-router-dom';


const AdminListUser = () => {
	const [users, setUsers] = useState([]);
	const navigate = useNavigate();

	const loadUsers = () => {
		return getUsers().then(usrs => setUsers(
					usrs
						.filter(u => u.uuid !== getLoggedUserId())
						.sort((a, b) => a.firstname.localeCompare(b.firstname))
				)
			);
	}

	useEffect(() => {
		loadUsers()

		return () => {}
	}, [])

	const handleBlockUser = user => {
		blockUser(user.uuid).then(() => loadUsers())
	};
	const handleUnblockUser = user => {
		unblockUser(user.uuid).then(() => loadUsers())
	};

	const handleLogOut = () => logOut().then(() => navigate('/login'));
	
	return (
		<div className='admin-list-user'>
			<div className='user-info'>
				<h2>Administrator</h2>
				<button onClick={() => handleLogOut()}>Wyloguj</button>
			</div>
			<h2>Lista użytkowników</h2>
			<button className='add-user-button' onClick={() => navigate('/admin/add-user')}>Dodaj użytkownika</button>
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
					{
						users.map((u, i) => 
							<tr key={i}>
								<td>{i + 1}</td>
								<td>{ u.firstname + ' ' + u.lastname }</td>
								<td>{ u.type }</td>
								<td>{ u.blocked ? 'Zablokowany' : 'Aktywny' }</td>
								<td>
									<button onClick={() => handleBlockUser(u)}>Zablokuj</button>
									<button onClick={() => handleUnblockUser(u)}>Odblokuj</button>
								</td>
							</tr>
						)
					}
				</tbody>
			</table>
		</div>
	);
};

export default AdminListUser;
