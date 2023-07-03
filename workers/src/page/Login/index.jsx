import React, { useRef, useEffect } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import {
	Row,
	Col,
	Button,
	Space,
	Input,
	message
} from 'antd'
import store from '../../redux/store'
import { post } from '../../tools/myFetch'
import style from './index.module.css'
export default function Login () {
	const usernameIn = useRef()
	const passwordIn = useRef()
	const navigate = useNavigate()
	const [messageApi, contextHolder] = message.useMessage()
	let { state } = useLocation()
	state = state ? state : { isLocation: false }
	useEffect(() => {
		if (state.isLocation) {
			messageApi.open({
				type: 'warning',
				content: '未登录无权访问！请登录'
			})
		}
	}, [])
	const login = async () => {
		const user = {
			userName: usernameIn.current.input.value,
			password: passwordIn.current.input.value
		}
		console.log(user);
		const token = await post('we22/api/auth/authentication', user)
		const verifyed = !(/.*error.*/).test(token) && token !== '';
		if (verifyed) {
			sessionStorage.setItem('isLogin', true)
			store.dispatch({ type: 'logined', data: {} })
			localStorage.setItem('token', token)
			navigate('/home', {
				state: {
					userName: usernameIn.current.input.value
				}
			})
		} else {
			messageApi.open({
				type: 'error',
				content: '用户或密码错误'
			})
			localStorage.removeItem('token')
		}
	}
	const toRegister = () => {
		navigate('/register')
	}
	return (
		<div className={style.box}>
			{contextHolder}
			<Row justify={'center'} className={style.row}>
				<Col className={style.col}>
					<h1>员工管理系统登录</h1>
				</Col>
			</Row>
			<Row justify={'center'} className={style.row}>
				<Col span={12}>
					<Space style={{ display: 'flex' }} size={'large'} direction='vertical'>
						<Input placeholder='请输入用户名' ref={usernameIn} />
						<Input.Password placeholder='请输入密码' ref={passwordIn} />
					</Space>
				</Col>
			</Row>
			<Row justify={'center'} className={style.row}>
				<Col span={4} pull={1}>
					<Button style={{ width: '100%' }} type='primary' onClick={login}>登 录</Button>
				</Col>
				<Col span={4} push={1}>
					<Button style={{ width: '100%' }} type='link' onClick={toRegister}>还没有账户？</Button>
				</Col>
			</Row>
		</div>
	)
}
