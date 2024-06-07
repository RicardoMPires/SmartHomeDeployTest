import * as React from 'react';
import {useState} from 'react';
import {
    useTheme, useMediaQuery,
    CssBaseline,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText
} from '@mui/material';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import {AccountCircle} from "@mui/icons-material";
import DarkModeIcon from '@mui/icons-material/DarkMode';
import HomeIcon from '@mui/icons-material/Home';
import BedRoomParentIcon from '@mui/icons-material/BedroomParent';
import DevicesIcon from '@mui/icons-material/Devices';
import MenuIcon from '@mui/icons-material/Menu';
import {useNavigate, Link} from "react-router-dom";

const drawerWidth = 200;

export default function Appbar({change}) {

    const [userLogin, setUserLogin] = useState(null);
    const [mobileOpen, setMobileOpen] = useState(false);
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    const handleUserClick = (event) => {
        setUserLogin(event.currentTarget);
    }

    const handleUserClose = () => {
        setUserLogin(null);
    }

    const handleDrawerToggle = () => {
        setMobileOpen(!mobileOpen);
    }

    const navigate = useNavigate();

    const handleListItemClick = (item) => {
        switch (item) {
            case 'House':
                navigate('/house');
                break;
            case 'Rooms':
                navigate('/rooms');
                break;
            case 'Devices':
                navigate('/devices');
                break;
            default:
                break;
        }
    }

    const drawer = (
        <div>
            <Toolbar/>
            <Box sx={{overflow: 'auto'}}>
                <List>
                    <ListItem onClick={() => handleListItemClick('House')}>
                        <ListItemButton>
                            <ListItemIcon>
                                <HomeIcon/>
                            </ListItemIcon>
                            <ListItemText primary='House'/>
                        </ListItemButton>
                    </ListItem>
                    <ListItem onClick={() => handleListItemClick('Rooms')}>
                        <ListItemButton>
                            <ListItemIcon>
                                <BedRoomParentIcon/>
                            </ListItemIcon>
                            <ListItemText primary='Rooms'/>
                        </ListItemButton>
                    </ListItem>
                    <ListItem onClick={() => handleListItemClick('Devices')}>
                        <ListItemButton>
                            <ListItemIcon>
                                <DevicesIcon/>
                            </ListItemIcon>
                            <ListItemText primary='Devices'/>
                        </ListItemButton>
                    </ListItem>
                </List>
            </Box>
        </div>
    );

    return (
        <Box sx={{display: 'flex'}}>
            <CssBaseline/>
            <AppBar position="fixed" sx={{zIndex: (theme) => theme.zIndex.drawer + 1, backgroundColor: "#1976d2"}}>
                <Toolbar>
                    {isMobile && (
                        <IconButton
                            color="inherit"
                            aria-label="open drawer"
                            edge="start"
                            onClick={handleDrawerToggle}
                            sx={{mr: 2, display: {sm: 'none'}}}
                        >
                            <MenuIcon/>
                        </IconButton>
                    )}
                    <Box sx={{flexGrow: 1}}>
                        <Typography variant="h6"
                                    component={Link}
                                    to="/"
                                    sx={{flexGrow: 1, textDecoration: 'none', color: 'inherit'}}>
                            SMARTHOME 4
                        </Typography>
                    </Box>
                    <IconButton color="inherit"
                                onClick={change}>
                        <DarkModeIcon/>
                    </IconButton>
                    <Button color="inherit"
                            onClick={handleUserClick}
                    >
                        <AccountCircle/>
                    </Button>
                    <Menu anchorEl={userLogin}
                          open={Boolean(userLogin)}
                          onClose={handleUserClose}
                    >
                        <MenuItem onClick={handleUserClose}>Sign In</MenuItem>
                        <MenuItem onClick={handleUserClose}>Sign Up</MenuItem>
                    </Menu>
                </Toolbar>
            </AppBar>
            <Box
                component="nav"
                sx={{width: {sm: drawerWidth}, flexShrink: {sm: 0}}}
            >
                <Drawer
                    variant="temporary"
                    open={mobileOpen}
                    onClose={handleDrawerToggle}
                    ModalProps={{
                        keepMounted: true,
                    }}
                    sx={{
                        display: {xs: 'block', sm: 'none'},
                        '& .MuiDrawer-paper': {width: drawerWidth, boxSizing: 'border-box'},
                    }}
                >
                    {drawer}
                </Drawer>
                <Drawer
                    variant="permanent"
                    sx={{
                        display: {xs: 'none', sm: 'block'},
                        '& .MuiDrawer-paper': {width: drawerWidth, boxSizing: 'border-box'},
                    }}
                    open
                >
                    {drawer}
                </Drawer>
            </Box>
        </Box>
    );
}
