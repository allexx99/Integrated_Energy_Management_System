import axios from "axios";
import { useState, useEffect } from "react";
import React from "react";
import { useNavigate } from "react-router-dom";

import { useFormik } from "formik";
import * as Yup from "yup";

const AdminRegistration = () => {
  const navigate = useNavigate("");

  const formik = useFormik({
    initialValues: {
      // firstName: '',
      // lastName: '',
      // email: '',
      username: "",
      password: "",
      confirmPassword: "",
    },
    validationSchema: Yup.object({
      // firstName: Yup.string().required('First name is required'),
      // lastName: Yup.string().required('Last name is required'),
      // email: Yup.string().email('Invalid email address').required('Email is required'),
      username: Yup.string().required("Username is required"),
      password: Yup.string()
        .required("Password is required")
        .matches(
          /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/,
          "Password must contain at least 8 characters, including uppercase, lowercase, and numbers"
        ),
      confirmPassword: Yup.string()
        .oneOf([Yup.ref("password"), null], "Passwords must match")
        .required("Confirm password is required"),
    }),
    onSubmit: (values) => {
      // Handle form submission
      // console.log(values);

      axios
        .post("http://localhost:8080/adminRegister", {
          // firstName: values.firstName,
          // lastName: values.lastName,
          username: values.username,
          password: values.password,
          // postList: [],
          // commentList: [],
          // posts: [],
          // email: values.email
        })
        .then((response) => {
          console.log(response.data);
          localStorage.setItem("username", response.data.username);
          localStorage.setItem("usertype", "admin");
          localStorage.setItem("token", response.data.token);
          // alert(response.data)
        })
        .catch((error) => {
          console.error("Error registering admin:", error);
        }); // metoda din backend

      navigate("/Admin");
    },
  });

  return (
    <div className="register">
      <h1>Admin Registration</h1>
      <br></br>

      <form onSubmit={formik.handleSubmit} className="form-container">
        {/* <div>
        <label htmlFor="firstName">First Name:</label>
        <input
          type="text"
          id="firstName"
          name="firstName"
          value={formik.values.firstName}
          onChange={formik.handleChange}
          onBlur={formik.handleBlur}
        />
        {formik.touched.firstName && formik.errors.firstName ? (
          <div className="error-message">{formik.errors.firstName}</div>
        ) : null}
      </div>
      <div>
        <label htmlFor="lastName">Last Name:</label>
        <input
          type="text"
          id="lastName"
          name="lastName"
          value={formik.values.lastName}
          onChange={formik.handleChange}
          onBlur={formik.handleBlur}
        />
        {formik.touched.lastName && formik.errors.lastName ? (
          <div className="error-message">{formik.errors.lastName}</div>
        ) : null}
      </div>
      <div>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          name="email"
          value={formik.values.email}
          onChange={formik.handleChange}
          onBlur={formik.handleBlur}
        />
        {formik.touched.email && formik.errors.email ? (
          <div className="error-message">{formik.errors.email}</div>
        ) : null}
      </div> */}
        <div>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            value={formik.values.username}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
          />
          {formik.touched.username && formik.errors.username ? (
            <div className="error-message">{formik.errors.username}</div>
          ) : null}
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formik.values.password}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
          />
          {formik.touched.password && formik.errors.password ? (
            <div className="error-message">{formik.errors.password}</div>
          ) : null}
        </div>
        <div>
          <label htmlFor="confirmPassword">Confirm Password:</label>
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={formik.values.confirmPassword}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
          />
          {formik.touched.confirmPassword && formik.errors.confirmPassword ? (
            <div className="error-message">{formik.errors.confirmPassword}</div>
          ) : null}
        </div>
        <button type="submit" disabled={!formik.isValid}>
          Register
        </button>
      </form>
    </div>
  );
};

export default AdminRegistration;
