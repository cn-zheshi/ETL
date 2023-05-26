import React from 'react';
import 'antd/dist/reset.css';
import './App.css';
import Login from './Login'
import Info from './Info'
import ChooseCourse from './ChooseCourse';
import DeleteCourse from './DeleteCourse';
import Admin from './Admin';
import GiveGrade from './GiveGrade';
import { BrowserRouter, Route, Routes } from "react-router-dom"

function App() {
 return <BrowserRouter>
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/info" element={<Info />} />
      <Route path="/chooseCourse" element={<ChooseCourse />} />
      <Route path="/deleteCourse" element={<DeleteCourse />} />
      <Route path="/admin" element={<Admin />} />
      <Route path="/giveGrade" element={<GiveGrade />} />
    </Routes>
  </BrowserRouter>

}

export default App;
