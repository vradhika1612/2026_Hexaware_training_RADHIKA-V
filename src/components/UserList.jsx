import React, { useState, useEffect } from "react";
import axios from "axios";
import "./UserList.css";

function UserList() {
  const [users, setUsers] = useState([]);

 useEffect(() => {
  const fetchUsers = async () => {
    const response = await axios.get(
      "https://jsonplaceholder.typicode.com/users"
    );
    setUsers(response.data);
  };fetchUsers();
}, []);

  // Delete user — remove from UI immediately
  const handleDelete = async (id) => {
  await axios.delete(
    `https://jsonplaceholder.typicode.com/users/${id}`
  );

  setUsers(users.filter((user) => user.id !== id));
};

  return (
    <div className="container mt-4">
      <h2 className="page-title">All Users</h2>
      <p className="page-subtitle">{users.length} users found</p>

      <table className="table table-dark table striped text-center">
        <thead>
          <tr>
            <th>id</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Company</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>{user.phone}</td>
              <td>{user.company.name}</td>
              <td>
                <button className="btn-delete" onClick={() => handleDelete(user.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UserList;