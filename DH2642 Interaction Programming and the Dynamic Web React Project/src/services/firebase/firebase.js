import firebase from 'firebase/app';
import 'firebase/auth';
import 'firebase/database';

const firebaseConfig = {
  apiKey: "AIzaSyBfEHzWgGkD_4jcoKH95Pn7V97MYp2MLpM",
  authDomain: "safetravels-f2891.firebaseapp.com",
  databaseURL: "https://safetravels-f2891-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "safetravels-f2891",
  storageBucket: "safetravels-f2891.appspot.com",
  messagingSenderId: "59234699847",
  appId: "1:59234699847:web:2aaf750c8fb7418683ff62"
};

firebase.initializeApp(firebaseConfig);

export const auth = firebase.auth;

export const db = firebase.database();