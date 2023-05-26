// 学生给分
import { Card, Space, Button, Table, Modal, InputNumber, Form } from 'antd';
import { useLocation } from 'react-router-dom';
import './givegrade.css';
import { useState, useEffect } from 'react';
import { getSelfAllStudentInfo } from '../api/student';
import { getSelectedCourseInfo, getOtherAllStudentInfo, getOtherSelectedCourseInfo } from '../api/info';
import { changeCourseScore, changeOtherCourseScore } from '../api/course';
import { useNavigate } from 'react-router-dom';

export default function GiveGrade() {
  // 获取navigate传递的信息
  const navigate = useNavigate();
  const { state } = useLocation();
  const toAdmin= () => {
    navigate('/admin');
  }
  return <div className='giveGradePage'>
    <h2>学生给分</h2>
    {
      state.department === 'A' ? <APage /> :
      state.department === 'B' ? <BPage /> :
      state.department === 'C' ? <CPage /> : null
    }
    <Button type="primary" className='returnButton' onClick={toAdmin}>返回</Button>
  </div>
}

const studentInfo = (studentNo, studentName, studentGender, studentDepartment) => { 
  return <Card title="学生信息" className='studentCard'>
      <p>学号：{studentNo}</p>
      <p>姓名：{studentName}</p>
      <p>性别：{studentGender}</p>
      <p>院系：{studentDepartment}</p>
    </Card>
}

const APage = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    if (courseDepartment === 'A') {
      changeCourseScore(
          courseNo, studentNo, score
        ).then(res => {
          if(res === "success") {
            alert("修改成功");
            setIsModalOpen(false);
            setTrigger(!trigger);
          } else {
            alert("修改失败");
          }
        })
    } else if (courseDepartment === 'B') {
      changeOtherCourseScore('A', 'B', courseNo, studentNo, score).then(res => {
        if(res === "success") {
          alert("修改成功");
          setIsModalOpen(false);
          setTrigger(!trigger);
        } else {
          alert("修改失败");
        }
      })
    } else if (courseDepartment === 'C') {
      changeOtherCourseScore('A', 'C', courseNo, studentNo, score).then(res => {
        if(res === "success") {
          alert("修改成功");
          setIsModalOpen(false);
          setTrigger(!trigger);
        } else {
          alert("修改失败");
        }
      })
    }
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };
  const [studentNo, setStudentNo] = useState('');
  const [studentName, setStudentName] = useState('');
  const [studentGender, setStudentGender] = useState('');
  const [studentDepartment, setStudentDepartment] = useState('');
  const { state } = useLocation();
  useEffect(() => {
    getSelfAllStudentInfo().then(res => {
      res.forEach(item => {
        if(item.studentNo === state.studentNo) {
          setStudentNo(item.studentNo);
          setStudentName(item.studentName);
          setStudentGender(item.studentSex);
          setStudentDepartment(item.studentDepartment);
        }
      })
    })
  }, [state.studentNo, studentName])

  // 选课表格
const columns = [
  {
    title: '课程编号',
    dataIndex: 'courseNo',
    key: 'courseNo',
  },
  {
    title: '课程名称',
    dataIndex: 'courseName',
    key: 'courseName',
  },
  {
    title: '学分',
    dataIndex: 'courseCredit',
    key: 'courseCredit',
  },
  {
    title: '授课老师',
    key: 'courseTeacher',
    dataIndex: 'courseTeacher',
  },
  {
    title: '授课地点',
    key: 'courseLocation',
    dataIndex: 'courseLocation',
  },
  {
    title: '成绩',
    key: 'courseGrade',
    dataIndex: 'courseGrade',
    render: (text) => (
      text === "0" ? "暂无" : text
    )
  },
  {
    title: '操作',
    key: 'action',
    render: (_, record) => (
      <Space size="middle">
        <Button type="default" onClick={
          () => {
            // 跳出弹窗
            setCourseNo(record.courseNo);
            if(record.courseNo.indexOf('A') !== -1) {
              setCourseDepartment('B');
            } else if(record.courseNo.indexOf('B') !== -1) {
              setCourseDepartment('C');
            } else if(record.courseNo.indexOf('C') !== -1) {
              setCourseDepartment('A');
            }
            showModal();
          }
        }> 修改分数 </Button>
      </Space>      
    ),
  },
];

const [data, setData] = useState([]);
const [ trigger, setTrigger ] = useState(false);

