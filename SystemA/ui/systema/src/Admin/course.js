import { Table, Tabs, Space, Button } from "antd"
import { useState, useEffect } from "react"
import { getSelfCourseInfo, getOtherCourseInfo } from "../api/info"
import { openCourseShare, openOtherCourseShare } from "../api/course"

export default function Course() {
    const items = [
    {
      key: '1',
      label: '院系A',
      children: <ACourse />
    },
    {
      key: '2',
      label: '院系B',
      children: <BCourse />
    },
    {
      key: '3',
      label: '院系C',
      children: <CCourse />
    }
  ]
  return <Tabs className='courseTabs' defaultActiveKey="1" items={items} />
}

const ACourse = () => {
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
        {
          record.courseShare === '1' ? <Button type="default" onClick={
            () => {
              openCourseShare(record.courseNo).then((res) => {
                if (res === 'success') {
                  alert('取消共享成功');
                  // 把courseShare改为1
                  record.courseShare = '1';
                  setTrigger(!trigger);
                } else {
                  alert('取消共享失败');
                }
              });
            } }> 取消共享 </Button> : <Button type="default" onClick={
            () => {
              openCourseShare(record.courseNo).then((res) => {
                if (res === 'success') {
                  alert('开放共享成功');
                  // 把courseShare改为1
                  record.courseShare = '1';
                  setTrigger(!trigger);
                } else {
                  alert('开放共享失败');
                }
              });
            } }> 开放共享 </Button>
        }
      </Space>      
    ),
  },
];
const [data, setData] = useState([]);
const [trigger, setTrigger] = useState(false);

useEffect(() => {
  getSelfCourseInfo().then((res) => {
    res.forEach((item, index) => {
      item.key = index;
    });
    setData(res);
  });
}, [data.length, trigger]);

  return <Table columns={columns} dataSource={data} />;
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
        {
          record.courseShare === '1' ? <Button type="default" onClick={
            () => {
              openOtherCourseShare('A', 'B', record.courseNo).then((res) => {
                if (res === 'success') {
                  alert('取消共享成功');
                  // 把courseShare改为1
                  record.courseShare = '1';
                  setTrigger(!trigger);
                } else {
                  alert('取消共享失败');
                }
              });
            } }> 取消共享 </Button> : <Button type="default" onClick={
            () => {
              openOtherCourseShare('A', 'B', record.courseNo).then((res) => {
                if (res === 'success') {
                  alert('开放共享成功');
                  // 把courseShare改为1
                  record.courseShare = '1';
                  setTrigger(!trigger);
                } else {
                  alert('开放共享失败');
                }
              });
            } }> 开放共享 </Button>
        }
      </Space>      
    ),
  },
  ];
  const [data, setData] = useState([]);
  const [trigger, setTrigger] = useState(false);
  useEffect(() => {
    getOtherCourseInfo('A', 'B', '20').then((res) => {
      res.forEach((item, index) => {
        item.key = index;
      });
      setData(res);
    });
  }, [data.length, trigger]);

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
        {
          record.courseShare === '1' ? <Button type="default" onClick={
            () => {
              openOtherCourseShare('A', 'C', record.courseNo).then((res) => {
                if (res === 'success') {
                  alert('取消共享成功');
                  // 把courseShare改为1
                  record.courseShare = '1';
                  setTrigger(!trigger);
                } else {
                  alert('取消共享失败');
                }
              });
            } }> 取消共享 </Button> : <Button type="default" onClick={
            () => {
              openOtherCourseShare('A', 'C', record.courseNo).then((res) => {
                if (res === 'success') {
                  alert('开放共享成功');
                  // 把courseShare改为1
                  record.courseShare = '1';
                  setTrigger(!trigger);
                } else {
                  alert('开放共享失败');
                }
              });
            } }> 开放共享 </Button>
        }
      </Space>      
    ),
  },
  ];
  const [data, setData] = useState([]);
  const [trigger, setTrigger] = useState(false);
  useEffect(() => {
    getOtherCourseInfo('A', 'C', '20').then((res) => {
      res.forEach((item, index) => {
        item.key = index;
      });
      setData(res);
    });
  }, [data.length, trigger]);
  return <Table columns={columns} dataSource={data} />;
}