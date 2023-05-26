import { Button, Tabs } from 'antd';
import './chooseCourse.css';
import SelfCourse from './selfCourse';
import OtherCourse from './otherCourse';
import { useNavigate } from 'react-router';


export default function ChooseCourse() {
  const navigate = useNavigate();
  const toInfo = () => {
    navigate('/info');
  }
  const items = [
    {
      key: '1',
      label: '本院系课程',
      children: <SelfCourse />
    },
    {
      key: '2',
      label: '跨院系课程',
      children: <OtherCourse />
    },
  ]
  return <div className="chooseCoursePage">
    <h2>选择课程</h2>
    <Tabs className='chooseTabs' defaultActiveKey="1" items={items} />
    <Button type="primary" className='returnButton' onClick={toInfo}>返回</Button>
  </div>
}