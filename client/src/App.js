import AuthenticatedRoute from "components/AuthenticatedRoute";
import { Routes, Route } from "react-router-dom";
import Dashboard from "modules/Dashboard";
import Landing from "modules/Landing";
import "./App.css";

function App() {
  return (
    <div className="app">
      <Routes>
        <Route path="/" index element={<Landing />} />
        <Route
          path="/app/*"
          index
          element={
            <AuthenticatedRoute>
              <Dashboard />
            </AuthenticatedRoute>
          }
        />
      </Routes>
    </div>
  );
}

export default App;
