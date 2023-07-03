import React, { useState, useEffect, useRef } from 'react'
import { Layout, Space, Row, Input, Col, Table, Button, Modal, notification } from 'antd';
import { EditTwoTone, SmileOutlined, CloseOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom'
import store from '../../redux/store.js';
import { get, post, put, del } from '../../tools/myFetch.js'
import shortId from '../../tools/shortId.js';
import style from './index.module.css'
const { Header, Footer, Content } = Layout

export default function Sys () {
	let employeeObj = {
		employeeId: shortId(),
		employeeName: undefined,
		gender: undefined,
		age: undefined,
		baseSalary: undefined,
		locationId: 1
	}
	let [dataSource, setDataSource] = useState()
	const [api, contextHolder_notfi] = notification.useNotification()
	const [modal, contextHolder] = Modal.useModal()
	const navigate = useNavigate()
	const init = () => {
		(async () => {
			setDataSource(await get('/we22/api/employee/selectAllEmployee'))
		})()
	}
	useEffect(() => {
		if (!sessionStorage.getItem('isLogin')) {
			navigate('/login', { state: { isLocation: true } })
		}
		(async () => {
			setDataSource(await get('/we22/api/employee/selectAllEmployee'))
		})()
	}, [])
	const add = () => {
		type = 'add'
		openModal(type, employeeObj, '', '')
	}
	const edit = async (record) => {
		type = 'edit'
		const { locationName: address } = await get(`/we22/api/location/selectLocationById?locationId=${record.locationId}`)
		openModal(type, record, address, '')
	}
	const remove = async (record) => {
		type = 'remove'
		await del(`we22/api/employee/deleteEmployeeByName?employeeName=${record.employeeName}`)
		api.open({ message: '员工已经成功开除', icon: <SmileOutlined style={{ color: 'green', }} /> })
		init()
	}
	const where = (record) => {
		navigate('/map', {
			state: { ...record }
		})
	}
	const openModal = (type, body, address, region) => {
		modal.confirm({
			icon: <EditTwoTone />,
			content: (
				<div>
					<br />
					<Row justify='center' direction='columns'>
						<Col span={20}>
							<Space size={'large'} style={{ display: 'flex' }} direction='vertical'>
								<Input addonBefore="用户名" onChange={e => body.employeeName = e.target.value} defaultValue={body.employeeName} />
								<Input addonBefore="性别" onChange={e => body.gender = e.target.value} defaultValue={body.gender} />
								<Input addonBefore="年龄" onChange={e => body.age = e.target.value / 1} defaultValue={body.age} />
								<Input addonBefore="工资" onChange={e => body.baseSalary = e.target.value / 1} defaultValue={body.baseSalary} />
								<Input addonBefore="地址" onChange={e => address = e.target.value} defaultValue={address} />
								<Input addonBefore="地区" onChange={e => region = e.target.value} defaultValue={region} />
							</Space>
						</Col>
					</Row>
					<br />
				</div>
			),
			title: type + 'employee',
			onOk: () => {
				(async () => {
					console.log(body);
					let locationId
					if (type === 'add') {
						const isExistEmployee = await get(`/we22/api/employee/selectEmployeeByName?employeeName=${body.employeeName}`)
						const location = await get(`/we22/api/location/selectLocationByName?locationName=${address}`)
						if (isExistEmployee) {
							api.open({ message: '员工已经存在', icon: <CloseOutlined style={{ color: 'red' }} /> })
						} else if (location) {
							locationId = location.locationId
							await post('/we22/api/employee/insertEmployee', { ...body, locationId })
							api.open({ message: '添加成功', icon: <SmileOutlined style={{ color: 'green', }} /> })
						} else {
							locationId = shortId()
							const place = await get(`/we22/api/findPlace?locationName=${address}&region=${region}`)
							await post('/we22/api/location/insertLocation', {
								locationId,
								locationName: address,
								latitude: place.lat,
								longitude: place.lng
							})
							await post('/we22/api/employee/insertEmployee', { ...body, locationId })
							api.open({ message: '添加成功', icon: <SmileOutlined style={{ color: 'green', }} /> })
						}
					}
					if (type === 'edit') {
						const location = await get(`/we22/api/location/selectLocationByName?locationName=${address}`)
						let result
						if (location) {
							locationId = location.locationId
							result = await put(`we22/api/employee/updateEmployee?oldName=${body.employeeName}`, body)
						} else {
							//如果修改后的地址不存在表里
							const place = await get(`/we22/api/findPlace?locationName=${address}&region=${region}`)
							await post('/we22/api/location/insertLocation', {
								locationId: shortId(),
								locationName: address,
								latitude: place.lat,
								longitude: place.lng
							})
							result = await put(`we22/api/employee/updateEmployee?oldName=${body.employeeName}`, body)
						}
						if (result === '更新成功') {
							api.open({ message: result, icon: <SmileOutlined style={{ color: 'green', }} /> })
						} else {
							api.open({ message: result, icon: <CloseOutlined style={{ color: 'red', }} /> })
						}
					}
					init()
				})()
			},
			onCancel () {
				console.log('Cancel')
			},
		})
	}
	let type = ''

	const columns = [
		{
			title: '员工Id',
			dataIndex: 'employeeId',
			key: 'EmployeeId',
		},
		{
			title: '员工姓名',
			dataIndex: 'employeeName',
			key: 'EmployeeName',
		},
		{
			title: '性别',
			dataIndex: 'gender',
			key: 'Gender',
		},
		{
			title: '年龄',
			dataIndex: 'age',
			key: 'Age',
		},
		{
			title: '基础薪资',
			dataIndex: 'baseSalary',
			key: 'BaseSalary',
		},
		{
			title: '操作',
			dataIndex: 'action',
			key: 'Action',
			render: (_, record) => {
				let employeeObj = record
				return (
					<Space key={record.key}>
						<Button onClick={() => edit(employeeObj)} size='small' type='primary'>Edit</Button>
						<Button onClick={(e) => { remove(employeeObj); e.target.parentNode.parentNode.parentNode.parentNode.parentNode.className = style.leave; }} size='small' type='primary' danger>Remove</Button>
						<Button onClick={() => where(employeeObj)} size='small' type='link' >Where</Button>
					</Space>
				)
			}
		}
	]

	//初始化



	return (
		<div className={style.box}>
			{contextHolder}
			{contextHolder_notfi}
			<Layout>
				<Header>
					<Row justify={'end'}>
						<Col>
							<Button type='primary' onClick={add}>Add</Button>
						</Col>
					</Row>
				</Header>
				<Content>
					<Table pagination={{ pageSize: 7 }} rowKey='employeeId' dataSource={dataSource} columns={columns} />
				</Content>
			</Layout>
		</div>
	)
}
