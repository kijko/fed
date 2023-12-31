import React, { useState } from 'react';
import axios from 'axios';

function LoginForm() {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const domain = 'https://fed-app.azurewebsites.net'
//    const domain = 'http://localhost:8080'
    try {
      const response = await axios.post(`${domain}/api/sign-in`, {
        login,
        password,
      });
      console.log(response.data); // Handle the response as needed
    } catch (error) {
      console.error('Login error', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" placeholder="Login" value={login} onChange={(e) => setLogin(e.target.value)} />
      <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
      <button type="submit">Log in</button>
    </form>
  );
}

export default LoginForm;