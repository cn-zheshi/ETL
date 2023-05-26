import { Button, Space, Table } from 'antd';
import { useState, useEffect } from 'react';
import { getSelectedCourseInfo } from '../api/info';
import { cancelCourse } from '../api/choose';

export default function SelfCourse() {
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
  },
  {
    title: '操作',
    key: 'action',
    render: (_, record) => (
      <Space size="middle">
        <Button type="default" onClick={
          () => {
            cancelCourse(record.courseNo, localStorage.getItem('学号')).then((res) => {
              if(res === 'success') {
                alert('退选成功');
                // 从data中删除该条数据
                const newData = [...data];
                const index = newData.findIndex((item) => record.key === item.key);
                newData.splice(index, 1);
                setData(newData);
              } else {
                alert('退选失败');
              }
            });
          }
        }> 退选 </Button>
      </Space>      
    ),
  },
];
const [data, setData] = useState([]);

useEffect(() => {
  getSelectedCourseInfo(localStorage.getItem('学号')).then((res) => {
    // 如果成绩是0，说明还没有成绩
    res.forEach((item, index) => {
      item.key = index;
      if(item.courseGrade === '0') {
        item.courseGrade = '暂无';
      }
    }
    );
    setData(res);
  });
}, [data.length]);


return <Table columns={columns} dataSource={data} />;
}