import React, {useEffect, useState} from 'react';
import {Container, Card, CardContent, Typography} from '@mui/material';
import {styled} from '@mui/material/styles';
import DeviceThermostatIcon from '@mui/icons-material/DeviceThermostat';
import axios from 'axios';

const StyledContainer = styled(Container)(({theme}) => ({
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: theme.spacing(4),
}));

const StyledCard = styled(Card)(({theme}) => ({
    width: '150px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundImage:
        theme.palette.mode === 'light'
            ? 'linear-gradient(315deg, #f6f6f6 0%, #e9e9e9 74%)'
            : 'linear-gradient(315deg, #2a2a2a 0%, #1a1a1a 74%)',
}));

const StyledContent = styled(CardContent)(({theme}) => ({
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    textAlign: 'center',
}));

const TempCard = () => {
    const [temperature, setTemperature] = useState('');

    useEffect(() => {
        (async () => {
            try {
                const res = await axios.get('https://api.openweathermap.org/data/2.5/weather?q=Porto&appid=6b3f0b6b3f3f5f3f3f5f3f5f3f5f3f5f');
                setTemperature(res.data.main.temp);
            } catch (error) {
                console.error("Error fetching the temperature data: ", error);
            }
        })();
    }, []);

    return (
        <StyledContainer>
            <StyledCard>
                <StyledContent>
                    <DeviceThermostatIcon fontSize="small"/>
                    <Typography variant="h6">Temperature</Typography>
                    <Typography variant="body1">{temperature}</Typography>
                </StyledContent>
            </StyledCard>
        </StyledContainer>
    );
}

export default TempCard;