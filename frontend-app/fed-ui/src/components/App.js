import './main.css';
import LoginForm from './LoginForm';
import AdminListUser from './AdminListUser';
import AdminAddUser from './AdminAddUser';
import ClientMain from './ClientMain';
import ClientTransfer from './ClientTransfer';
import BankForApproval from './BankForApproval';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Navigate to="/login" replace />}/>
				<Route path='/login' element={<LoginForm />} />
				<Route path='/admin/list' element={<AdminListUser />} />
				<Route path='/admin/add-user' element={<AdminAddUser />} />
				<Route path='/client' element={<ClientMain />} />
				<Route path='/client/transfer' element={<ClientTransfer />} />
				<Route path='/emp' element={<BankForApproval />} />
			</Routes>
		</BrowserRouter>
	);
}

export default App;
