// 管理员界面
import { Tabs } from "antd";
import './admin.css';
import Student from "./student";
import Course from "./course";
import Grade from "./grade";

export default function Admin() {
    const items = [
    {
      key: '1',
      label: '学生信息',
      children: <Student />
    },
    {
      key: '2',
      label: '课程信息',
      children: <Course />
    },
    {
      key: '3',
      label: '选课赋分',
      children: <Grade />
    }
  ]
  return <div className="adminPage">
    <h2>管理员界面</h2>
  <Tabs className='adminTabs' defaultActiveKey="1" items={items} />
  </div>;
}