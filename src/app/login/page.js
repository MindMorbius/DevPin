'use client';  // 这里标记该组件为客户端组件

import React, { useState } from 'react';
import { Button, Form, Input, Tabs, message } from 'antd';

const LoginRegister = () => {
  const [activeTab, setActiveTab] = useState('login'); // 默认选择登录tab
  const [form] = Form.useForm();

  // 登录表单提交
  const onLoginFinish = (values) => {
    // 这里只是模拟成功登录的情况
    message.success('登录成功');
    form.resetFields();
  };

  // 注册表单提交
  const onRegisterFinish = (values) => {
    // 这里只是模拟成功注册的情况
    message.success('注册成功');
    form.resetFields();
  };

  const handleTabChange = (key) => {
    setActiveTab(key);
  };

  const goHome = () => {
    document.location.href = '/';
  };

  return (
    <div style={styles.container}>

     

      <div style={styles.formWrapper}>
        <Tabs activeKey={activeTab} onChange={handleTabChange} centered>
          <Tabs.TabPane tab="登录" key="login">
            <Form form={form} onFinish={onLoginFinish}>
              <Form.Item
                name="username"
                rules={[{ required: true, message: '请输入用户名' }]}
              >
                <Input placeholder="请输入用户名" />
              </Form.Item>
              <Form.Item
                name="password"
                rules={[{ required: true, message: '请输入密码' }]}
              >
                <Input.Password placeholder="请输入密码" />
              </Form.Item>
              <Form.Item>
                <Button type="primary" htmlType="submit" block>
                  登录
                </Button>
                <Button onClick={goHome} style={{ marginTop: 8 }} type="primary" htmlType="submit" block>
                  返回主页
                </Button>
              </Form.Item>
            </Form>
          </Tabs.TabPane>
          <Tabs.TabPane tab="注册" key="register">
            <Form form={form} onFinish={onRegisterFinish}>
              <Form.Item
                name="username"
                rules={[{ required: true, message: '请输入用户名' }]}
              >
                <Input placeholder="请输入用户名" />
              </Form.Item>
              <Form.Item
                name="email"
                rules={[{ required: true, message: '请输入邮箱' }, { type: 'email', message: '请输入有效的邮箱' }]}
              >
                <Input placeholder="请输入邮箱" />
              </Form.Item>
              <Form.Item
                name="password"
                rules={[{ required: true, message: '请输入密码' }]}
              >
                <Input.Password placeholder="请输入密码" />
              </Form.Item>
              <Form.Item
                name="confirmPassword"
                dependencies={['password']}
                rules={[
                  { required: true, message: '请确认密码' },
                  ({ getFieldValue }) => ({
                    validator(_, value) {
                      if (!value || getFieldValue('password') === value) {
                        return Promise.resolve();
                      }
                      return Promise.reject(new Error('两次输入的密码不一致'));
                    },
                  }),
                ]}
              >
                <Input.Password placeholder="确认密码" />
              </Form.Item>
              <Form.Item>
                <Button type="primary" htmlType="submit" block>
                  注册
                </Button>
                <Button onClick={goHome} style={{ marginTop: 8 }} type="primary" htmlType="submit" block>
                  返回主页
                </Button>
              </Form.Item>
            </Form>
          </Tabs.TabPane>
        </Tabs>
      </div>
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100vh',  // 居中整个视窗
    backgroundColor: '#f0f2f5',  // 背景色，可以根据需求调整
  },
  formWrapper: {
    width: '400px',  // 设置表单的最大宽度
    backgroundColor: '#fff',
    borderRadius: '8px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',  // 添加阴影使表单更突出
    padding: '20px',
    boxSizing: 'border-box',  // 保证padding不影响宽度计算
  },
};

export default LoginRegister;
