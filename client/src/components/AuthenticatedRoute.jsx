import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";

function AuthenticatedRoute({ children }) {
  const { isLoggedIn } = useSelector((state) => state.auth);

  if (isLoggedIn) return children;
  return <Navigate to="/" />;
}

export default AuthenticatedRoute;
