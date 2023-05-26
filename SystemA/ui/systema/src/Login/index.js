import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { Button, Form, Input, Select, Space } from "antd";
import "./login.css"; 
import { studentLogin, adminLogin } from "../api/login";
import { useNavigate } from "react-router-dom"; 


export default function Login() {
  const navigate = useNavigate();

  const onFinish = (values) => {
    const { username, password, role } = values;
    if(role === 'student') {
      studentLogin(username, password).then(res => {
      if (res === 'success') {
        localStorage.setItem('用户名', username);
        localStorage.setItem('身份', role);
        navigate("/info");
      }
      })
    } else {
      adminLogin(username, password).then(res => {
        if (res === 'success') {
            localStorage.setItem('用户名', username);
            localStorage.setItem('身份', role);
            navigate("/admin");
        }
      })
    }
  };

  return (
    <div className={"login-page-container"}>
    <i className={"mask"}></i>
      <div className={"login-wrapper"}>
        <h2>A院系教务系统</h2>
        <Form name="normal_login" className="login-form" onFinish={onFinish}>
          <Form.Item
            name="username"
            rules={[
              {
                required: true,
                message: "请输入用户名!",
              },
            ]}
          >
            <Input
              prefix={<UserOutlined className="site-form-item-icon" />}
              placeholder="请输入用户名"
            />
          </Form.Item>
          <Form.Item
            name="password"
            rules={[
              {
                required: true,
                message: "请输入密码",
              },
            ]}
          >
            <Input
              prefix={<LockOutlined className="site-form-item-icon" />}
              type="password"
              placeholder="请输入密码"
            />
          </Form.Item>
          {/* 选择角色：学生或者管理员 */}
          <Form.Item
            name="role"
            rules={[
              {
                required: true,
                message: "请选择角色",
              },
            ]}
          >
            <Select
              placeholder="请选择角色"
            >
              <Select.Option value="student">学生</Select.Option>
              <Select.Option value="admin">管理员</Select.Option>
            </Select>
          </Form.Item>

          <Form.Item>
            <Space size="large" style={{width: '100%',justifyContent: 'center'}}>
              <Button
                type="primary"
                htmlType="submit"
                className="login-form-button"
              >
                登录
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
}
