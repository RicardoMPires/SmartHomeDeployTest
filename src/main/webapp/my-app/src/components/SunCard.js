import React, { useEffect, useState } from 'react';
import { Container, Grid, Card, CardContent, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import WbSunnyIcon from '@mui/icons-material/WbSunny';
import WbTwilightIcon from '@mui/icons-material/WbTwilight';
import axios from "axios";

const StyledContainer = styled(Container)(({ theme }) => ({
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: theme.spacing(4),
}));

const StyledCard = styled(Card)(({ theme }) => ({
    width: '200px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    // background: 'radial-gradient(circle, rgba(128,91,5,1) 0%, rgba(179,118,9,1) 44%, rgba(218,219,22,1) 100%)',
    backgroundImage:
        theme.palette.mode==='light'
        ? 'linear-gradient(315deg, #f6f6f6 0%, #e9e9e9 74%)'
        : 'linear-gradient(315deg, #2a2a2a 0%, #1a1a1a 74%)',
}));

const StyledContent = styled(CardContent)(({ theme }) => ({
    textAlign: 'center',
}));

const SunCard = ({latitude, longitude}) => {
    const [sunrise, setSunrise] = useState('');
    const [sunset, setSunset] = useState('');

    useEffect(() => {
        const fetchSunData = async () => {
            try {
                const res = await axios.get(`https://api.sunrise-sunset.org/json?lat=${latitude}&lng=${longitude}`);
                setSunrise(res.data.results.sunrise);
                setSunset(res.data.results.sunset);
            } catch (error) {
                console.error("Error fetching the sunrise/sunset data: ", error);
            }
        };
        fetchSunData();
    }, [latitude, longitude]);

    return (
        <StyledContainer>
            <StyledCard>
                <StyledContent>
                    <Grid container spacing={3}>
                        <Grid item xs={12} md={6}>
                            <WbSunnyIcon fontSize="small"/>
                            <Typography variant="h6">Sunrise</Typography>
                            <Typography variant="body1">{sunrise || 'Loading...'}</Typography>
                        </Grid>
                        <Grid item xs={12} md={6}>
                            <WbTwilightIcon fontSize="small"/>
                            <Typography variant="h6">Sunset</Typography>
                            <Typography variant="body1">{sunset || 'Loading...'}</Typography>
                        </Grid>
                    </Grid>
                </StyledContent>
            </StyledCard>
        </StyledContainer>
    );
};

export default SunCard;
