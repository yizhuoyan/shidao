/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : shidao

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2017-11-24 22:23:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qst_knowledge_point
-- ----------------------------
DROP TABLE IF EXISTS `qst_knowledge_point`;
CREATE TABLE `qst_knowledge_point` (
  `id` char(32) NOT NULL,
  `code` varchar(256) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `remark` text,
  `childrenAmount` int(11) DEFAULT NULL,
  `parent_id` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qst_knowledge_point
-- ----------------------------

-- ----------------------------
-- Table structure for qst_question
-- ----------------------------
DROP TABLE IF EXISTS `qst_question`;
CREATE TABLE `qst_question` (
  `id` char(32) NOT NULL DEFAULT '',
  `content` varchar(256) DEFAULT NULL COMMENT '题目内容',
  `difficult` tinyint(4) DEFAULT NULL COMMENT '难度',
  `id_user_creater` char(32) DEFAULT NULL COMMENT '创建人',
  `kind` tinyint(1) NOT NULL DEFAULT '0' COMMENT '题目类型0=不定项选择,1=单选,2=多选',
  `time_create` datetime DEFAULT NULL,
  `time_update` datetime DEFAULT NULL COMMENT '答案',
  `answer` text,
  `answer_explain` text COMMENT '答案解析',
  `options` text COMMENT '题目选项换行符隔开',
  `id_question_belong` char(32) DEFAULT NULL COMMENT '所属题目，用于综合题的小题',
  `children_Amount` tinyint(4) DEFAULT NULL COMMENT '包含小题数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qst_question
-- ----------------------------
INSERT INTO `qst_question` VALUES ('', null, null, null, '0', null, null, null, null, null, null, null);
INSERT INTO `qst_question` VALUES ('1', '1+2=单选题', '0', null, '1', null, null, null, null, null, null, null);
INSERT INTO `qst_question` VALUES ('2', '2?2=4多选题', '0', null, '2', null, null, null, null, null, null, null);
INSERT INTO `qst_question` VALUES ('3', '2?=4不定项', '0', null, '0', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for qst_question_kind
-- ----------------------------
DROP TABLE IF EXISTS `qst_question_kind`;
CREATE TABLE `qst_question_kind` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qst_question_kind
-- ----------------------------
INSERT INTO `qst_question_kind` VALUES ('choice-any', '不定型选题', null);
INSERT INTO `qst_question_kind` VALUES ('choice-mult', '多项选择题', '');
INSERT INTO `qst_question_kind` VALUES ('choice-radio', '单项选择题', 'ssss');
INSERT INTO `qst_question_kind` VALUES ('composite', '综合题', null);
INSERT INTO `qst_question_kind` VALUES ('composite-part', '综合小题', null);
INSERT INTO `qst_question_kind` VALUES ('essay', '问答题', null);
INSERT INTO `qst_question_kind` VALUES ('fillinblank', '填空题', null);
INSERT INTO `qst_question_kind` VALUES ('yesno', '是非题', null);

-- ----------------------------
-- Table structure for rel_question_knowledge_point
-- ----------------------------
DROP TABLE IF EXISTS `rel_question_knowledge_point`;
CREATE TABLE `rel_question_knowledge_point` (
  `id_knowledge_point` char(32) NOT NULL,
  `id_question` char(32) NOT NULL,
  PRIMARY KEY (`id_knowledge_point`,`id_question`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rel_question_knowledge_point
-- ----------------------------

-- ----------------------------
-- Table structure for rel_role_functionality
-- ----------------------------
DROP TABLE IF EXISTS `rel_role_functionality`;
CREATE TABLE `rel_role_functionality` (
  `role_id` char(32) NOT NULL DEFAULT '',
  `functionality_id` char(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`role_id`,`functionality_id`),
  KEY `module_id` (`functionality_id`),
  CONSTRAINT `rel_role_functionality_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `rel_role_functionality_ibfk_4` FOREIGN KEY (`functionality_id`) REFERENCES `sys_functionality` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rel_role_functionality
-- ----------------------------
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '024af391ee97d28bdd34c992effe6ab0');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '1');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '144454b9dcd3d9cb53c4cbd4316671f9');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '17174ac49dbc782b8b946b07fdec19cd');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '2');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '56cf2a5ddd8f00fb61244f49e647a3c2');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '697f5f731cd5afea0db40879a5d5fd13');
INSERT INTO `rel_role_functionality` VALUES ('e435c0ef9d801cbb2b7427a76bfbe7d9', '697f5f731cd5afea0db40879a5d5fd13');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '6d8e83a8829944d8e354bd7ea9463b9f');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '7228c48eb66585c9e8b4faa64f16d115');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '7708e4aea79a974aed34b6c93a4aa13b');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '7df64e78d8ee8688042484fc2cec3be6');
INSERT INTO `rel_role_functionality` VALUES ('e435c0ef9d801cbb2b7427a76bfbe7d9', '7df64e78d8ee8688042484fc2cec3be6');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', '7e00d4b61c7bceb8f84470c6dbb9efbd');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', 'ae11caced5e2785a4da4a0a21fb3208a');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', 'c0ec45a8ae14e2788a44a689ce5731cf');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', 'c573c76681561e189774858eb218000c');
INSERT INTO `rel_role_functionality` VALUES ('e435c0ef9d801cbb2b7427a76bfbe7d9', 'c573c76681561e189774858eb218000c');
INSERT INTO `rel_role_functionality` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', 'e5931aeffc9267f894c46f59d97e7c54');

-- ----------------------------
-- Table structure for rel_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rel_user_role`;
CREATE TABLE `rel_user_role` (
  `user_id` char(32) NOT NULL DEFAULT '',
  `role_id` char(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `rel_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `rel_user_role_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rel_user_role
-- ----------------------------
INSERT INTO `rel_user_role` VALUES ('b0acbdb9c6146a59c614339554990188', '0f2dc8d5b9c380ab45e442c979ab902f');
INSERT INTO `rel_user_role` VALUES ('supermanager', '0f2dc8d5b9c380ab45e442c979ab902f');
INSERT INTO `rel_user_role` VALUES ('521ba463ce74fad81ee4d582a36ca255', '537d48b3b02094fa5cb4bde747b7ddd4');
INSERT INTO `rel_user_role` VALUES ('b0acbdb9c6146a59c614339554990188', '537d48b3b02094fa5cb4bde747b7ddd4');
INSERT INTO `rel_user_role` VALUES ('b0acbdb9c6146a59c614339554990188', '941c69eda85e772bca0488a23a1edb51');
INSERT INTO `rel_user_role` VALUES ('521ba463ce74fad81ee4d582a36ca255', 'a6ff5e51751b9ff88934fbb54a4b0d77');
INSERT INTO `rel_user_role` VALUES ('b0acbdb9c6146a59c614339554990188', 'e435c0ef9d801cbb2b7427a76bfbe7d9');
INSERT INTO `rel_user_role` VALUES ('supermanager', 'e435c0ef9d801cbb2b7427a76bfbe7d9');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` char(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `value` varchar(256) DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `status` smallint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('064157911a75e9197c444b52de0444bb', 'v1', '啊', null, '1');
INSERT INTO `sys_config` VALUES ('0a5fb1e039a00ad8b9c4820a51222550', '计算机网络管理111', '啊啊', null, '1');
INSERT INTO `sys_config` VALUES ('1', 'USER-INIT-PASSWORD', '123456', '用户初始化密码', '1');
INSERT INTO `sys_config` VALUES ('199d0bb5c29db47affa436e0a2d810b0', ' 1', ' 2', null, '0');
INSERT INTO `sys_config` VALUES ('1b26fb3992e1aec853f4d772f4c00f11', '1', '1', '12', '1');
INSERT INTO `sys_config` VALUES ('2', 'VERSION', '1.0', '', '1');
INSERT INTO `sys_config` VALUES ('2d20f71e7742015a239453eb47ca0aa0', 'PAGINATION-PAGESIZE', '10', '系统所有查询页面中默认分页大小', '1');
INSERT INTO `sys_config` VALUES ('33b6d9899d8aa38ae574e1290bb9deba', '2', '2', '', '1');
INSERT INTO `sys_config` VALUES ('3d86063cae96bf6a0f64788b0593ab14', '&nbsp;ass5', '&nbsp;dd', '', '0');
INSERT INTO `sys_config` VALUES ('4', '1', '学生', null, '0');
INSERT INTO `sys_config` VALUES ('5', '2', '教师', null, '0');
INSERT INTO `sys_config` VALUES ('52044c1124744afa1ef452dd74abf397', '112222', '11', '11', '1');
INSERT INTO `sys_config` VALUES ('52044cb124744afa1ef452dd74abf397', '112222', '11', '11', '1');
INSERT INTO `sys_config` VALUES ('5900e7b98602a7c88c84747ee5fe1ce0', 'v1234', '啊', null, '1');
INSERT INTO `sys_config` VALUES ('5ab98e4ea69713babb943bc62103b294', 'v12', '啊', null, '1');
INSERT INTO `sys_config` VALUES ('6', '3', '作业评审员', null, '0');
INSERT INTO `sys_config` VALUES ('6512d06ff375cda94eb48203ab9fc49d', 'v123', '啊', null, '1');
INSERT INTO `sys_config` VALUES ('67ef37978017050a6a8413c8ac0e7742', 'v', '啊', null, '1');
INSERT INTO `sys_config` VALUES ('6ee7f19af5cf2a5b0e447ee4a7b54128', '11', '11', '<button>ok</button>', '1');
INSERT INTO `sys_config` VALUES ('7', '4', '管理员', null, '0');
INSERT INTO `sys_config` VALUES ('7771f2f86f0848a952e473c020fd576c', '计算机网络管理1', '啊啊', null, '1');
INSERT INTO `sys_config` VALUES ('866851b1a509d8e88df41063d4d43715', '计算机网络管理1111', '啊啊', null, '1');
INSERT INTO `sys_config` VALUES ('914516e392ec189b9d44b30f5036538c', 'xxxxaa', 'aaa', '<script>alert(3)</script>', '1');
INSERT INTO `sys_config` VALUES ('a342a337a18b47bbe3e4c6746c0365ce', '系统管理', '系统管理', '系统管理', '1');
INSERT INTO `sys_config` VALUES ('af7209d1e382a80b49d4b16c4bd29064', '计算机网络管理', '啊啊', null, '1');
INSERT INTO `sys_config` VALUES ('b062d870f43f18289a347324f9ffd02e', 'v12345', '啊', null, '1');
INSERT INTO `sys_config` VALUES ('c0ad3ed5465c72c89064b313ade928d2', 'PASSWORD-EXPIRE-DAYS', '30', '密码默认过期天数,过期后用户必须修改密码', '1');
INSERT INTO `sys_config` VALUES ('c23559021e45b7f88ea4ad91e444b7f4', '系统管理1', '系统管理', '系统管理', '1');
INSERT INTO `sys_config` VALUES ('c4a5dc93ec24dadb2804145a3ba822cd', 'aaa1', 'aaa', '发的', '0');
INSERT INTO `sys_config` VALUES ('d7acc5e47bc4d0f98824379c1669e41f', '1122', '11', '11', '1');
INSERT INTO `sys_config` VALUES ('d7b0554f3fbc69d938048fff674b906b', 'BFFD', 'FDF', 'FDF', '1');
INSERT INTO `sys_config` VALUES ('e1d7fcd9c08d2bc96de4af98bb7c32fe', 'fff', 'ff', null, '1');
INSERT INTO `sys_config` VALUES ('ef40c2ad352686c983f462885e0dfbe8', 'name1', 'aa', 'aa', '1');
INSERT INTO `sys_config` VALUES ('f21974e40518dcb99784f62392a94171', '计算机网络管理11', '啊啊', null, '1');
INSERT INTO `sys_config` VALUES ('radio-Choice', 'radio-Choice', '单选', null, '0');

-- ----------------------------
-- Table structure for sys_functionality
-- ----------------------------
DROP TABLE IF EXISTS `sys_functionality`;
CREATE TABLE `sys_functionality` (
  `id` char(32) NOT NULL DEFAULT '',
  `code` varchar(512) NOT NULL,
  `name` varchar(32) NOT NULL,
  `url` varchar(256) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `parent_id` char(32) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `kind` tinyint(1) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `orderCode` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `sys_functionality_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `sys_functionality` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_functionality
-- ----------------------------
INSERT INTO `sys_functionality` VALUES ('024af391ee97d28bdd34c992effe6ab0', '/ksgl/sjgl', '试卷管理', null, '2015-11-04 22:07:42', '17174ac49dbc782b8b946b07fdec19cd', '1', '1', '', null);
INSERT INTO `sys_functionality` VALUES ('1', '/xtgl/xtpz', '系统配置', '/platform/systemconfig/list/view.html', '2015-11-10 13:15:40', 'c0ec45a8ae14e2788a44a689ce5731cf', '1', '1', '系统参数配置,保证系统正常运行,必须小心操作', null);
INSERT INTO `sys_functionality` VALUES ('144454b9dcd3d9cb53c4cbd4316671f9', '/grzx', '个人中心', '', '2017-11-10 21:45:41', null, '1', '0', '', '80');
INSERT INTO `sys_functionality` VALUES ('17174ac49dbc782b8b946b07fdec19cd', '/ksgl', '考试管理', null, '2015-11-02 22:30:11', null, '1', '0', null, '67');
INSERT INTO `sys_functionality` VALUES ('2', '/xtgl/xtmk', '系统功能', '/platform/systemfunctionality/list/view.html', '2015-11-17 13:27:19', 'c0ec45a8ae14e2788a44a689ce5731cf', '1', '1', '管理系统功能模块,以及功能模块分配给角色\r\n', null);
INSERT INTO `sys_functionality` VALUES ('56cf2a5ddd8f00fb61244f49e647a3c2', '/ksgl/ksgl', '考生管理', null, '2015-11-02 22:06:40', '17174ac49dbc782b8b946b07fdec19cd', '1', '1', '', null);
INSERT INTO `sys_functionality` VALUES ('681f7716b5690dbad934c14d8302fc38', '/xtgl/xtpz/del', '删除', '/platform/systemfunctionality/del', '2017-11-22 15:10:58', '1', '1', '2', '<删除>', '02');
INSERT INTO `sys_functionality` VALUES ('697f5f731cd5afea0db40879a5d5fd13', '/tkzx', '题库中心', null, '2016-01-17 19:58:59', null, '1', '0', null, '65');
INSERT INTO `sys_functionality` VALUES ('6d8e83a8829944d8e354bd7ea9463b9f', '/xtgl/xtgl/add', '新增', null, '2017-11-01 17:15:54', '2', '1', '2', '', null);
INSERT INTO `sys_functionality` VALUES ('7228c48eb66585c9e8b4faa64f16d115', '/xtgl/jsgl', '角色管理', '/platform/rolemanage/list/view.html', '2015-11-02 22:07:55', 'c0ec45a8ae14e2788a44a689ce5731cf', '1', '1', null, null);
INSERT INTO `sys_functionality` VALUES ('7708e4aea79a974aed34b6c93a4aa13b', '/grzx/wdzh', '我的账号', '/platform/usercenter/my-account/view.html', '2017-11-10 22:03:09', '144454b9dcd3d9cb53c4cbd4316671f9', '1', '1', '123', '01');
INSERT INTO `sys_functionality` VALUES ('7df64e78d8ee8688042484fc2cec3be6', '/tkzx/stgl', '题目管理', '/questionhub/questionmanage/list/view.html', '2015-11-02 22:09:23', '697f5f731cd5afea0db40879a5d5fd13', '1', '1', '', '');
INSERT INTO `sys_functionality` VALUES ('7e00d4b61c7bceb8f84470c6dbb9efbd', '/xtgl/yhgl', '用户管理', '/platform/usermanage/list/view.html', '2015-11-02 22:10:38', 'c0ec45a8ae14e2788a44a689ce5731cf', '1', '1', '包括用户的新增删除修改,角色的分配,权限的查看等', null);
INSERT INTO `sys_functionality` VALUES ('ae11caced5e2785a4da4a0a21fb3208a', '/dashbord', '首页', '/dashboard/view.html', '2017-11-03 21:54:19', null, '1', '1', '首页', '00');
INSERT INTO `sys_functionality` VALUES ('bac883b3c099ca28e904f8449f140006', '/xtgl/xtpz/add', '新增', '/platform/systemfunctionality/add', '2017-11-22 15:09:07', '1', '1', '2', ' 新增', '01');
INSERT INTO `sys_functionality` VALUES ('c0ec45a8ae14e2788a44a689ce5731cf', '/xtgl', '系统管理', null, '2015-10-31 16:33:47', null, '1', '0', '顶层模块,包含系统设置等基础数据维护,保证系统正确运行1', '90');
INSERT INTO `sys_functionality` VALUES ('c573c76681561e189774858eb218000c', '/tkzx/txgl', '题型管理', '/appquestionbank/questiontypemanage/index.html', '2016-01-17 12:07:29', '697f5f731cd5afea0db40879a5d5fd13', '1', '1', '题库题型管理,主要用于配制题型视图模板,题目存储数据库表名称', null);
INSERT INTO `sys_functionality` VALUES ('d87fd9ebba4e8e1a72e4843c9de41b9b', '/xtgl/xtpz/mod', '修改', '/platform/systemfunctionality/mod', '2017-11-22 15:15:09', '1', '1', '2', '', '03');
INSERT INTO `sys_functionality` VALUES ('e5931aeffc9267f894c46f59d97e7c54', '/grzx/xgmm', '修改密码', '/platform/usercenter/modify-password/view.html', '2017-11-10 22:00:30', '144454b9dcd3d9cb53c4cbd4316671f9', '1', '1', '12345', '02');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` char(32) NOT NULL,
  `code` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('0f2dc8d5b9c380ab45e442c979ab902f', 'suppermanager', '超级系统管理员', '超级系统管理员,主要用于系统配置,系统模块,系统角色的管理,不可删除');
INSERT INTO `sys_role` VALUES ('537d48b3b02094fa5cb4bde747b7ddd4', 'exammanager', '考试管理员', '考试管理员负责考试基础数据维护');
INSERT INTO `sys_role` VALUES ('941c69eda85e772bca0488a23a1edb51', 'examquestioninputer', '考试试题录入人员', '负责录入考试试题');
INSERT INTO `sys_role` VALUES ('a6ff5e51751b9ff88934fbb54a4b0d77', 'exameen', '考生', '可与参与考试');
INSERT INTO `sys_role` VALUES ('e435c0ef9d801cbb2b7427a76bfbe7d9', 'questionbankmanager', '题库管理员', '题库中题型和题目管理');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` char(32) NOT NULL,
  `account` varchar(16) NOT NULL,
  `name` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `avator` varchar(256) DEFAULT NULL COMMENT '头像',
  `createTime` datetime NOT NULL,
  `lastModPassTime` datetime DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `loginIp` varchar(16) DEFAULT NULL,
  `status` tinyint(1) unsigned NOT NULL COMMENT '用户状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('07d2ab1f870f8579ab0449f4e3783bc5', '123456', '新用户', '123456', null, '2015-10-30 13:52:13', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('0fd00151d1d9b3196954961d22929568', '131231', '新用户', '123456', null, '2017-11-03 16:14:39', null, null, '1', null, '1');
INSERT INTO `sys_user` VALUES ('247e9f6eeafbc78bfa5483ee2cee23f6', '123', '新用户', '123', null, '2015-10-30 13:52:04', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('310387d2e792a379f8a4fadce277ebeb', '1234', '新用户', '123456', null, '2015-10-30 13:52:07', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('398e6bb70453d28b1e54221267515cc9', '2', '2', '123456', null, '2015-11-10 11:06:21', null, null, '2', null, '0');
INSERT INTO `sys_user` VALUES ('48335ba9c8c19faaabd4b06cc001a0f2', 'teacher2', '请修改名称', '123456', null, '2015-08-07 23:00:23', null, null, null, null, '0');
INSERT INTO `sys_user` VALUES ('51142eac865bc539e6a4f888b32fb8d3', '阿凡达', '新用户', '123456', null, '2017-11-03 16:06:37', null, null, '11', null, '1');
INSERT INTO `sys_user` VALUES ('521ba463ce74fad81ee4d582a36ca255', '12345', '新用户', '123456', null, '2015-10-30 13:52:10', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('63e03c1ad1ea7c399bb48a0c5a2f719a', 'yizhuoyan', '新用户', '123456', null, '2015-10-30 12:49:36', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('951a992e978c8e7b841455261dd2fa8a', 'admin', 'admin', '123456', null, '2016-08-14 11:49:39', null, null, 'fl;dakd', null, '1');
INSERT INTO `sys_user` VALUES ('a63c38bd845536e8ac34caed29dafa0a', '阿凡达7777', '新用户', '123456', null, '2017-11-03 16:11:12', null, null, '11', null, '1');
INSERT INTO `sys_user` VALUES ('b0acbdb9c6146a59c614339554990188', '1', '新用户', '1', null, '2015-11-10 11:05:04', null, null, '1', null, '1');
INSERT INTO `sys_user` VALUES ('be446da83e5c59181ed48ad30f297d47', 'xyz', '请修改名称', '123456', null, '2015-10-30 12:10:45', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('cc2a8be31c1544d97cd4fb12c90ce10a', '12', '新用户', '123456', null, '2017-11-03 15:56:36', null, null, '12', null, '1');
INSERT INTO `sys_user` VALUES ('cd7e82dddf537b8a0f941d08393b5df0', 'teacher1', '请修改名称', '123456', null, '2015-08-07 22:40:57', null, null, null, null, '0');
INSERT INTO `sys_user` VALUES ('s1', 'student', '学生0', '123456', null, '2015-07-22 22:14:37', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('s2', 'student1', '学生1', '123456', null, '2015-07-22 22:14:58', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('supermanager', 'administrator', '超级系统管理员', 'administrator', null, '2015-12-05 14:01:12', '2015-12-31 14:01:20', '2017-11-24 19:15:33', '超级系统管理员22', null, '0');
INSERT INTO `sys_user` VALUES ('system', 'system', '系统管理员', 'admin', null, '2015-07-01 15:39:36', null, '2016-04-21 23:00:58', null, null, '0');
INSERT INTO `sys_user` VALUES ('t1', 'teacher', '教师1', '123456', null, '2015-07-22 22:14:08', null, null, null, null, '0');
