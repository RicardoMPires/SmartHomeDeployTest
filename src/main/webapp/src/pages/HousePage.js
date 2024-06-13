import React, {useEffect, useState} from "react"
import SunCard from "../components/SunCard";
import {alpha} from "@mui/material";
import Box from "@mui/material/Box";

const House = (props) => {
    const [houseState, setHouseState] = useState(null);

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BACKEND_API_URL}/smarthome/house`)
            .then(response => response.json())
            .then(data => setHouseState(data))
            .catch(err => console.log(err))
    }, []);


    console.log(houseState)

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
        })} >
            <div>
                {houseState ? houseState.door : 'Loading...'}
            </div>

            <div>
                <h3>
                    {houseState ? houseState.street : 'Loading...'}
                </h3>
            </div>

            <div>
                {houseState ? houseState.houseID : 'Loading...'}
            </div>

            <div>
                {houseState ? houseState.city : 'Loading...'}
            </div>

            <div>
                {houseState ? houseState.latitude : 'Loading...'}
                {houseState ? houseState.longitude : 'Loading...'}
            </div>

            <div>
                {houseState ? (
                    <SunCard latitude = {houseState.latitude}
                                   longitude = {houseState.longitude}/>
                ) : (
                    <span>Loading sunrise/sunset data...</span>
                )}
            </div>
        </Box>
    )
}

export default House;