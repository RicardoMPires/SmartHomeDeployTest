import React, {useEffect, useState} from 'react';
import {Accordion, AccordionDetails, AccordionSummary, Button, alpha} from "@mui/material";
import Typography from "@mui/material/Typography";
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import Box from "@mui/material/Box";
import { useNavigate } from 'react-router-dom';

export default function Room() {

    const [rooms, setRooms] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8080/rooms')
            .then(response => response.json())
            .then(data => {
                if (data._embedded && data._embedded.roomDTOList) {
                    setRooms(data._embedded.roomDTOList);
                } else {
                    setRooms([]);
                }
            })
            .catch(err => console.log(err))
    }, []);

    const handleViewDevices = (roomId) => {
        navigate(`/rooms/${roomId}/devices`);
    };

    return (
        <Box sx={(theme) => ({
            width: '100%',
            minHeight: '100vh',
            backgroundImage:
                theme.palette.mode === 'light'
                    ? 'linear-gradient(180deg, #CEE5FD, #FFF)'
                    : `linear-gradient(180deg, #02294F, ${alpha('#090E10', 0.0)})`,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            margin: 0,
            padding: 0,
        })}>
            <div>
                {rooms.map((room, index) => (
                        <Accordion key={index + 1}>
                            <AccordionSummary
                                expandIcon={<ArrowDropDownIcon/>}
                                aria-controls={`panel${index + 1}-content`}
                                id={`panel${index + 1}-header`}
                            >
                                <Typography>{room.roomName}</Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                <Box sx={{ display: 'flex', justifyContent: 'space-between',  alignItems: 'flex-start', width: '100%' }}>

                                    <Typography>
                                        <b>Floor:</b> {room.floor}<br/>
                                        <b>Height:</b> {room.roomHeight} meters<br/>
                                        <b>Length:</b> {room.roomLength} meters<br/>
                                        <b>Width:</b> {room.roomWidth} meters<br/>
                                    </Typography>
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        onClick={() => handleViewDevices(room.id)}
                                        sx={{ height: '25%' }}
                                    >
                                        View Devices
                                    </Button>
                                </Box>
                            </AccordionDetails>
                        </Accordion>
                ))}
            </div>
        </Box>
    );
}
