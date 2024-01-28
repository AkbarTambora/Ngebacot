import { db } from "../connect.js"
import bcrypt from "bcryptjs"
import jwt from "jsonwebtoken"

//End Point Register
export const register = (req, res) => {
    /*
        Check error data
     */
    // Check if required fields exist in the request body
    const { username, email, password } = req.body;

    const errors = {};

    if (!username) {
        errors.username = ["Username must not be blank"];
    } else if (username.length < 6) {
        errors.username = ["Username must be at least 6 characters long"];
    } else if (username.includes(' ')){
        errors.username = ["Username cannot contain spaces"];
    }

    if (!email) {
        errors.email = ["Email must not be blank"];
    }

    if (!password) {
        errors.password = ["Password must not be blank"];
    } else if (password.length < 8) {
        errors.password = ["Password must be at least 8 characters long"];
    }

    // Check if there are any errors in the request
    if (Object.keys(errors).length > 0) {
        return res.status(400).json({ errors });
    }
    /* 
        Check users if exists
     */
    // Check if the user already exists
    const q = "SELECT * FROM users WHERE username = ? OR email = ?";
    db.query(q, [username, email], (err, data) => {
        if (err) return res.status(500).json({ message: "Server Error" });
        if (data.length) {
            const existingUser = data.find(
                user => user.username === username || user.email === email
            );
            if (existingUser.username === username) {
                return res.status(409).json({ message: "Username already exists!" });
            } else {
                return res.status(409).json({ message: "Email already exists!" });
            }
        }

        // Check email format
        const emailFormat = /\S+@\S+\.\S+/;
        const isValidEmail = emailFormat.test(email);
        if (!isValidEmail) {
            return res.status(403).json({ message: "Invalid email format!" });
        }

        // Hash the password and create a new user
        const salt = bcrypt.genSaltSync(10);
        const hashedPassword = bcrypt.hashSync(password, salt);

        const insertQuery =
            "INSERT INTO users (`username`,`email`,`password`,`name`) VALUE (?)";
        const values = [username, email, hashedPassword, req.body.name || null];

        db.query(insertQuery, [values], (err, data) => {
            if (err) return res.status(500).json({ message: "Failed to create user." });
            return res.status(200).json({ message: "User has been created." });
        });
    });
};

// EndPoint Login
export const login = (req, res) => {
    const { username, password } = req.body;

    const q = "SELECT * FROM users WHERE username = ?";
    db.query(q, [username], (err, data) => {
        if (err) return res.status(500).json({ message: "Server Error" });
        if (data.length === 0) return res.status(404).json({ message: "User not found!" });

        const checkPassword = bcrypt.compareSync(password, data[0].password);
        if (!checkPassword) return res.status(403).json({ message: "Wrong password or username" });

        const token = jwt.sign({ id: data[0].id }, "secretkey");
        const { password: userPassword, ...userInfo } = data[0];

        //JWT TOKEN AND SEND DATA
        res.status(200).header("Authorization",`Bearer ${token}`).json({
            "jwtToken": token,
            user: {
                id: userInfo.id,
                username: userInfo.username,
                email: userInfo.email,
                name: userInfo.name,
                coverpic: userInfo.coverpic,
                profilepic: userInfo.profilepic,
                city: userInfo.city,
                website: userInfo.website,
                created_at: userInfo.created_at
            }
        });
    });
};


//End Point Logout
export const logout = (req, res) => {
    // Clear the Authorization header to remove the bearer token
    res.setHeader('Authorization', ' ');

    res.clearCookie("accessToken", {
        secure: true,
        sameSite: "none",
        httpOnly: true
    }).status(200).header("Authorization", "").json("User has been logged out.")

}