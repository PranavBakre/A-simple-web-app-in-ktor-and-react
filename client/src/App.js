import React from "react";
import "static/App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import background from "static/background.jpg";
import Header from "./components/Header";
import { Route, Switch } from "react-router-dom";
import Index from "routes/index";
import Home from "routes/home";
import AuthenticatedRoute from "components/authenticated-route";
import { Provider } from "react-redux";
import store from "./redux/store";
import { BrowserRouter } from "react-router-dom";
function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
    <div
      className="App"
      style={{
        backgroundImage: `url(${background})`,
        backgroundSize: "cover",
        height: "100vh",
      }}
    >
      <Header></Header>

      <Switch>
        <AuthenticatedRoute path="/home" component={Home}></AuthenticatedRoute>
        <Route path="/" component={Index}></Route>
      </Switch>
    </div>
    </BrowserRouter>
    </Provider>
  );
}

export default App;
