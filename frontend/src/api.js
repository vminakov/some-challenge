import axios from 'axios';

const BASE_URL = "http://localhost:8080"

export function fetchCards() {
    return axios.get(`${BASE_URL}/cards`);
}

export function createCard(number, initialBalance) {
    return axios.post(`${BASE_URL}/cards`, {
        number: number,
        initialBalance: initialBalance
    })
}

export function fetchTransactions(creditCardId) {
    return axios.get(`${BASE_URL}/cards/${creditCardId}/transactions`);
}

export function createTransaction(creditCardId, type, amount) {
    return axios.post(`${BASE_URL}/cards/${creditCardId}/transactions`, {
        type: type,
        amount: amount
    });
}