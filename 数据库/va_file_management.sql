/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : va_file_management

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 28/07/2023 10:05:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vfm_filelist
-- ----------------------------
DROP TABLE IF EXISTS `vfm_filelist`;
CREATE TABLE `vfm_filelist`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `end` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `size` double NULL DEFAULT NULL,
  `about` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vfm_filelist
-- ----------------------------
INSERT INTO `vfm_filelist` VALUES (1, 'D:\\VFM\\2.docx', '2', 'docx', 11108, '\"my name is Sun Daming and I am a senior software engineer . I have extensive experience in developing and implementing complex software systems using advanced development tools .\"');
INSERT INTO `vfm_filelist` VALUES (2, 'D:\\VFM\\2.docx', '2', 'docx', 11108, '\"my name is Sun Daming and I am a senior software engineer . I have extensive experience in developing and implementing complex software systems using advanced development tools .\"');

-- ----------------------------
-- Table structure for vfm_location
-- ----------------------------
DROP TABLE IF EXISTS `vfm_location`;
CREATE TABLE `vfm_location`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `vfml_location` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `vfml_location_uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_delete` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vfm_location
-- ----------------------------
INSERT INTO `vfm_location` VALUES (1, 'D:/VFM', '0', 'SYSTEM', 'SYSTEM', '1', 'tyza66', 0);

-- ----------------------------
-- Table structure for vfm_user
-- ----------------------------
DROP TABLE IF EXISTS `vfm_user`;
CREATE TABLE `vfm_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `vfmu_user_uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `vfmu_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `vfmu_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_delete` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vfm_user
-- ----------------------------
INSERT INTO `vfm_user` VALUES (1, '0', 'tyza66', 'tyza66', 'SYSTEM', 'SYSTEM', 'SYSTEM', 'SYSTEM', 0);
INSERT INTO `vfm_user` VALUES (2, '07cb9f46-9dc9-404b-b23e-6575ee7d0617', 'admin', 'admin', 'SYSTEM', 'SYSTEM', 'SYSTEM', 'SYSTEM', 0);
INSERT INTO `vfm_user` VALUES (3, '6921d41e-d58d-4e08-90c5-7274fff39e22', '12321', '231', 'SYSTEM', 'SYSTEM', 'SYSTEM', 'SYSTEM', 0);

SET FOREIGN_KEY_CHECKS = 1;
