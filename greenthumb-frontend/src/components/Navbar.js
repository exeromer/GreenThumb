import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import './Navbar.css';

const Navbar = () => {
    const { loginWithRedirect, logout, isAuthenticated, user } = useAuth0();

    return (
        <nav className="navbar">
            <div className="navbar-container">
                <Link to="/" className="navbar-logo">
                    GreenThumb
                </Link>
                <ul className="nav-menu">
                    <li className="nav-item">
                        <Link to="/catalogo" className="nav-links">
                            CATÁLOGO
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/carrito" className="nav-links">
                            🛒
                        </Link>
                    </li>
                    {isAuthenticated ? (
                        <>
                            <li className="nav-item">
                                <span className="nav-links user-email">{user.name}</span>
                            </li>
                            <li className="nav-item">
                                <button onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })} className="nav-links-button">
                                    SALIR
                                </button>
                            </li>
                        </>
                    ) : (
                        <>
                            <li className="nav-item">
                                <button onClick={() => loginWithRedirect()} className="nav-links-button">
                                    INGRESAR
                                </button>
                            </li>
                        </>
                    )}
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;