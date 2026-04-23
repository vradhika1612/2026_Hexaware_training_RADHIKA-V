import React from "react";
import { BrowserRouter, Routes, Route, NavLink } from "react-router-dom";
import UserList from "./components/UserList";
import AddUser from "./components/AddUser";

function App() {
  return (
    <BrowserRouter>
      <header className="d-flex justify-content-between bg-dark">
  
  <div className="text-white fw-bold">
    Admin Dashboard
  </div>

  
  <nav className="d-flex gap-4">
    <NavLink to="/users" className={"text-white text-decoration-none"}>
      User List
    </NavLink>

    <NavLink to="/add-user" className={"text-warning text-decoration-none"}>
      Add User
    </NavLink>
  </nav>

</header>
      <main className="main-content">
        <Routes>
          <Route path="/" element={<UserList />} />
          <Route path="/users" element={<UserList />} />
          <Route path="/add-user" element={<AddUser />} />
        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;