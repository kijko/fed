import React, { useState } from 'react';
import './main.css';
import { auth, register } from '../auth.js'
import { useNavigate } from 'react-router-dom';

function RegisterForm() {
	const [login, setLogin] = useState('');
	const [password, setPassword] = useState('');
	const [firstName, setFirstName] = useState('');
	const [lastName, setLastName] = useState('');
	const navigate = useNavigate()

	const handleSubmit = async (event) => {
		event.preventDefault();

		const response = await register(login, password, firstName, lastName);

		if (response) {
            navigate('/login');
		}
	};

	return (
		<div className='App'>
			<header className='App-header'>
				<h1>Zarejestruj się</h1>
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
                        <input
                            type='text'
                            placeholder='Imię'
                            id='firstName'
                            name='firstName'
                            value={firstName}
                            onChange={e => setFirstName(e.target.value)}
                        />
                        <input
                            type='text'
                            placeholder='Nazwisko'
                            id='lastName'
                            name='lastName'
                            value={lastName}
                            onChange={e => setLastName(e.target.value)}
                        />

						<button type='submit'>Zarejestruj się</button>
						<button onClick={() => navigate('/login')}>Wróć</button>
					</form>
				</div>
			</header>
		</div>
	);
}

export default RegisterForm;
