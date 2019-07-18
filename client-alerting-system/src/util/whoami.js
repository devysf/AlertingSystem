import jwt_decode from "jwt-decode";


const whoisloggedin = ()=>{
  const token = localStorage.getItem("jwtToken");
  const decoded = jwt_decode(token);
  
  return decoded.sub;
}

export default whoisloggedin;
