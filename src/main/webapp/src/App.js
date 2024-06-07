import './App.css';
import {createTheme, ThemeProvider} from "@mui/material/styles";
import {useState} from "react";
import MainRoute from './routes/MainRoute';

function App() {

    const [darkMode, setDarkMode] = useState(false);

    const theme = createTheme({
        palette: {
            mode: darkMode ? "dark" : "light",
        },
    })


    return (
        <ThemeProvider theme={theme}>
            <MainRoute darkMode={darkMode} setDarkMode={setDarkMode}/>
        </ThemeProvider>
    );
}

export default App;
