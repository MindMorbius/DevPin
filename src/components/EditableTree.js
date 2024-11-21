'use client';  // 确保这个文件是客户端组件

import React, { useState } from 'react';
import { Button, Input, Modal, Tree, Form, Popconfirm } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, SaveOutlined } from '@ant-design/icons';
import { useRouter } from 'next/navigation'  

const { TextArea } = Input;

const EditableTree = () => {


  const router = useRouter();

  const [treeData, setTreeData] = useState([
    {
      title: 'Root Node 1',
      key: '0-0',
      children: [
        { title: 'Child Node 1', key: '0-0-0', children: [] },
        { title: 'Child Node 2', key: '0-0-1', children: [] },
      ],
    },
    {
      title: 'Root Node 2',
      key: '0-1',
      children: [
        { title: 'Child Node 3', key: '0-1-0', children: [] },
      ],
    },
  ]);
  const [editingKey, setEditingKey] = useState(null);
  const [newNodeTitle, setNewNodeTitle] = useState('');
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedNode, setSelectedNode] = useState(null);

  const onEditNode = (key, title) => {
    setEditingKey(key);
    setNewNodeTitle(title);
  };

  const onSaveNode = () => {
    const updatedTreeData = [...treeData];
    const node = findNodeByKey(updatedTreeData, editingKey);
    if (node) {
      node.title = newNodeTitle;
      setTreeData(updatedTreeData);
    }
    setEditingKey(null);
    setNewNodeTitle('');
  };

  const findNodeByKey = (tree, key) => {
    for (let node of tree) {
      if (node.key === key) {
        return node;
      }
      if (node.children && node.children.length) {
        const result = findNodeByKey(node.children, key);
        if (result) return result;
      }
    }
    return null;
  };

  const onAddNode = (parentNodeKey) => {
    const updatedTreeData = [...treeData];
    const newNode = {
      title: 'New Node',
      key: `${parentNodeKey}-${Date.now()}`,
      children: [],
    };
    const parentNode = findNodeByKey(updatedTreeData, parentNodeKey);
    if (parentNode) {
      parentNode.children.push(newNode);
      setTreeData(updatedTreeData);
    }
  };

  const onDeleteNode = (key) => {
    const updatedTreeData = deleteNodeByKey([...treeData], key);
    setTreeData(updatedTreeData);
  };

  const deleteNodeByKey = (tree, key) => {
    return tree.filter(node => {
      if (node.key === key) return false;
      if (node.children && node.children.length) {
        node.children = deleteNodeByKey(node.children, key);
      }
      return true;
    });
  };

  const onSelectNode = (selectedKeys, { node }) => {
    setSelectedNode(node);
  };

  const goArt = () => {
    router.push('/art')
  };

  return (
    <div>
      <Tree
        treeData={treeData}
        onSelect={onSelectNode}
        titleRender={(nodeData) => (
          <div>
            {editingKey === nodeData.key ? (
              <Input
                value={newNodeTitle}
                onChange={(e) => setNewNodeTitle(e.target.value)}
                onBlur={onSaveNode}
                autoFocus
              />
            ) : (
              <span>
                <div style={{display:'inline'}} onClick={() => goArt()}>{nodeData.title}</div>
                <EditOutlined onClick={() => onEditNode(nodeData.key, nodeData.title)} />
                <PlusOutlined
                  style={{ marginLeft: 8 }}
                  onClick={() => onAddNode(nodeData.key)}
                />
                <Popconfirm
                  title="Are you sure to delete this node?"
                  onConfirm={() => onDeleteNode(nodeData.key)}
                  okText="Yes"
                  cancelText="No"
                >
                  <DeleteOutlined style={{ marginLeft: 8 }} />
                </Popconfirm>
              </span>
            )}
          </div>
        )}
        selectable
        draggable
      />
      {selectedNode && (
        <Modal
          title="Add a New Child Node"
          visible={isModalVisible}
          onCancel={() => setIsModalVisible(false)}
          onOk={() => onAddNode(selectedNode.key)}
        >
          <Form>
            <Form.Item label="Node Title">
              <Input
                value={newNodeTitle}
                onChange={(e) => setNewNodeTitle(e.target.value)}
              />
            </Form.Item>
          </Form>
        </Modal>
      )}
    </div>
  );
};

export default EditableTree;