useEffect(() => {
  getSelectedCourseInfo(state.studentNo).then(res => {
    getOtherSelectedCourseInfo('A', 'B', state.studentNo).then(res1 => {
      getOtherSelectedCourseInfo('A', 'C', state.studentNo).then(res2 => {
        res = res.concat(res1);
        res = res.concat(res2);
        res.forEach((item, index) => {
          item.key = index;
        })
        setData(res);
    })
  })
  })
}, [state.studentNo, trigger])

const [score, setScore] = useState(60);
const [courseNo, setCourseNo] = useState('');
const [courseDepartment, setCourseDepartment] = useState('');

const onChange = (value) => {
  setScore(value);

};

  return <div>
    {studentInfo(studentNo, studentName, studentGender, studentDepartment)}
    <Table className='gradeTable' columns={columns} dataSource={data} />
    <Modal title="输入分数" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
      <Form>
          <InputNumber min={0} max={100} defaultValue={60} onChange={onChange}/>
      </Form>
    </Modal>
  </div>
}

const BPage = () => {
const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    if (courseDepartment === 'A') {
      changeCourseScore(
          courseNo, studentNo, score
        ).then(res => {
          if(res === "success") {
            alert("修改成功");
            setIsModalOpen(false);
            setTrigger(!trigger);
          } else {
            alert("修改失败");
          }
        })
    } else if (courseDepartment === 'B') {
      changeOtherCourseScore('A', 'B', courseNo, studentNo, score).then(res => {
        if(res === "success") {
          alert("修改成功");
          setIsModalOpen(false);
          setTrigger(!trigger);
        } else {
          alert("修改失败");
        }
      })
    } else if (courseDepartment === 'C') {
      changeOtherCourseScore('A', 'C', courseNo, studentNo, score).then(res => {
        if(res === "success") {
          alert("修改成功");
          setIsModalOpen(false);
          setTrigger(!trigger);
        } else {
          alert("修改失败");
        }
      })
    }
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

    const [studentNo, setStudentNo] = useState('');
  const [studentName, setStudentName] = useState('');
  const [studentGender, setStudentGender] = useState('');
  const [studentDepartment, setStudentDepartment] = useState('');
  const { state } = useLocation();
  useEffect(() => {
    getOtherAllStudentInfo('A', 'B').then(res => {
      res.forEach(item => {
        if(item.studentNo === state.studentNo) {
          setStudentNo(item.studentNo);
          setStudentName(item.studentName);
          setStudentGender(item.studentSex);
          setStudentDepartment(item.studentDepartment);
        }
      })
    })
  }, [state.studentNo, studentName])

  // 选课表格
const columns = [
  {
    title: '课程编号',
    dataIndex: 'courseNo',
    key: 'courseNo',
  },
  {
    title: '课程名称',
    dataIndex: 'courseName',
    key: 'courseName',
  },
  {
    title: '学分',
    dataIndex: 'courseCredit',
    key: 'courseCredit',
  },
  {
    title: '授课老师',
    key: 'courseTeacher',
    dataIndex: 'courseTeacher',
  },
  {
    title: '授课地点',
    key: 'courseLocation',
    dataIndex: 'courseLocation',
  },
  {
    title: '成绩',
    key: 'courseGrade',
    dataIndex: 'courseGrade',
    render: (text) => (
      text === "0" ? "暂无" : text
    )
  },
  {
    title: '操作',
    key: 'action',
    render: (_, record) => (
      <Space size="middle">
        <Button type="default" onClick={
          () => {
            setCourseNo(record.courseNo);
            if(record.courseNo.indexOf('A') !== -1) {
              setCourseDepartment('B');
            } else if(record.courseNo.indexOf('B') !== -1) {
              setCourseDepartment('C');
            } else if(record.courseNo.indexOf('C') !== -1) {
              setCourseDepartment('A');
            }
            showModal();
          }
        }> 修改分数 </Button>
      </Space>      
    ),
  },
];

const [data, setData] = useState([]);
const [ trigger, setTrigger ] = useState(false);

useEffect(() => {
  getSelectedCourseInfo(state.studentNo).then(res => {
    getOtherSelectedCourseInfo('A', 'B', state.studentNo).then(res1 => {
      getOtherSelectedCourseInfo('A', 'C', state.studentNo).then(res2 => {
        res === 'fail' ? res = [] : res = res;
        res1 === 'fail' ? res1 = [] : res1 = res1;
        res2 === 'fail' ? res2 = [] : res2 = res2;
        res = res.concat(res1);
        res = res.concat(res2);
        res.forEach((item, index) => {
          item.key = index;
        })
        setData(res);
    })
  })
  })
}, [state.studentNo, trigger])

const [score, setScore] = useState(60);
const [courseNo, setCourseNo] = useState('');
const [courseDepartment, setCourseDepartment] = useState('');

const onChange = (value) => {
  setScore(value);
};

  return <div>
    {studentInfo(studentNo, studentName, studentGender, studentDepartment)}
    <Table className='gradeTable' columns={columns} dataSource={data} />
    <Modal title="输入分数" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
      <Form>
          <InputNumber min={0} max={100} defaultValue={60} onChange={onChange}/>
      </Form>
    </Modal>
  </div>
}

