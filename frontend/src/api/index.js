import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:8000';

let base = 'http://localhost:8000';

const api = {
  upload: formData => axios.post(`${base}/general/uploadFolder`, formData),
  add: general => axios.post(`${base}/general/add`, general),
};

export default api
