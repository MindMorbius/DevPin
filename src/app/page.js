'use client';  // 这里标记该组件为客户端组件

import React, { useState } from 'react';
import { Button, Form, Input, List, Modal } from 'antd';
import EditableTree from '@/components/EditableTree'
import { useRouter } from 'next/navigation'  

const Home = () => {




  const router = useRouter();
  const [messages, setMessages] = useState([]);
  const [form] = Form.useForm();
  const [isModalVisible, setIsModalVisible] = useState(false);

  const onFinish = (values) => {
    const newMessage = {
      id: messages.length + 1,
      content: values.message,
      date: new Date().toLocaleString(),
    };
    setMessages([newMessage, ...messages]);
    form.resetFields();
    setIsModalVisible(false);
  };

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const goLogin = () => {
    router.push('/login')
  };

  return (
    <div style={{ padding: '20px' }}>

      

      <Button type="primary" onClick={showModal} style={{ marginBottom: '20px' }}>
        新想法
      </Button>
      
      <Button type="primary" onClick={goLogin} style={{ marginBottom: '20px',marginLeft: '20px' }}>
        登录/注册
      </Button>

      <Modal
        title="输入具体的细节"
        visible={isModalVisible}
        onCancel={handleCancel}
        footer={null}
        destroyOnClose
      >
        <Form form={form} onFinish={onFinish}>
          <Form.Item
            name="message"
            rules={[{ required: true, message: '请输入' }]}
          >
            <Input.TextArea style={{ marginTop: 8 }} rows={4} placeholder="解决问题-不评论-知行合一" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" style={{ width: '100%' }}>
              开始分解
            </Button>
          </Form.Item>
        </Form>
      </Modal>

     
      <List
        header={<div>点子池</div>}
        bordered
        dataSource={messages}
        renderItem={item => (
          <List.Item key={item.id}>
            <div>
              <p><strong>{item.content}</strong></p>
              <EditableTree/>
              <Button type="primary" onClick={showModal} style={{ marginTop: '18px', marginLeft: 18 }}>
                分享这个想法
              </Button>
              <p style={{ marginTop: '20px' }}><small>{item.date}</small></p>
            </div>
          </List.Item>
        )}
      />
    </div>
  );
};

export default Home;
