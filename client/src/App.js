import { Routes, Route } from "react-router-dom";
import Landing from "routes/Landing";
import "./App.css";

function App() {
  return (
    <div className="app">
      <Routes>
        <Route path="/" index element={<Landing />} />
      </Routes>
    </div>
  );
}

export default App;
