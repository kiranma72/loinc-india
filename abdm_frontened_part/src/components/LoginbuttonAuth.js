import React from "react";
import { useAuth0 } from "@auth0/auth0-react";
import TestList from "./TestList";

function LoginbuttonAuth() {
    const { loginWithRedirect } = useAuth0();
    const { user, isAuthenticated, isLoading } = useAuth0();
    const loginit = () => {
        return (<button onClick={() => loginWithRedirect()}>Log In</button>);
    }
    return (<button onClick={() => loginWithRedirect()}>Log In</button>);
};
//<TestList Authtoken={isAuthenticated} loginfn={loginit} />
//   return (
//     isAuthenticated && (
//       <div>
//         <img src={user.picture} alt={user.name} />
//         <h2>{user.name}</h2>
//         <p>{user.email}</p>
//       </div>
//     )
//   );
// };
export default LoginbuttonAuth;