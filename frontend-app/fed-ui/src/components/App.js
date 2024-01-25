import './main.css';
import LoginForm from './LoginForm';
import AdminListUser from './AdminListUser';
import AdminAddUser from './AdminAddUser';
import ClientMain from './ClientMain';
import ClientTransfer from './ClientTransfer';
import BankForApproval from './BankForApproval';
import BankAproved from './BankAproved';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

function App() {
	return (
		<BrowserRouter>
			<Routes>
				{/* <Route index element={<Home />} /> */}
				<Route path='LoginForm' element={<LoginForm />} />
				<Route path='AdminListUser' element={<AdminListUser />} />
				<Route path='AdminAddUser' element={<AdminAddUser />} />
				<Route path='ClientMain' element={<ClientMain />} />
				<Route path='ClientTransfer' element={<ClientTransfer />} />
				<Route path='BankForApproval' element={<BankForApproval />} />
				<Route path='BankAproved' element={<BankAproved />} />
				{/* <Route path="*" element={<NoPage />} /> */}
			</Routes>
		</BrowserRouter>
	);
}

export default App;
