import { Button, Space, Table, Tabs } from 'antd';
import { useEffect, useState } from 'react';
import { getOtherSelectedCourseInfo } from '../api/info';
import { cancelOtherCourse } from '../api/choose';

export default function OtherCourse() {
  const items = [
    {
      key: '1',
      label: '院系B课程',
      children: <BCourse />
    },
    {
      key: '2',
      label: '院系C课程',
      children: <CCourse />
    }
  ]
  return <Tabs className='courseTabs' defaultActiveKey="1" items={items} />
}

const BCourse = () => {
  // "课程编号", "课程名称", "学分", "授课老师", "授课地点", "成绩"
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
    render: (text) => text === '0' ? '暂无' : text,
  },
  {
    title: '操作',
    key: 'action',
    render: (_, record) => (
      <Space size="middle">
        <Button type="default" onClick={
          () => {
            cancelOtherCourse('A', 'B', localStorage.getItem('学号'), record.courseNo).then(res => {
              if (res === 'success') {
                alert('退选成功');
                setData(data.filter(item => item.courseNo !== record.courseNo));
              } else {
                alert('退选失败');
              }
            })
          }
        }> 退选 </Button>
      </Space>      
    ),
  },
];
const [data, setData] = useState([]);
useEffect(() => {
  getOtherSelectedCourseInfo('A', 'B', localStorage.getItem('学号')).then(res => {
    res.forEach((item, index) => {
      item.key = index;
    })
    setData(res);
  })
}, [data.length])

return <Table columns={columns} dataSource={data} />;
}

const CCourse = () => {
  // "课程编号", "课程名称", "学分", "授课老师", "授课地点", "成绩"
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
    render: (text) => text === '0' ? '暂无' : text,
  },
  {
    title: '操作',
    key: 'action',
    render: (_, record) => (
      <Space size="middle">
        <Button type="default" onClick={
          () => {
            cancelOtherCourse('A', 'C', localStorage.getItem('学号'), record.courseNo).then(res => {
              if (res === 'success') {
                alert('退选成功');
                setData(data.filter(item => item.courseNo !== record.courseNo));
              } else {
                alert('退选失败');
              }
            })
          }
        }> 退选 </Button>
      </Space>      
    ),
  },
];

const [data, setData] = useState([]);

useEffect(() => {
  getOtherSelectedCourseInfo('A', 'C', localStorage.getItem('学号')).then(res => { 
    res.forEach((item, index) => {
      item.key = index;
    })
    setData(res);
  })
}, [data.length])

return <Table columns={columns} dataSource={data} />;
}
