import axios from 'axios'

const axiosApi = axios.create({
   baseURL: 'http://localhost:3000/'
});

export const setAuthHeader = (token) => {
   axiosApi.defaults.headers.common['Authorization'] = `Bearer ${token}`;
}

// Set the initial header from storage or something (should surround with try catch in actual app)
setAuthHeader(sessionStorage.getItem('jwtToken'));

export default axiosApi;