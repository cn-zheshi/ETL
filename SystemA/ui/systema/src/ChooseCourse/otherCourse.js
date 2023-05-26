import { Space, Table, Button, Tabs } from 'antd';
import { useEffect, useState } from 'react';
import { getOtherCourseInfo } from '../api/info';
import { chooseOtherCourse } from '../api/choose';
export default function OtherCourse() {
  const items = [
    {
      key: '1',
      label: '院系B',
      children: <BCourse />
    },
    {
      key: '2',
      label: '院系C',
      children: <CCourse />
    }
  ]
  return <Tabs className='courseTabs' defaultActiveKey="1" items={items} />
}

const BCourse = () => {
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
            if (record.courseShare === '0') {
              alert('该课程不可选');
              return;
            }
            chooseOtherCourse('A', 'B', localStorage.getItem('学号'), record.courseNo).then(res => {
              console.log(res);
              if (res === 'success') {
                alert('选课成功');
              } else {
                alert('选课失败');
              }
            })
          }
        }> 选课 </Button>
      </Space>      
    ),
  },
];
const [data, setData] = useState([]);

useEffect(() => {
  getOtherCourseInfo('A', 'B', localStorage.getItem('学号')).then(res => {
    res.forEach((item, index) => {
      item.key = index;
    });
    setData(res);
  })
}, [data.length])

return <Table columns={columns} dataSource={data} />;
}

const CCourse = () => {
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
            if (record.courseShare === '0') {
              alert('该课程不可选');
              return;
            }
            chooseOtherCourse('A', 'C', localStorage.getItem('学号'), record.courseNo).then(res => {
              if (res === 'success') {
                alert('选课成功');
              } else {
                alert('选课失败');
              }
            })
          }
        }> 选课 </Button>
      </Space>      
    ),
  },
];
const [data, setData] = useState([]);

useEffect(() => {
  getOtherCourseInfo('A', 'C', localStorage.getItem('学号')).then(res => {
    res.forEach((item, index) => {
      item.key = index;
    });
    setData(res);
  })
}, [data.length])

return <Table columns={columns} dataSource={data} />;
}