const CPage = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    if (courseDepartment === 'A') {
      changeCourseScore(
          courseNo, studentNo, score
        ).then(res => {
          if(res === "success") {
            alert("修改成功");
            setIsModalOpen(false);
            setTrigger(!trigger);
          } else {
            alert("修改失败");
          }
        })
    } else if (courseDepartment === 'B') {
      changeOtherCourseScore('A', 'B', courseNo, studentNo, score).then(res => {
        if(res === "success") {
          alert("修改成功");
          setIsModalOpen(false);
          setTrigger(!trigger);
        } else {
          alert("修改失败");
        }
      })
    } else if (courseDepartment === 'C') {
      changeOtherCourseScore('A', 'C', courseNo, studentNo, score).then(res => {
        if(res === "success") {
          alert("修改成功");
          setIsModalOpen(false);
          setTrigger(!trigger);
        } else {
          alert("修改失败");
        }
      })
    }
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

    const [studentNo, setStudentNo] = useState('');
  const [studentName, setStudentName] = useState('');
  const [studentGender, setStudentGender] = useState('');
  const [studentDepartment, setStudentDepartment] = useState('');
  const { state } = useLocation();
  useEffect(() => {
    getOtherAllStudentInfo('A', 'C').then(res => {
      res.forEach(item => {
        if(item.studentNo === state.studentNo) {
          setStudentNo(item.studentNo);
          setStudentName(item.studentName);
          setStudentGender(item.studentSex);
          setStudentDepartment(item.studentDepartment);
        }
      })
    })
  }, [state.studentNo, studentName])

  // 选课表格
const columns = [
  {
    title: '课程编号',
    dataIndex: 'courseNo',
    key: 'courseNo',
  },
  {
    title: '课程名称',
    dataIndex: 'courseName',
    key: 'courseName',
  },
  {
    title: '学分',
    dataIndex: 'courseCredit',
    key: 'courseCredit',
  },
  {
    title: '授课老师',
    key: 'courseTeacher',
    dataIndex: 'courseTeacher',
  },
  {
    title: '授课地点',
    key: 'courseLocation',
    dataIndex: 'courseLocation',
  },
  {
    title: '成绩',
    key: 'courseGrade',
    dataIndex: 'courseGrade',
    render: (text) => (
      text === "0" ? "暂无" : text
    )
  },
  {
    title: '操作',
    key: 'action',
    render: (_, record) => (
      <Space size="middle">
        <Button type="default" onClick={
          () => {
            setCourseNo(record.courseNo);
            if(record.courseNo.indexOf('A') !== -1) {
              setCourseDepartment('B');
            } else if(record.courseNo.indexOf('B') !== -1) {
              setCourseDepartment('C');
            } else if(record.courseNo.indexOf('C') !== -1) {
              setCourseDepartment('A');
            }
            showModal();
          }
        }> 修改分数 </Button>
      </Space>      
    ),
  },
];

const [data, setData] = useState([]);
const [ trigger, setTrigger ] = useState(false);

useEffect(() => {
  getSelectedCourseInfo(state.studentNo).then(res => {
    getOtherSelectedCourseInfo('A', 'B', state.studentNo).then(res1 => {
      getOtherSelectedCourseInfo('A', 'C', state.studentNo).then(res2 => {
        res === 'fail' ? res = [] : res = res;
        res1 === 'fail' ? res1 = [] : res1 = res1;
        res2 === 'fail' ? res2 = [] : res2 = res2;
        res = res.concat(res1);
        res = res.concat(res2);
        res.forEach((item, index) => {
          item.key = index;
        })
        setData(res);
    })
  })
  })
}, [state.studentNo, trigger])

const [score, setScore] = useState(60);
const [courseNo, setCourseNo] = useState('');
const [courseDepartment, setCourseDepartment] = useState('');

const onChange = (value) => {
  setScore(value);
};

  return <div>
    {studentInfo(studentNo, studentName, studentGender, studentDepartment)}
    <Table className='gradeTable' columns={columns} dataSource={data} />
    <Modal title="输入分数" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
      <Form>
          <InputNumber min={0} max={100} defaultValue={60} onChange={onChange}/>
      </Form>
    </Modal>
  </div>
}