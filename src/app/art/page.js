'use client';  // 这里标记该组件为客户端组件

import React, { useState } from 'react';
import { Button, Card, Form, Input, message, Modal } from 'antd';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';  // 导入富文本编辑器的样式

const PostDetailPage = () => {
  const [comments, setComments] = useState([]);  // 存储评论
  const [commentContent, setCommentContent] = useState('');  // 当前评论内容
  const [showModal, setShowModal] = useState(false);  // 控制富文本编辑框的弹出
  const [loading, setLoading] = useState(false);  // 控制提交评论的加载状态

  // 模拟获取帖子详情数据
  const post = {
    title: '这是一个帖子标题',
    content: '这是帖子内容，这里可以包含文本、图片等。',
    author: '作者名称',
    createdAt: '2024-11-14',
  };

  // 提交评论
  const handleSubmitComment = () => {
    if (!commentContent) {
      message.error('评论内容不能为空');
      return;
    }

    setLoading(true);
    setTimeout(() => {
      // 模拟提交评论
      setComments([...comments, { content: commentContent, author: '当前用户', createdAt: new Date().toLocaleString() }]);
      setCommentContent('');
      setLoading(false);
      message.success('评论成功');
    }, 1000);
  };

  // 富文本编辑器变化处理
  const handleEditorChange = (value) => {
    setCommentContent(value);
  };

  return (
    <div style={styles.container}>
      {/* 帖子详情 */}
      <Card title={post.title} bordered={false} style={styles.card}>
        <p>{post.content}</p>
        <p>作者：{post.author}</p>
        <p>发布于：{post.createdAt}</p>
      </Card>

      {/* 评论区 */}
      <div style={styles.commentSection}>
        <h3>评论区</h3>
        <div style={styles.commentList}>
          {comments.length === 0 ? (
            <p>暂无评论</p>
          ) : (
            comments.map((comment, index) => (
              <Card key={index} style={styles.commentCard}>
                <p><strong>{comment.author}</strong> 发表于 {comment.createdAt}</p>
                <div dangerouslySetInnerHTML={{ __html: comment.content }} />
              </Card>
            ))
          )}
        </div>

        {/* 富文本评论框 */}
        <Button type="primary" onClick={() => setShowModal(true)} style={{ marginBottom: 16 }}>
          撰写评论
        </Button>

        <Modal
          title="撰写评论"
          visible={showModal}
          onCancel={() => setShowModal(false)}
          footer={null}
        >
          <ReactQuill
            value={commentContent}
            onChange={handleEditorChange}
            theme="snow"
            placeholder="在这里输入评论..."
          />
          <div style={{ marginTop: 16, textAlign: 'right' }}>
            <Button onClick={() => setShowModal(false)} style={{ marginRight: 8 }}>
              取消
            </Button>
            <Button
              type="primary"
              loading={loading}
              onClick={handleSubmitComment}
            >
              提交评论
            </Button>
          </div>
        </Modal>
      </div>
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '20px',
    backgroundColor: '#f7f7f7',
  },
  card: {
    width: '80%',
    marginBottom: '20px',
  },
  commentSection: {
    width: '80%',
    backgroundColor: '#fff',
    padding: '20px',
    borderRadius: '8px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
  },
  commentList: {
    marginBottom: '16px',
  },
  commentCard: {
    marginBottom: '12px',
  },
};

export default PostDetailPage;
