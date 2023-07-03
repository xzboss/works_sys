import React, { useRef, useState } from 'react'
import { Row, Col, Button, Input, Space, Alert, message } from 'antd'
import { useNavigate } from 'react-router-dom'
import { nanoid } from 'nanoid'
import { get, post } from '../../tools/myFetch.js'
import style from './index.module.css'
export default function Register () {
	const usernameReg = /^[a-zA-Z0-9_-]{4,16}$/
	const passwordReg = /^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z]).*$/
	const navigate = useNavigate()
	const usernameIn = useRef()
	const passwordIn = useRef()
	const [messageApi, contextHolder] = message.useMessage()

	let [nameStatus, setNameStatus] = useState('error')
	let [pwdStatus, setPwdStatus] = useState('error')
	const toLogin = () => {
		navigate('/login')
	}
	const register = async () => {
		console.log(nanoid());
		const isExist = await get(`/we22/api/user/selectUserByName?userName=${usernameIn.current.input.value}`)
		console.log(isExist);
		if (isExist) {
			messageApi.open({
				type: 'error',
				content: '对不起，用户名已经存在'
			})
		} else {
			try {
				await post('/we22/api/user/insertUser', {
					userId: nanoid(),
					userName: usernameIn.current.input.value,
					password: passwordIn.current.input.value
				})
				messageApi.open({
					type: 'success',
					content: '注册成功，3秒后返回登陆界面',
					onClose: () => {
						navigate('/login')
					}
				})
			} catch (error) {

			}

		}
	}
	const isLegal_name = (e) => {
		if (usernameReg.test(usernameIn.current.input.value)) {
			setNameStatus('success')
		} else {
			setNameStatus('error')
		}
	}
	const isLegal_pwd = (e) => {
		if (passwordReg.test(passwordIn.current.input.value)) {
			setPwdStatus('success')
		} else {
			setPwdStatus('error')
		}
	}
	const isDisabled = () => {
		return nameStatus === 'success' & pwdStatus === 'success' ? false : true
	}
	return (
		<div className={style.box}>
			{contextHolder}
			<Row justify={'center'} className={style.row}>
				<Col className={style.col}>
					<h1>员工管理系统注册</h1>
				</Col>
			</Row>
			<Row justify={'center'} className={style.row}>
				<Col span={12}>
					<Space style={{ display: 'flex' }} size={'large'} direction='vertical'>
						<Input placeholder='请输入用户名' showCount onChange={isLegal_name} status={nameStatus} ref={usernameIn} />
						<Alert showIcon className={style.alert} message={nameStatus !== 'success' ? '用户名由4~16位字母，数字或下划线组成' : ''} type={nameStatus} />
						<Input placeholder='请输入密码' showCount onChange={isLegal_pwd} status={pwdStatus} ref={passwordIn} />
						<Alert showIcon className={style.alert} message={pwdStatus !== 'success' ? '密码最少6位，包括至少1个大写字母，1个小写字母，1个数字' : ''} type={pwdStatus} />
					</Space>
				</Col>
			</Row>
			<Row justify={'center'} className={style.row}>
				<Col span={4} pull={1}>
					<Button style={{ width: '100%' }} type='link' onClick={toLogin}>返回登录</Button>
				</Col>
				<Col span={4} push={1}>
					<Button style={{ width: '100%' }} type='primary' onClick={register} disabled={isDisabled()}>注册</Button>
				</Col>
			</Row>
		</div>
	)
}
