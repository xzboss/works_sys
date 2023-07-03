import React, { useState, useEffect } from 'react'
import { useRoutes, useNavigate } from 'react-router-dom'
import { Row, Col, Button } from 'antd'

import routes from './router'
import './App.css'
//import { Map, Marker, NavigationControl, InfoWindow } from 'react-bmapgl';
export default function App () {
  const elements = useRoutes(routes)
  const style = {
    margin: '80px auto 0px auto',
  }

  const navigate = useNavigate()
  const exit = () => {
    navigate('/login')
    localStorage.removeItem('token')
    sessionStorage.removeItem('isLogin')
  }

  useEffect(() => {

  }, [])
  return (
    <div style={style}>
      <Row justify='center'>
        <Col xs={24} sm={24} md={20} lg={16} xl={12} >
          {elements}
        </Col>
      </Row>
      <div className='exit'>
        <Button size='large'
          onClick={exit}
          danger
          type="text"
          children={(/.*(register)|(login)$/).test(window.location) ? '' : '退出'}></Button>
      </div>
    </div>
  )
}

