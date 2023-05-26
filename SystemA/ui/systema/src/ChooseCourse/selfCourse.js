import { Button, Space, Table } from 'antd';
import { getSelfCourseInfo } from '../api/info';
import { useState, useEffect } from 'react';
import { chooseCourse } from '../api/choose';

export default function SelfCourse() {
// 课程编号、课程名称、学分、授课老师、授课地点、共享、操作
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
    title: '是否共享',
    key: 'courseShare',
    dataIndex: 'courseShare',
    render: (text) => text === '1' ? '是' : '否',
  },
  {
    title: '操作',
    key: 'action',
    render: (_, record) => (
      <Space size="middle">
        <Button type="default" onClick={
          () => {
            chooseCourse(record.courseNo, localStorage.getItem('学号')).then((res) => {
              if(res === 'success') {
                alert('选课成功！');
              } else {
                alert('选课失败！');
              }
            });
          }
        }> 选课 </Button>
      </Space>      
    ),
  },
];

const [data, setData] = useState([]);

useEffect(() => {
  getSelfCourseInfo().then((res) => {
    res.forEach((item, index) => {
      item.key = index;
    });
    setData(res);
  });
}, [data.length]);


return <Table columns={columns} dataSource={data} />;
}