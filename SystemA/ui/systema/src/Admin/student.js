import { Tabs, Table } from "antd";
import { useEffect, useState } from "react";
import { getSelfAllStudentInfo } from "../api/student";
import { getOtherAllStudentInfo } from "../api/info";

export default function Student() {
  const items = [
    {
      key: '1',
      label: '院系A',
      children: <AStudent />
    },
    {
      key: '2',
      label: '院系B',
      children: <BStudent />
    },
    {
      key: '3',
      label: '院系C',
      children: <CStudent />
    }
  ]
  return <Tabs className='studentTabs' defaultActiveKey="1" items={items} />
}

const AStudent = () => {
  // 获取本院系学生信息
  const [data, setData] = useState([]);
  useEffect(() => {
    getSelfAllStudentInfo().then(res => {
      res.forEach((item, index) => {
        item.key = index;
      })
      setData(res);
    })
  }, [data.length])
  // 学号, 姓名, 性别, 院系, 关联账户
  const columns = [
    {
      title: '学号',
      dataIndex: 'studentNo',
      key: 'studentNo',
    },
    {
      title: '姓名',
      dataIndex: 'studentName',
      key: 'studentName',
    },
    {
      title: '性别',
      dataIndex: 'studentSex',
      key: 'studentSex',
    },
    {
      title: '院系',
      dataIndex: 'studentDepartment',
      key: 'studentDepartment',
    },
    {
      title: '关联账户',
      dataIndex: 'studentAccount',
      key: 'studentAccount',
    }
  ];
  return <Table dataSource={data} columns={columns} />;
}

const BStudent = () => {
  const [data, setData] = useState([]);
  // 学号，姓名，性别，院系
  const columns = [
    {
      title: '学号',
      dataIndex: 'studentNo',
      key: 'studentNo',
    },
    {
      title: '姓名',
      dataIndex: 'studentName',
      key: 'studentName',
    },
    {
      title: '性别',
      dataIndex: 'studentSex',
      key: 'studentSex',
    },
    {
      title: '院系',
      dataIndex: 'studentDepartment',
      key: 'studentDepartment',
    }
  ];

  useEffect(() => {
    getOtherAllStudentInfo('A', 'B').then(res => {
      res.forEach((item, index) => {
        item.key = index;
      })
      setData(res);
    })
  }, [data.length])
  return <Table dataSource={data} columns={columns} />
}

const CStudent = () => {
    const [data, setData] = useState([]);
  // 学号，姓名，性别，院系
  const columns = [
    {
      title: '学号',
      dataIndex: 'studentNo',
      key: 'studentNo',
    },
    {
      title: '姓名',
      dataIndex: 'studentName',
      key: 'studentName',
    },
    {
      title: '性别',
      dataIndex: 'studentSex',
      key: 'studentSex',
    },
    {
      title: '院系',
      dataIndex: 'studentDepartment',
      key: 'studentDepartment',
    }
  ];

  useEffect(() => {
    getOtherAllStudentInfo('A', 'C').then(res => {
      res.forEach((item, index) => {
        item.key = index;
      })
      setData(res);
    })
  }, [data.length])
  return <Table dataSource={data} columns={columns} />
}