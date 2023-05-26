import { useNavigate } from "react-router";
import { Button, Tabs } from 'antd';
import SelfCourse from "./selfCourse";
import OtherCourse from "./otherCourse";
import './deleteCourse.css';

export default function DeleteCourse() {
  const navigate = useNavigate();
  const toInfo = () => {
    navigate('/info');
  }
  const items = [
    {
      key: '1',
      label: '本院系已选课程',
      children: <SelfCourse />
    },
    {
      key: '2',
      label: '跨院系已选课程',
      children: <OtherCourse />
    }
  ]
  return <div className="deleteCoursePage">
    <h2>退选课程</h2>
    <Tabs className='deleteTabs' defaultActiveKey="1" items={items} />
    <Button type="primary" className='returnButton' onClick={toInfo}>返回</Button>
  </div>
}