import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { Row, Col, Descriptions } from 'antd'
import style from './index.module.css'
import { get } from '../../tools/myFetch.js'
export default function MyMap () {
	let { state } = useLocation()
	state = state ? state : { employeeName: '', gender: '', age: 0, baseSalary: 0 }
	let [locationName, setLocationName] = useState()
	const [distance, setDistance] = useState(0)
	const navigate = useNavigate()
	useEffect(() => {
		if (!sessionStorage.getItem('isLogin')) {
			navigate('/login', { state: { isLocation: true } })
		}
		(async () => {
			const location = await get(`/we22/api/location/selectLocationById?locationId=${state.locationId}`)
			setLocationName(location.locationName)
			const { BMapGL } = window
			const res = await get(`/we22/api/getDistance?latitude=${location.latitude}&longitude=${location.longitude}`)
			setDistance(res)
			let map = new BMapGL.Map("container")
			let pointMain = new BMapGL.Point(103.738, 29.566)
			let markerMain = new BMapGL.Marker(pointMain);
			let point = new BMapGL.Point(location.longitude, location.latitude)
			let marker = new BMapGL.Marker(point);        // 创建标注   
			map.addOverlay(marker)
			map.addOverlay(markerMain)
			const v = map.getViewport([point, pointMain])
			let driving = new BMapGL.DrivingRoute(map, { renderOptions: { map: map, autoViewport: true } });
			driving.search(pointMain, point);
			map.centerAndZoom(v.center, v.zoom)
			map.enableScrollWheelZoom(true);
		})()

	}, [])
	return (
		<div className={style.box}>
			<Row>
				<Col>
					<Descriptions title="员工信息">
						<Descriptions.Item label="姓名">{state.employeeName}</Descriptions.Item>
						<Descriptions.Item label="性别">{state.gender}</Descriptions.Item>
						<Descriptions.Item label="年龄">{state.age}</Descriptions.Item>
						<Descriptions.Item label="底薪">{'￥' + state.baseSalary}</Descriptions.Item>
						<Descriptions.Item label="总薪">{'￥' + (state.baseSalary + (distance > 20000 ? 500 : 0))}</Descriptions.Item>
						<Descriptions.Item label="地址">{locationName}</Descriptions.Item>
						<Descriptions.Item label="差旅补助">{distance / 1000 === 0 ? '...' : distance > 20000 ? '有' : '无'}</Descriptions.Item>
						<Descriptions.Item label="距离公司">{(distance / 1000 === 0 ? '...' : distance / 1000) + 'km'}</Descriptions.Item>
					</Descriptions>
				</Col>
			</Row>
			<Row>
				<Col span={24}>
					<div className={style.container} id='container' style={{ width: '100%', height: '450px', borderRadius: '5px' }}>

					</div>
				</Col>
			</Row>
		</div>
	)
}
