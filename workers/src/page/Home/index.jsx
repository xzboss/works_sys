import React, { useEffect } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import { Row, Col, Button } from 'antd'
import store from '../../redux/store'
import style from './index.module.css'

export default function Home () {
	const navigate = useNavigate()
	let { state } = useLocation()
	state = state ? state : { userName: '' }
	const toSys = () => {
		navigate('/sys')
	}
	useEffect(() => {
		if (!sessionStorage.getItem('isLogin')) {
			navigate('/login', { state: { isLocation: true } })
		}
	}, [])

	return (
		<div className={style.border}>
			<Row justify='center'>
				<Col span={24}>
					<h1 className={style.h1}>Welcome {state.userName}</h1>
				</Col>
				<Col span={8}>
					<Button onClick={toSys} type="dashed" block>go workers system</Button>
				</Col>
			</Row>
		</div>
	)
}
