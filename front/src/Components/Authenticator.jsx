import { Outlet, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

export default function Authenticator() {
    const [token, setToken] = useState(localStorage.getItem('access_token'));
    const navigate = useNavigate();

    useEffect(() => {
        if (!token) {
            navigate('/login');
        }
    }, [token, navigate]);

    return (
        <div>
            {token ? <Outlet /> : null}
        </div>
    );
}