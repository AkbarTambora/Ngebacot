import { useState, Redirect} from "react";
import { Link } from "react-router-dom";
import "./register.scss";
import axios from "axios";

const Register = () => {
    const [inputs, setInputs] = useState({
        username: "",
        email: "",
        password: "",
    });
    const [err, setErr] = useState(null);
    const [isRegistered, setIsRegistered] = useState(false);

    const handleChange = (e) => {
        setInputs((prev) => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const handleClick = async (e) => {
        e.preventDefault();
        try {
            await axios.post("http://localhost:8800/api/auth/register", inputs);
            setIsRegistered(true); 
        } catch (err) {
            if (err.response.status === 400 && err.response.data.message === "Invalid email format!"){
                setErr({ errors: {email:["Invalid Email Format"]}});
            } else if (err.response.status === 409){
                setErr({ errors: { [err.response.data.message.split(' ')[0].toLowerCase()]: [err.response.data.message] } });
            }else{
                setErr(err.response.data);
            }
        }
    };

    if (isRegistered) {
        return <Redirect to="/" />; // Redirect to home page after successful registration
    }

    return (
        <div className="register">
            <div className="card">
                <div className="left">
                    <h1>Nge bacot</h1>
                    <p>
                        Lorem ipsum dolor sit amet consectetur adipisicing elit. Libero cum,
                        alias totam numquam ipsa exercitationem dignissimos, error nam,
                        consequatur.
                    </p>
                    <span>Do you have an account?</span>
                    <Link to="/login">
                        <button>Login</button>
                    </Link>
                </div>
                <div className="right">
                    <h1>Register</h1>
                    <form>
                        <input
                            type="text"
                            placeholder="Username"
                            name="username"
                            onChange={handleChange}
                        />
                        <input
                            type="email"
                            placeholder="Email"
                            name="email"
                            onChange={handleChange}
                        />
                        <input
                            type="password"
                            placeholder="Password"
                            name="password"
                            onChange={handleChange}
                        />
                        {err && err.errors && (
                            <div className="error-message">
                                {err.errors.username && <p>{err.errors.username[0]}</p>}
                                {err.errors.email && <p>{err.errors.email[0]}</p>}
                                {err.errors.password && <p>{err.errors.password[0]}</p>}
                            </div>
                        )}
                        <button onClick={handleClick}>Register</button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default Register;