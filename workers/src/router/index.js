import { Navigate } from "react-router-dom"
import Login from "../page/Login"
import Home from "../page/Home"
import Register from "../page/Register"
import Sys from "../page/Sys"
import MyMap from "../page/MyMap"
import store from "../redux/store"
const routes = [
	{
		path: '/login',
		element: <Login />
	},
	{
		path: '/home',
		element: <Home />
	},
	{
		path: '/register',
		element: <Register />
	},
	{
		path: '/sys',
		element: <Sys />
	},
	{
		path: '/map',
		element: <MyMap />
	},

	{
		path: '/',
		element: <Navigate to='/login' />
	}
]
export default routes