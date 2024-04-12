import React, { useState } from 'react';
import './main.css';
import { auth } from '../auth.js'
import { useNavigate } from 'react-router-dom';

function LoginForm() {
	const [login, setLogin] = useState('');
	const [password, setPassword] = useState('');
	const navigate = useNavigate()

	const handleSubmit = async (event) => {
		event.preventDefault();

		const userType = await auth(login, password);

		if (userType === "ADMIN") {
			navigate('/admin/list');
		} else if (userType === "CLIENT") {
			navigate('/client');
		} else if (userType === "BANK_EMPLOYEE") {
			navigate('/emp');
		} else {
			console.error("Unknown user type");
		}

	};

	return (
		<div className='App'>
			<header className='App-header'>
				<h1>Zaloguj się do systemu</h1>
				<div className='login-form'>
					<form onSubmit={handleSubmit}>
						<input
							type='text'
							placeholder='Login'
							value={login}
							onChange={(e) => setLogin(e.target.value)}
						/>
						<input
							type='password'
							placeholder='Password'
							value={password}
							onChange={(e) => setPassword(e.target.value)}
						/>
						<button type='submit'>Log in</button>
						<button onClick={() => navigate('/register')}>Zarejestruj się</button>
					</form>
				</div>
			</header>
		</div>
	);
}

export default LoginForm;
