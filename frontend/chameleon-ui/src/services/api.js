import axios from 'axios';
const BASE = 'http://localhost:8080/api'

export const createTransaction = data => axios.post(`${BASE}/transactions`, data);
export const getTransactions = id => axios.get(`${BASE}/transactions/${id}`);
export const getCreditHistory = id => axios.get(`${BASE}/credit-history/${id}`);
export const getCard             = id   => axios.get(`${BASE}/cards/${id}`);
export const getRewards          = id   => axios.get(`${BASE}/rewards/${id}`);
export const getNotifications    = id   => axios.get(`${BASE}/notifications/${id}`);
export const markNotificationsRead = id => axios.patch(`${BASE}/notifications/${id}/read`);