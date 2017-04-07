/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-11-18 11:07:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '排序（升序）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '正常', '0', 'del_flag', '删除标记', '10', '1', '2013-05-27 08:00:00', '2,超级管理员', '2015-02-28 23:07:13', null, '0');
INSERT INTO `sys_dict` VALUES ('2', '删除', '1', 'del_flag', '删除标记', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('3', '显示', '1', 'show_hide', '显示/隐藏', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('4', '隐藏', '0', 'show_hide', '显示/隐藏', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('5', '是', '1', 'yes_no', '是/否', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('6', '否', '0', 'yes_no', '是/否', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('17', '国家', '1', 'sys_area_type', '区域类型', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('18', '省份、直辖市', '2', 'sys_area_type', '区域类型', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('19', '地市', '3', 'sys_area_type', '区域类型', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('20', '区县', '4', 'sys_area_type', '区域类型', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('22', '部门', '2', 'sys_office_type', '机构类型', '70', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('23', '一级', '1', 'sys_office_grade', '机构等级', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('24', '二级', '2', 'sys_office_grade', '机构等级', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('25', '三级', '3', 'sys_office_grade', '机构等级', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('26', '四级', '4', 'sys_office_grade', '机构等级', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('27', '所有数据', '1', 'sys_data_scope', '数据范围', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('28', '所在公司及以下数据', '2', 'sys_data_scope', '数据范围', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('29', '所在公司数据', '3', 'sys_data_scope', '数据范围', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('30', '所在部门及以下数据', '4', 'sys_data_scope', '数据范围', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('31', '所在部门数据', '5', 'sys_data_scope', '数据范围', '50', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('32', '仅本人数据', '8', 'sys_data_scope', '数据范围', '90', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('33', '按明细设置', '9', 'sys_data_scope', '数据范围', '100', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('34', '系统管理', '1', 'sys_user_type', '用户类型', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('35', '部门经理', '2', 'sys_user_type', '用户类型', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('36', '普通用户', '3', 'sys_user_type', '用户类型', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('62', '操作日志', '1', 'sys_log_type', '日志类型', '30', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('63', '异常日志', '2', 'sys_log_type', '日志类型', '40', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('64', '单表增删改查', 'single', 'prj_template_type', '代码模板', '10', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('65', '所有entity和dao', 'entityAndDao', 'prj_template_type', '代码模板', '20', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('66', '公司', '1', 'sys_office_type', '', '1', null, '2015-01-10 22:15:43', null, '2015-01-10 22:15:43', null, '0');
INSERT INTO `sys_dict` VALUES ('67', '等级', '1', 'score', '', '1', '2,超级管理员', '2015-12-28 22:19:24', null, '2015-12-28 22:19:24', null, '0');
INSERT INTO `sys_dict` VALUES ('68', '首页导航', '0', 'nav_type', '', '1', null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('69', '底部导航', '1', 'nav_type', '', '2', null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('70', '文章', '0', 'cms_cate_type', '1', '1', null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('71', '图片', '1', 'cms_cate_type', '2', '2', null, null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `area_id` bigint(20) NOT NULL COMMENT '归属区域',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '机构名称',
  `type` char(1) DEFAULT NULL COMMENT '机构类型',
  `grade` char(1) DEFAULT NULL COMMENT '机构等级',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `icon` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_parent_ids` (`parent_ids`(255)),
  KEY `sys_office_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('1', '0', '0,', '2', '100000', '北京市总公司', '1', '1', '', null, '', '', '', '', '1', '2013-05-27 08:00:00', '2,超级管理员', '2015-02-28 20:49:57', '', '0', 'fa fa-bicycle');
INSERT INTO `sys_office` VALUES ('2', '1', '0,1,', '2', '100001', '公司领导', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_office` VALUES ('3', '1', '0,1,', '2', '100002', '人力部', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_office` VALUES ('4', '1', '0,1,', '2', '100003', '市场部', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_office` VALUES ('5', '1', '0,1,', '2', '100004', '技术部', '2', '4', '', null, '', '', '', '', '1', '2013-05-27 08:00:00', '22', '2015-01-24 16:39:03', '', '0', '');
INSERT INTO `sys_office` VALUES ('6', '1', '0,1,', '2', '100005', '研发部', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_office` VALUES ('7', '1', '0,1,', '3', '200000', '山东省分公司', '1', '2', '', '', '', '', '', '', '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', '', '0', null);
INSERT INTO `sys_office` VALUES ('8', '7', '0,1,7,', '8', '200001', '公司领导', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('9', '7', '0,1,7,', '8', '200002', '综合部', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('10', '7', '0,1,7,', '8', '200003', '市场部', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('11', '7', '0,1,7,', '8', '200004', '技术部', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('12', '7', '0,1,7,', '9', '201000', '济南市分公司', '1', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('13', '12', '0,1,7,12,', '9', '201001', '公司领导', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('14', '12', '0,1,7,12,', '9', '201002', '综合部', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('15', '12', '0,1,7,12,', '9', '201003', '市场部', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('16', '12', '0,1,7,12,', '9', '201004', '技术部', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('17', '12', '0,1,7,12,', '11', '201010', '济南市历城区分公司', '1', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('18', '17', '0,1,7,12,17,', '11', '201011', '公司领导', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('19', '17', '0,1,7,12,17,', '11', '201012', '综合部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('20', '17', '0,1,7,12,17,', '11', '201013', '市场部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('21', '17', '0,1,7,12,17,', '11', '201014', '技术部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('22', '12', '0,1,7,12,', '12', '201020', '济南市历下区分公司', '1', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('23', '22', '0,1,7,12,22,', '12', '201021', '公司领导', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('24', '22', '0,1,7,12,22,', '12', '201022', '综合部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('25', '22', '0,1,7,12,22,', '12', '201023', '市场部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('26', '22', '0,1,7,12,22,', '12', '201024', '技术部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('27', '5', '0,1,5,', '2', null, '技术1部门', '2', null, '', null, '', '', '', '', null, '2015-01-11 15:19:53', null, '2015-01-11 15:19:53', '', '0', null);
INSERT INTO `sys_office` VALUES ('28', '0', '0,', '1', null, '测试公司名字一定要长', '1', null, '', null, '', '', '', '', null, '2015-01-11 23:48:22', null, '2015-01-12 21:46:32', '', '1', null);
INSERT INTO `sys_office` VALUES ('29', '28', '0,28,', '1', null, '测试部门1', '2', null, '', null, '', '', '', '', null, '2015-01-11 23:48:35', null, '2015-01-11 23:48:35', '', '1', null);
INSERT INTO `sys_office` VALUES ('30', '10', '0,1,7,10,', '1', null, '市场子部门', '2', null, '', null, '', '', '', '', null, '2015-01-13 22:56:14', null, '2015-01-13 22:56:14', '', '0', null);
INSERT INTO `sys_office` VALUES ('35', '0', '0,', '1', null, 'fsdfsdf', '2', '1', '', null, '', '', '', '', null, '2015-01-14 23:13:43', null, '2015-01-14 23:13:43', '', '0', null);
INSERT INTO `sys_office` VALUES ('36', '35', '0,35,', '1', null, '2222', '2', '2', '', null, '', '', '', '', '2', '2015-01-18 20:29:53', '2', '2015-01-18 20:34:08', '', '0', null);
INSERT INTO `sys_office` VALUES ('37', '1', '0,1,', '2', null, '测测', '2', '2', '', null, '', '', '', '', '22', '2015-01-24 15:19:09', null, '2015-01-24 15:19:09', '', '0', '');
INSERT INTO `sys_office` VALUES ('38', '5', '0,1,5,', '2', null, 'sdsd', '2', '3', '', null, '', '', '', '', '22', '2015-01-24 17:08:50', null, '2015-01-24 17:08:50', '', '0', '');
INSERT INTO `sys_office` VALUES ('39', '35', '0,35,', '1', null, 'sssddd', '2', '2', '', null, '', '', '', '', '2', '2015-01-24 17:35:09', null, '2015-01-24 17:35:09', '', '0', '');
INSERT INTO `sys_office` VALUES ('40', '1', '0,1,', '2', null, '测试', '2', '2', '', null, '', '', '', '', '22', '2015-01-25 10:23:15', null, '2015-01-25 10:23:15', '', '1', '');
INSERT INTO `sys_office` VALUES ('41', '1', '0,1,', '2', null, 'aaaa', '2', '2', '', null, '', '', '', '', '22', '2015-01-25 21:34:43', null, '2015-01-25 21:34:43', '', '0', '');
INSERT INTO `sys_office` VALUES ('42', '1', '0,1,', '2', null, 'aaaa', '2', '2', '', null, '', '', '', '', '22', '2015-01-25 21:37:13', null, '2015-01-25 21:37:13', '', '1', '');
INSERT INTO `sys_office` VALUES ('43', '1', '0,1,', '2', null, 'ffffddd', '2', '2', '', null, '', '', '', '', '22', '2015-01-25 21:37:48', null, '2015-01-25 21:37:48', '', '0', '');
INSERT INTO `sys_office` VALUES ('45', '1', '0,1,', '2', null, '测试自动赋权', '2', '2', '', null, '', '', '', '', '22', '2015-01-27 20:02:50', null, '2015-01-27 20:02:50', '', '0', '');
INSERT INTO `sys_office` VALUES ('46', '1', '0,1,', '2', null, 'cc22', '2', '2', '', null, '', '', '', '', '22', '2015-01-27 20:19:45', null, '2015-01-27 20:19:45', '', '0', '');
INSERT INTO `sys_office` VALUES ('47', '0', '0,', '1', null, 'sss', '1', '1', '', null, '', '', '', '', '2', '2015-01-28 21:46:00', null, '2015-01-28 21:46:00', '', '1', '');
INSERT INTO `sys_office` VALUES ('48', '1', '0,1,', '1', null, 'dd', '1', '2', '', null, '', '', '', '', '22', '2015-01-28 22:33:04', null, '2015-01-28 22:33:04', '', '0', '');
INSERT INTO `sys_office` VALUES ('49', '0', '0,', '1', null, 'xcxzcxc', '2', '1', '', null, '', '', '', '', '22', '2015-01-28 22:55:37', null, '2015-01-28 22:55:37', '', '0', '');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '资源名称',
  `common` char(1) DEFAULT '0' COMMENT '是否是公共资源(0.不是 1.是)',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `sort` int(11) DEFAULT '1' COMMENT '排序号',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `type` char(1) DEFAULT '0' COMMENT '类型(0.菜单 1.按钮)',
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态(0.正常 1.禁用)',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '父级集合',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  `permission_str` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '菜单配置', '0', 'fa fa-list', '5', '188', '0', 'menu', '', '0', '0,188,', null, '2015-03-11 23:12:27', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('181', '区域管理', '0', 'fa fa-globe', '6', '188', '0', 'area', '', '0', '0,188,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('188', '系统管理', '0', 'fa fa-cogs', '1', '0', '0', '', '', '0', '0,', null, '2015-03-12 23:57:18', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('190', '字典管理', '0', 'fa fa-calculator', '7', '188', '0', 'dict', '', '0', '0,188,', null, '2015-03-11 23:12:41', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('192', '机构管理', '0', 'fa fa-sitemap', '4', '188', '0', 'office', '', '0', '0,188,', null, '2015-03-11 23:08:59', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('193', '用户管理', '0', 'fa fa-user', '1', '188', '0', 'sysuser', '', '0', '0,188,', null, '2015-03-11 23:07:11', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('194', '角色管理', '0', 'fa fa-graduation-cap', '2', '188', '0', 'role', '', '0', '0,188,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('195', '日志查询', '0', 'fa fa-copy', '8', '188', '0', 'syslog', '', '0', '0,188,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('203', '搜索按钮', '0', 'fa fa-angellist', '1', '181', '1', 'sys:area:find', '这是一个按钮级别的示例，页面为添加，请添加@if(auth.hasPermission(\"sys:area:find\")){}测试', '0', '0,188,181,', '2015-01-20 20:50:16', '2015-01-20 20:57:38', '22', '22', '0', null);
INSERT INTO `sys_resource` VALUES ('204', '系统监控', '0', 'fa fa-binoculars', '6', '0', '0', '', '', '0', '0,', '2015-03-03 20:11:10', '2015-03-11 23:12:56', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('205', 'Ehcache监控', '0', 'fa fa-crosshairs', '1', '204', '0', 'monitor/ehcache', '', '0', '0,204,', '2015-03-03 20:11:19', '2015-03-11 23:15:52', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('206', 'jvm监控', '0', 'fa fa-flash', '1', '204', '0', 'monitor/jvm', '', '0', '0,204,', '2015-03-08 11:17:00', '2015-03-11 23:20:19', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('207', '执行sql', '0', 'fa fa-ge', '1', '204', '0', 'monitor/db/sql', '', '0', '0,204,', '2015-03-09 21:07:49', '2015-03-11 23:18:39', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('208', '数据库监控', '0', 'fa fa-github-alt', '1', '204', '0', 'monitor/db/druid', '', '0', '0,204,', '2015-03-10 21:11:20', '2015-03-11 23:19:56', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('214', '博客管理', '0', 'fa fa-bell-slash', '1', '0', '0', '', '', '0', '0,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('215', '博客类型', '0', '', '1', '214', '0', 'blogType', '', '0', '0,214,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('216', '文章管理', '0', '', '1', '214', '0', 'blog', '', '0', '0,214,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('228', '内容管理', '0', '', '1', '0', '0', '', '', '0', '0,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('229', '文章管理', '0', '', '1', '228', '0', 'cmsArticle', '', '0', '0,228,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('230', '网站链接', '0', '', '4', '228', '0', 'cmsLink', '', '0', '0,228,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('231', '增加', '0', 'fa fa-area-chart', '1', '230', '1', 'cms:cmsLink:add', '', '0', '0,228,230,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('232', '官网管理', '0', 'fa fa-youtube', '1', '0', '0', '', '', '0', '0,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('233', '产品管理', '0', 'fa fa-area-chart', '1', '232', '0', 'product', '', '0', '0,232,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('234', '产品类型', '0', 'fa fa-bicycle', '2', '232', '0', 'productType', '', '0', '0,232,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('235', '官网信息', '0', 'fa fa-cc-mastercard', '4', '232', '0', 'gwInfo', '', '0', '0,232,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('236', '官网导航', '0', 'fa fa-arrows-alt', '1', '232', '0', 'nav', '', '0', '0,232,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('237', '编辑', '0', '', '1', '233', '1', 'gw:product:edit', '', '0', '0,232,233,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('238', '删除', '0', '', '1', '233', '1', 'gw:product:delete', '', '0', '0,232,233,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('239', '删除', '0', '', '1', '236', '1', 'gw:nav:delete', '', '0', '0,232,236,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('242', '文章管理', '0', '', '1', '214', '0', 'blog', '', '0', '0,214,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('243', 'ss', '0', '', '1', '207', '0', 'ss', '', '0', '0,204,207,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('244', '站点管理', '0', '', '2', '228', '0', 'cmsSite', '', '0', '0,228,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('245', '栏目管理', '0', '', '3', '228', '0', 'cmsCategory', '', '0', '0,228,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('246', '图片管理', '0', '', '7', '228', '0', 'cmsImg', '', '0', '0,228,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('247', '模板管理', '0', '', '2', '214', '0', 'blogTemplate', '', '0', '0,214,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('248', 'demo', '0', '', '1', '188', '0', 'demo', '', '0', '0,188,', null, null, null, null, '0', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `office_id` bigint(20) DEFAULT NULL COMMENT '归属机构',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('21', '49', 'test', '8', '2,超级管理员', '2016-03-08 22:14:31', null, '2016-03-08 22:14:31', null, '0');
INSERT INTO `sys_role` VALUES ('22', '49', 'zhuan', '5', '2,超级管理员', '2016-03-08 22:18:25', '2,超级管理员', '2016-03-08 22:18:46', null, '0');

-- ----------------------------
-- Table structure for sys_role_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_office`;
CREATE TABLE `sys_role_office` (
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  `office_id` bigint(20) NOT NULL COMMENT '机构编号',
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-机构';

-- ----------------------------
-- Records of sys_role_office
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1496 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1334', '21', '188', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1335', '21', '1', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1336', '21', '190', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1337', '21', '195', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1338', '21', '189', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1339', '21', '181', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1340', '21', '203', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1341', '21', '192', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1342', '21', '193', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1343', '21', '194', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1344', '21', '204', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1345', '21', '205', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1346', '21', '206', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1347', '21', '207', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1348', '21', '208', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1469', '22', '188', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1470', '22', '1', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1471', '22', '181', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1472', '22', '203', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1473', '22', '190', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1474', '22', '192', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1475', '22', '193', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1476', '22', '195', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1477', '22', '194', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1478', '22', '214', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1479', '22', '215', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1480', '22', '228', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1481', '22', '229', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1482', '22', '244', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1483', '22', '245', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1484', '22', '230', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1485', '22', '231', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1486', '22', '232', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1487', '22', '233', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1488', '22', '236', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1489', '22', '234', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1490', '22', '235', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1491', '22', '204', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1492', '22', '205', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1493', '22', '206', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1494', '22', '207', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1495', '22', '208', null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `company_id` bigint(20) NOT NULL COMMENT '归属公司',
  `office_id` bigint(20) NOT NULL COMMENT '归属部门',
  `username` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `user_type` char(1) DEFAULT '0' COMMENT '用户类型(0.普通 1.系统超级管理员)',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `status` char(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`),
  KEY `sys_user_login_name` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('2', '1', '1', 'admin', '86f3059b228c8acf99e69734b6bb32cc', '0002', '超级管理员', '@163.com', '8675', '8675', '1', '127.0.0.1', '2016-11-16 19:13:32', '1', '2013-05-27 08:00:00', '2,超级管理员', '2016-11-11 09:42:59', '管理员', '0', '0');
INSERT INTO `sys_user` VALUES ('27', '1', '1', 'shen123', 'd7d8ae585083189f3a676506ee3f0ec6', '', 'shen', '', '', '', '0', '0:0:0:0:0:0:0:1', '2015-11-06 14:01:20', '2,超级管理员', '2015-11-06 14:01:01', '27,shen', '2015-11-06 14:01:20', '', '1', '0');
INSERT INTO `sys_user` VALUES ('31', '49', '49', 'shen', '2d21ce1763c355a5d946bbe9624ad878', 'ww', 'shen', 'eee', '333', '333', '1', '127.0.0.1', '2016-11-03 15:40:33', '2,超级管理员', '2016-03-08 22:19:17', '31,zhuan', '2016-03-08 22:19:46', '33', '0', '0');
INSERT INTO `sys_user` VALUES ('32', '1', '1', 'shenzhuan', '00e5d5880613d3dbbdf7882184827520', '', 'shenzhuan', '', '', '', '0', '0:0:0:0:0:0:0:1', '2016-05-24 22:30:35', null, null, null, null, '', '0', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('76', '21', '30', null, null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('79', '22', '31', null, null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('80', '22', '32', null, null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('81', '22', '2', null, null, null, null, '0');
