import React,{useState, useEffect} from 'react';
import {getCard} from '../services/api';

export default function ChameleonCard({customerId}){
 const [card, setCard] =useState(null);
 const THEMES = {
     FOOD:{bg:'linear-gradient(135deg,#FF6B35,#F7931E)',emoji:'🍕',label:'Food card'},
     TRAVEL:{ bg:'linear-gradient(135deg,#0077B6,#00B4D8)',emoji:'✈️',label:'Travel Card'},
     SHOPPING:{ bg:'linear-gradient(135deg,#7B2D8B,#C77DFF)',emoji:'🛍️',label:'Shopping Card'},
     FUEL:{ bg:'linear-gradient(135deg,#333,#666)',emoji:'⛽',label:'Fuel Card'},
     ENTERTAINMENT:{ bg:'linear-gradient(135deg,#D62828,#F77F00)',emoji:'🎬',label:'Entertainment Card'},
     UTILITIES:{ bg:'linear-gradient(135deg,#2D6A4F,#52B788)',emoji:'⚡',label:'Utilities Card'},
     GENERAL: {bg: 'linear-gradient(135deg,#457B9D,#1D3557)',emoji: '💳',label: 'General Card'}

 }
 useEffect(() =>{
    const fetch = () => getCard(customerId).then(r => setCard(r.data)).catch(() => {});
    fetch();
    const intervel =setInterval(fetch, 5000);
    return () => clearInterval(intervel);
 },[customerId]);
 if (!card) return <p>Loading your chameleon card...</p>;
    const theme = THEMES[card.activeCategory] || THEMES.GENERAL;
    return (
        <div style={{ background:theme.bg, borderRadius:'20px', padding:'36px',
            color:'#fff', width:'400px', fontFamily:'Arial',
            boxShadow:'0 12px 40px rgba(0,0,0,0.35)', transition:'background 1.2s ease' }}>
            <div style={{fontSize:'56px'}}>{theme.emoji}</div>
            <h2>🦎 Chameleon Card</h2>
            <h3 style={{opacity:0.9}}>{theme.label}</h3>
            <p>Customer: {card.customerId}</p>
            <p>Card ID: {card.cardId}</p>
            <p>KNN Confidence: {card.knnConfidence?.toFixed(1)}%</p>
            <p style={{fontSize:'12px',opacity:0.8}}>Updated: {new Date(card.lastUpdated).toLocaleString()}</p>
        </div>
);

}