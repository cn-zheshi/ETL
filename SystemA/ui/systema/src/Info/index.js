// 学生信息: 学号，姓名，性别，院系，关联账户
import { Card, Button } from 'antd';
import { useEffect, useState } from 'react';
import "./info.css"; 
import { getStudentInfo } from "../api/info";
import { useNavigate } from "react-router-dom";

export default function Info() { 
  const navigate = useNavigate();
  const [id, setId] = useState('');
  const [name, setName] = useState('');
  const [gender, setGender] = useState('');
  const [department, setDepartment] = useState('');
  const [account, setAccount] = useState('');

  useEffect(() => {
    getStudentInfo(localStorage.getItem('用户名')).then(res => {
      if(res !== "fail") {
        setId(res.studentNo);
        localStorage.setItem('学号', res.studentNo);
        setName(res.studentName);
        setGender(res.studentGender);
        setDepartment(res.studentDepartment);
        setAccount(res.studentAccount);
      }
    })
  }, [name]);

  function toChooseCourse() {
    navigate("/chooseCourse");
  }

  function toDeleteCourse() {
    navigate("/deleteCourse");
  }


  return <div>
    <Card title="学生信息" className='infoCard'>
      <p>学号：{id}</p>
      <p>姓名：{name}</p>
      <p>性别：{gender}</p>
      <p>院系：{department}</p>
      <p>关联账户：{account}</p>
    </Card>
    <div className='buttonGroup'>
      <Button type="primary" onClick={toChooseCourse}>选课</Button>
      <Button type="primary" onClick={toDeleteCourse} danger>退选</Button>
    </div>
  </div>
}