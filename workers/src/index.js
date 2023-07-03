import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App'
import { BrowserRouter, HashRouter } from 'react-router-dom';
class ROOT extends React.Component {
  render () {
    return (
      <HashRouter>
        <React.StrictMode>
          <App />
        </React.StrictMode>
      </HashRouter>
    )
  }
}
const root = createRoot(document.getElementById('root'))
root.render(<ROOT />,)
