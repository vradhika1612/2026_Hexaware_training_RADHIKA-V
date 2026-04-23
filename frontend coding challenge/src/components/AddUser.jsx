import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./AddUser.css";

function AddUser() {
  // ONE state — holds all form field values as an object
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    company: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  // Runs on form submit — sends POST request then redirects
 const handleSubmit = async (e) => {
  e.preventDefault();

  const newUser = {
    name: formData.name,
    email: formData.email,
    phone: formData.phone,
    company: { name: formData.company },
  };

  try {
    const response = await axios.post(
      "https://jsonplaceholder.typicode.com/users",
      newUser
    );

    console.log("API Response:", response.data);
    navigate("/users");
  } catch (err) {
    console.log("Error adding user");
  }
};

  return (
    <div className="container mt-4">
      <h2 className="mb-2">Add New User</h2>
      <p className="page-subtitle">Fill in the details below to register a user.</p>

      <form className="card p-4 shadow-sm" onSubmit={handleSubmit}>

        <div className="form-group">
          <label htmlFor="name">Full Name<span className="text-danger">*</span></label>
          <input type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="e.g. Radhika"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email Address<span className="text-danger">*</span></label>
          <input
            type="email"
            
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="e.g. abc@example.com"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="phone">Phone Number<span className="text-danger">*</span></label>
          <input
            type="text"
           
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            placeholder="e.g. 9876543210"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="company">Company Name<span className="text-danger">*</span></label>
          <input
            type="text"
            id="company"
            name="company"
            value={formData.company}
            onChange={handleChange}
            placeholder="e.g. Hexa Pvt Ltd"
            required
          />
        </div>

        <div className="form-actions">
          <button type="submit" className="btn-submit">Add User</button>
          <button type="button" className="btn-cancel" onClick={() => navigate("/users")}>
            Cancel
          </button>
        </div>

      </form>
    </div>
  );
}

export default AddUser;