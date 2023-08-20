import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { Auth0Provider } from '@auth0/auth0-react';
import { createRoot } from 'react-dom/client';
ReactDOM.render(
  <Auth0Provider
    domain="dev-2877ohzzvcx2thov.jp.auth0.com"
    clientId="mX11QrZMHhQOUzUmGvFqjKdrJiMhvte0"
    authorizationParams={{
      redirect_uri: window.location.origin
    }}
  >
    <BrowserRouter>
      <App />
    </BrowserRouter>,
  </Auth0Provider>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
reportWebVitals();